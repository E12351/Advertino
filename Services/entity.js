//log configuration----------------------------------------------------------------------------------------------------
var fs = require('fs');
var util = require('util');

console.log = function(d) { //
   var datetime = new Date();
   global.log_file.write(datetime + " : " + util.format(d) + '\n');
};

exports.getEntitybyEntityid = function(entityId, callback){
    const request = require('request');
    const options = {
        url: global.config.service + 'api/entities/' + entityId,
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    };
    request(options, function (err, res, body) {
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
exports.getEntitybyUserid = function(userId, callback){
   const request = require('request');
   const options = {
      url: global.config.service + 'api/entities?userId=' + userId,
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
exports.createEntity = function (description, location, name, parentEntityId, subEntityIds, telephone, callback) {
   const request = require('request');
   const options = {
      url: global.config.service + '/api/entities',
      method: 'POST',
      headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json'
      },
      body: JSON.stringify({
         "description": description,
         "deviceIds": [],
         "location": location,
         "name": name,
         "parentEntityId": parentEntityId,
         "subEntityIds": subEntityIds,
         "telephone": telephone
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
exports.updateEntity = function (id, description, location, name, parentEntityId, subEntityIds, telephone, callback) {
   const request = require('request');
   const options = {
      url: global.config.service + '/api/entities',
      method: 'PUT',
      headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json'
      },
      body: JSON.stringify({
         "id":id,
         "description": description,
         "deviceIds": [],
         "location": location,
         "name": name,
         "parentEntityId": parentEntityId,
         "subEntityIds": subEntityIds,
         "telephone": telephone
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
exports.deleteEntity = function (id, callback) {
   const request = require('request');
   const options = {
      url: global.config.service + '/api/entities/'+id,
      method: 'DELETE',
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
