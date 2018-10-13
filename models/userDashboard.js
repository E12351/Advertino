var bcrypt = require('bcryptjs');
var mysql = require('mysql');
//log configuration----------------------------------------------------------------------------------------------------
var fs = require('fs');
var util = require('util');

console.log = function (d) {
    var datetime = new Date();
    global.log_file.write(datetime + " : " + util.format(d) + '\n');
};
//---------------------------------------------------------------------------------------------------------------------
var env = process.argv[2];
if (env == 'production') {
    config = require('./../config/production');
} else {
    config = require('./../config/config');
}

var con = mysql.createConnection({
    host: config.sqlhost,
    user: config.sqluser,
    password: config.sqluserpw
});

try {
    con.connect(function (err) {
        if (err) {
            console.log(err);
        }
    });
} catch (ex) {
    console.log(ex);
}

function setActive() {
    // var setDb = "USE "+ config.sqldb;
    var setDb = "USE bms";
    con.query(setDb, function (err) {
        if (err) throw err;
    });
}

module.exports.saveDashboard = function (userId, data, entity, callback) {
    setActive();
    var sql = "INSERT INTO dashboard (user_id, widget_data, entity) VALUES ('" + userId + "', '" + data + "', '" + entity + "')";
    con.query(sql, function (err, result) {
        if (err) throw err;
        console.log("1 record inserted into dashboard.");
    });
};

module.exports.updateDashboard = function (userId, entity, data, callback) {
    setActive();
    var sql = "UPDATE dashboard SET widget_data='" + data + "' WHERE  user_id=" + userId + " AND entity=" + entity;
    con.query(sql, function (err, result) {
        if (err) throw err;
        console.log("1 record inserted into dashboard : " + sql);
    });
};

module.exports.getWidgetsDatabyEntity = function (entity, callback) {
    setActive();
    con.query("SELECT * FROM dashboard WHERE entity = '" + entity + "'", function (err, result) {
        if (err) console.log(err);
        if (result[0] != null) {
            return callback(result[0]['widget_data']);
        }
    });
};

module.exports.getWidgetsDataforUpdate = function (entity, callback) {
    setActive();
    con.query("SELECT * FROM dashboard WHERE entity = '" + entity + "'", function (err, result) {
        if (err) console.log(err);
        if (result.length > 0) {
            return callback(result[0]['widget_data']);
        } else {
            return callback(null);
        }
    });
};

module.exports.getDevicesbyEntity = function (entity, callback) {
    setActive();
    var sql = "SELECT * FROM widgetsEntity WHERE entity = '" + entity + "'";
    con.query(sql, function (err, result) {
        if (err) throw err;
        var deviceId = [];
        for (var i = 0; i < result.length; i++) {
            if (result[i] != null) {
                deviceId.push(result[i]);
            }
        }
        return callback(deviceId);
    });
};
