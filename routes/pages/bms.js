var express = require('express');
var deviceData = require('../../Services/deviceData');
var router = express.Router();

router.get('/dashboard', ensureAuthenticated, function(req, res){
    res.render('dashboardWidgetView');
});

function ensureAuthenticated(req, res, next){
    if(req.isAuthenticated()){
        return next();
    } else {
        req.flash('error_msg','You are not logged in');
        res.redirect('/BMS/users/login');
    }
}

module.exports = router;
