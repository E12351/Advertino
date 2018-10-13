var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var exphbs = require('express-handlebars');
var expressValidator = require('express-validator');
var flash = require('connect-flash');
var session = require('express-session');
var passport = require('passport');

var app = express();
var http = require('http').Server(app);

// var LocalStrategy = require('passport-local').Strategy;

var routes = require('./routes/pages/index');
var users = require('./routes/pages/users');
var bms = require('./routes/pages/bms');
var data = require('./routes/data');

var config;
// configurations-------------------------------------------------------------------------------------------------------
var env = process.argv[2];
if (env == 'production') {
    config = require('./config/production');
    global.config = require('./config/production');
} else {
    config = require('./config/config');
    global.config = require('./config/config');
}

//log configuration----------------------------------------------------------------------------------------------------
var fs = require('fs');
var util = require('util');
global.log_file = fs.createWriteStream(__dirname + '/debug.log', {flags: 'w'});

console.log = function (d) {
    var datetime = new Date().toLocaleString();
    global.log_file.write(datetime + " : " + util.format(d) + '\n');
    // log_stdout.write(datetime + " : " + util.format(d) + '\n');
};

// View Engine
app.set('views', path.join(__dirname, 'views'));
app.engine('handlebars', exphbs({defaultLayout: 'layout'}));
app.set('view engine', 'handlebars');

// BodyParser Middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false, limit: '5mb'}));
app.use(cookieParser());

// Express Session
app.use(session({
    secret: 'secret',
    saveUninitialized: true,
    resave: true
}));

// Passport init
app.use(passport.initialize());
app.use(passport.session());

// Express Validator
app.use(expressValidator({
    errorFormatter: function (param, msg, value) {
        var namespace = param.split('.')
            , root = namespace.shift()
            , formParam = root;

        while (namespace.length) {
            formParam += '[' + namespace.shift() + ']';
        }
        return {
            param: formParam,
            msg: msg,
            value: value
        };
    }
}));

// Connect Flash
app.use(flash());

// Global Vars
app.use(function (req, res, next) {
    res.locals.success_msg = req.flash('success_msg');
    res.locals.error_msg = req.flash('error_msg');
    res.locals.error = req.flash('error');
    res.locals.user = req.user || null;
    res.locals.env = env;
    next();
});

if (env == "production") {
    app.use('/', routes);
    app.use('/bms', bms);
    app.use('/users', users);
    app.use('/data', data);
    app.use("/", express.static(path.join(__dirname + '/public')));

} else {
    app.use('/', routes);
    app.use('/BMS/bms', bms);
    app.use('/BMS/users', users);
    app.use('/BMS/data', data);
    app.use("/BMS/", express.static(path.join(__dirname + '/public')));

}

http.listen(config.port, function () {
    if (config.debug == true) {
        console.log('-------------------------Loading Server-----------------------');
        console.log('Server Port : ' + config.port);
    }
});
