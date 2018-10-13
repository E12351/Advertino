
var bcrypt = require('bcryptjs');
var mysql = require('mysql');
//log configuration----------------------------------------------------------------------------------------------------
var fs = require('fs');
var util = require('util');

console.log = function(d) {
    var datetime = new Date();
    global.log_file.write(datetime + " : " + util.format(d) + '\n');
};
//---------------------------------------------------------------------------------------------------------------------
var env = process.argv[2];
if(env == 'production'){
    config = require('./../production');
}else {
    config = require('./../config');
}

function setActive(){
    var setDb = "USE "+ config.sqldb;
    con.query(setDb, function (err) {
        if (err) throw err;
    });
}


exports.createDb = function () {
    con.query("CREATE DATABASE BMS", function (err, result) {
        if (err) throw err;
        console.log("Database created");
    });
};

exports.createTable = function () {
    var sql = "CREATE TABLE users (\n" +
        "    ID int NOT NULL PRIMARY KEY,\n" +
        "    username varchar(255) NOT NULL,\n" +
        "    name varchar(255) NOT NULL,\n" +
        "    email varchar(255) NOT NULL,\n" +
        "    password varchar(255)\n" +
        ");";
    con.query(sql, function (err, result) {
        if (err) throw err;
        console.log("Table created");
    });
};

module.exports.createUser = function (newUser, callback) {
    bcrypt.genSalt(10, function (err, salt) {
        bcrypt.hash(newUser.password, salt, function (err, hash) {
            newUser.password = hash;
            newUser.save(callback);

            insert(newUser.username, newUser.password, newUser.email, newUser.password.name);
        });
    });
};

function insert(username, password, email, name) {
    var sql = "INSERT INTO users (username, password, email, name) VALUES ('" + username + "', '" + password + "', '" + email + "', '" + name + "')";
    con.query(sql, function (err, result) {
        if (err) throw err;
        console.log("1 record inserted");
    });
};


module.exports.getUserByUsername = function (username, callback) {
    setActive();

    con.query("SELECT * FROM users WHERE username = '" + username + "'", function (err, result) {
        if (err) throw err;
        return callback(err, result[0]);
    });

};

module.exports.getUserById = function (id, callback) {
    setActive();
    con.query("SELECT * FROM users WHERE id = '" + id + "'", function (err, result) {
        if (err) throw err;
        return callback(err, result[0]);
    });

};

module.exports.comparePassword = function (candidatePassword, hash, callback) {
    bcrypt.compare(candidatePassword, hash, function (err, isMatch) {
        if (err) throw err;
        callback(null, isMatch);
    });
};
