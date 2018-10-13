var express = require('express');
var router = express.Router();
var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var userService = require('../../Services/user.js');
//log configuration----------------------------------------------------------------------------------------------------
var util = require('util');
console.log = function(d) { //
   var datetime = new Date();
   global.log_file.write(datetime + " : " + util.format(d) + '\n');
   // log_stdout.write(datetime + " : " + util.format(d) + '\n');
};

// create User
router.post('/register', function (req, res) {
   if( (req.body['email'] != null) && (req.body['mobile'] != null) && (req.body['name'] != null) && (req.body['password'] != null) ){
      userService.userCreate(0, 0, req.body['email'], req.body['mobile'], req.body['name'], req.body['password'],function(resdata){
         if(config.debug == true){
            console.log(resdata);
         }
         res.send(resdata);
      });
   }else{
      console.log('empty fields');
      res.send('empty fields');
   }
});
// get User
router.get('/user', function (req, res) {
   res.send('not implemented.');
});

router.get('/login', function (req, res) {
   if(config.debug == true){
      console.log('login call' + req.body);
   }
   res.render('login');
});

passport.use(new LocalStrategy(
   function (username, password, done) {
      userService.userlogin(username, password, function(data){
         if((data.id!=null) || (data.username!=null) || (data.name!=null) || (data.email!=null)){
            var user = {
               id:data.id,
               username:data.email,
               name:data.name,
               email:data.email
            };
            return done(null, user);
         }else{
            return done(null, false, {message: 'Invalid User'});

         }
      })
   })
);

passport.serializeUser(function (user, done) {
   done(null, user.id);
});

passport.deserializeUser(function (id, done) {
   userService.getUserbyId( 1, function (user){
      done(null, {
         id: user.id,
         username: user.username,
         name: user.name,
         email: user.email,
         password: '$2a$10$7eoEpaLcbSbkgkuKD.HnY.A7hWWbAbUYjm5vxk4/ykNlm9Nzm8YB.'
      });
   });
});

router.post('/login',
passport.authenticate('local', {successRedirect: '/BMS/bms/dashboard', failureRedirect: '/BMS/users/login', failureFlash: true}),
function (req, res) {
   res.redirect('/');
});

router.get('/logout', function (req, res) {
   req.logout();
   req.flash('success_msg', 'You are logged out');
   res.redirect('/BMS/users/login');
});

module.exports = router;


// Register User
// router.post('/register', function (req, res) {
//     var name = req.body.name;
//     var email = req.body.email;
//     var username = req.body.username;
//     var password = req.body.password;
//     var password2 = req.body.password2;
//
//     // Validation
//     req.checkBody('name', 'Name is required').notEmpty();
//     req.checkBody('email', 'Email is required').notEmpty();
//     req.checkBody('email', 'Email is not valid').isEmail();
//     req.checkBody('username', 'Username is required').notEmpty();
//     req.checkBody('password', 'Password is required').notEmpty();
//     req.checkBody('password2', 'Passwords do not match').equals(req.body.password);
//
//     var errors = req.validationErrors();
//
//     if (errors) {
//         res.render('register', {
//             errors: errors
//         });
//     } else {
//         //checking for email and username are already taken
//         User.findOne({
//             username: {
//                 "$regex": "^" + username + "\\b", "$options": "i"
//             }
//         }, function (err, user) {
//             User.findOne({
//                 email: {
//                     "$regex": "^" + email + "\\b", "$options": "i"
//                 }
//             }, function (err, mail) {
//                 if (user || mail) {
//                     res.render('register', {
//                         user: user,
//                         mail: mail,
//                     });
//                 }
//                 else {
//                     var newUser = new User({
//                         name: name,
//                         email: email,
//                         username: username,
//                         password: password
//                     });
//
//                     db.createUser(newUser);
//
//                     // User.createUser(newUser, function (err, user) {
//                     // 	if (err) throw err;
//                     // 	console.log(user);
//                     // });
//
//
//                     req.flash('success_msg', 'You are registered and can now login');
//                     res.redirect('/users/login');
//                 }
//             });
//         });
//     }
// });

// passport.use(new LocalStrategy(
//    function (username, password, done) {
//       db.getUserByUsername(username, function (err, user) {
//          if (err) throw err;
//          if (!user) {
//             return done(null, false, {message: 'Unknown User'});
//          }
//          db.comparePassword(password, user.password, function (err, isMatch) {
//             if (err) throw err;
//             if (isMatch) {
//                return done(null, user);
//             } else {
//                return done(null, false, {message: 'Invalid password'});
//             }
//          });
//       });
//    })
// );

