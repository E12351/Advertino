//log configuration----------------------------------------------------------------------------------------------------
var fs = require('fs');
var util = require('util');

console.log = function(d) {
   var datetime = new Date();
   global.log_file.write(datetime + " : " + util.format(d) + '\n');
   // log_stdout.write(datetime + " : " + util.format(d) + '\n');
};
exports.userlogin = function (email, password, callback) {
   const request = require('request');
   const options = {
      url: global.config.service + 'api/users/login',
      method: 'POST',
      headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json'
      },
      body: JSON.stringify({
         "email": email,
         "password": password
      })
   };
   request(options, function (err, res, body) {
      console.log(body);
      if(err == null){
         if (body != null){
            var obj = JSON.parse(body);
            return callback(obj);
         }else{
            return callback('null object returned.');
         }
      }else{
         return callback(err);
      }
   });
};
exports.userCreate = function (AdminId, id, email, mobile, name, password, callback) {
   const request = require('request');
   const options = {
      url: global.config.service + 'api/users',
      method: 'POST',
      headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json'
      },
      body: JSON.stringify({
         "addedAdminId": AdminId,
         "email": email,
         "id": 0,
         "mobile": mobile,
         "name": name,
         "password": password,
         "roleId": 0
      })
   };
   request(options, function (err, res, body) {
      if(err == null){
         if (body != null){
            obj = JSON.parse(body);
            return callback(obj);
         }else{
            return callback('null object returned.');
         }
      }else{
         return callback(err);
      }
   });
};
exports.getUserbyId = function (id, callback) {
   const request = require('request');
   const options = {
      url: global.config.service + 'api/users/'+id,
      method: 'GET',
      headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json'
      }
   };
   request(options, function (err, res, body) {
      if(err == null){
         if (body != null){
            obj = JSON.parse(body);
            return callback(obj);
         }else{
            return callback('null object returned.');
         }
      }else{
         return callback(err);
      }
   });
};
exports.updateUserbyId = function (AdminId, id, email, mobile, name, password, callback){
   const request = require('request');
   const options = {
      url: global.config.service + 'api/users',
      method: 'PUT',
      headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json'
      },
      body: JSON.stringify({
         "addedAdminId": AdminId,
         "email": email,
         "id": id,
         "mobile": mobile,
         "name": name,
         "password": password,
         "roleId": 0
      })
   };
   request(options, function (err, res, body) {
      if(err == null){
         if (body != null){
            obj = JSON.parse(body);
            return callback(obj);
         }else{
            return callback('null object returned.');
         }
      }else{
         return callback(err);
      }
   });
};
//need to implement
exports.deletetUserbyId = function (){

}
