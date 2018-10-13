//log configuration----------------------------------------------------------------------------------------------------
var fs = require('fs');
var util = require('util');

console.log = function (d) { //
    var datetime = new Date();
    global.log_file.write(datetime + " : " + util.format(d) + '\n');
    // log_stdout.write(datetime + " : " + util.format(d) + '\n');
};

exports.getDeviceLastDatabyId = function (deviceId, callback) {
    try {
        const request = require('request');
        const options = {
            url: global.config.service + 'api/devices/' + deviceId.toString(),
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }

        };
        request(options, function (err, res, body) {
            if (err == null) {
                if (body != null) {
                    var obj = JSON.parse(body);
                    return callback(obj);
                } else {
                    return callback('null object returned.');
                }
            } else {
                return callback(err);
            }
        });
    }catch(ex){
        console.log(ex);
    }
};

// need to test and mapped with the rountings.
exports.createDeviceGroup = function (deviceId, operation, expireSeconds,callback) {
    const request = require('request');
    var accesstocken = data.access_token;

    const options = {
        url: 'https://iotdev.dialog.lk/axt-iot-mbil-instance0001001/apkios/axtitomblebckenddev/deviceGroups',
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'IotMife-AccessToken': accesstocken,
            'x-key': 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJodHRwczovL2lvdGRldi5kaWFsb2cubGsiLCJleHAiOjE1Njg1OTY2NzYsImlhdCI6MTM1Njk5OTUyNCwiaXNzIjoiaHR0cHM6Ly9pb3RkZXYuZGlhbG9nLmxrIiwianRpIjoiSW90Kjc2NTY2RGlhbG9nIiwibmJmIjoxMzU3MDAwMDAwLCJ0eXAiOiJodHRwczovL2lvdGRldi5kaWFsb2cubGsvYXV0aGVudGljYXRlIiwidXNlcl9pZCI6NTc3NzB9.7LHwQQpQD5ygS8h4t3TEkL2urlqBo1YyvrymsceW4iU'
        },

        body: JSON.stringify({
            "deviceIds": deviceId,
            "operation": operation,
            "expireSeconds": expireSeconds,
            "valueRanges":{

            }
        })
    };
    request(options, function (err, res, body) {
        return callback(body);
    });
};