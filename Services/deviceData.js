//log configuration----------------------------------------------------------------------------------------------------
var fs = require('fs');
var util = require('util');
var urlencode = require('urlencode');
var moment = require('moment');
var device = require("./Device");
var Promise = require('promise');

console.log = function (d) { //
    var datetime = new Date();
    global.log_file.write(datetime + " : " + util.format(d) + '\n');
};

//---------------------------------------------------------------------------------------------------------------------
exports.getAccessTocken = function (callback) {
    const request = require('request');
    const options = {
        url: 'https://iotdev.dialog.lk/axt-iot-mbil-instance0001001/apkios/axtitomblebckenddev/generate/iotmifetokenviaunps ',
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'IotMife-Token': config.IotMife_Token,
            'IotMife-Username': config.IotMife_Username,
            'IotMife-Passowrd': config.IotMife_Passowrd
        }
    };

    request(options, function (err, res, body) {
        if (body != undefined) {
            var obj = JSON.parse(body);
            return callback(obj);
        }
    });
};

exports.getAccessX_JWT = function (data, callback) {
    var accesstocken = data.access_token;
    const request = require('request');

    const options = {
        url: 'https://iot.dialog.lk/developer/api/usermgt/v1/authenticate',
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + accesstocken
        },
        body: JSON.stringify({
            "username": 'DialogDeveloper@axiata.com',
            "password": 'Dialog@12345'
        })
    };

    request(options, function (err, res, body) {

        if (body != undefined) {
            var obj = JSON.parse(body);
            data.X_JWT = obj['X-IoT-JWT'];
            return callback(data);
        }
    });
};

exports.getValuesBymac = function (data, mac, event, noEvents, callback) {
    const request = require('request');
    var accesstocken = data.access_token;

    const options = {
        url: 'https://iotdev.dialog.lk/axt-iot-mbil-instance0001001/apkios/axtitomblebckenddev/solarservice/getDataByCount',
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'IotMife-AccessToken': accesstocken,
            'x-key': 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJodHRwczovL2lvdGRldi5kaWFsb2cubGsiLCJleHAiOjE1Njg1OTY2NzYsImlhdCI6MTM1Njk5OTUyNCwiaXNzIjoiaHR0cHM6Ly9pb3RkZXYuZGlhbG9nLmxrIiwianRpIjoiSW90Kjc2NTY2RGlhbG9nIiwibmJmIjoxMzU3MDAwMDAwLCJ0eXAiOiJodHRwczovL2lvdGRldi5kaWFsb2cubGsvYXV0aGVudGljYXRlIiwidXNlcl9pZCI6NTc3NzB9.7LHwQQpQD5ygS8h4t3TEkL2urlqBo1YyvrymsceW4iU'
        },

        body: JSON.stringify({
            "mac_address": mac,
            "eventName": event,
            "noOfEvents": noEvents
        })
    };

    request(options, function (err, res, body) {
        return callback(body);
    });

};

exports.getdeviceData = function (X_JWT, data, deviceIds, event, eventParams, noEvents, startDate, endDate, zoneId, callback) {
    const request = require('request');
    var accesstocken = data.access_token;
    var X_JWT = data.X_JWT;
    const options = {
        // url: 'https://iot.dialog.lk/developer/api/datamgt/v1/user/devicehistory?eventName=' + event + '&deviceIds=' + deviceIds + '&startDate=' + urlencode(startDate) + '&endDate=' + urlencode(endDate) + '&noOfEvents=' + noEvents + '&zoneId=' + zoneId + '&eventParams=' + eventParams,
        url: 'https://iot.dialog.lk/developer/api/datamgt/v1/user/devicehistory?eventName=' + event + '&deviceIds=' + deviceIds + '&startDate=' + urlencode(startDate) + '&endDate=' + urlencode(endDate) + '&zoneId=' + zoneId + '&eventParams=' + eventParams,
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + accesstocken,
            'X-IoT-JWT': X_JWT
        }
    };

    request(options, function (err, res, body) {
        try {
            // if(body != undefined){
            var obj = JSON.parse(body);

            if (obj.errorCode) {
                console.log(deviceIds + ' ' + body);
            }

            return callback(obj[deviceIds]);
            // }else{
            console.log('No Data');
            return callback(null);
            // }

        } catch (ex) {
            console.log(ex);
            return callback(null);
        }

    });

};

exports.dataFormation = function (data, deviceIdIot, minMaxflag, eventParamsArry, callback) {

    if (data == undefined) {
        console.log('data undefined. No data Alert');
        var error = {
            type: 'No data',
            data: ''
        };
        return callback(error);
    }

    const detailviewData = {};
    eventParamsArry.push('time_s');

    // noinspection JSAnnotator
    for (const key of eventParamsArry) {
        detailviewData[key] = [];
    }

    eventParamsArry.forEach(function (param) {
        // console.log(data);
        data.forEach(function (element) {
            detailviewData[param].push(parseFloat(element[param]));
            if (param == 'time_s') {
                detailviewData[param].push(decodeURI(element['time_s']));
                // detailviewData[param].push(moment(decodeURI(element['time_s'])).format('hh:mm'));
            }
        });
    });

    // console.log(detailviewData);
    if (minMaxflag == "1") {
        if (eventParamsArry) {

            const minMax = {
                device: ''
            };

            // noinspection JSAnnotator
            for (const key of eventParamsArry) {
                if (key != 'time_s') {
                    minMax[key] = {
                        min: '',
                        max: '',
                        avg: ''
                    };
                }
            }

            minMax.device = deviceIdIot;
            eventParamsArry.forEach(function (param) {
                if (param != 'time_s') {
                    minMax[param].min = Math.min.apply(Math, detailviewData[param]);
                    minMax[param].max = Math.max.apply(Math, detailviewData[param]);

                    var sum = 0;
                    for (var i = 0; i < detailviewData[param].length; i++) {
                        sum += parseInt(detailviewData[param][i], 10); //don't forget to add the base
                    }
                    minMax[param].avg = sum / detailviewData[param].length;
                }
            });
            return callback(minMax);
        }
    } else {
        return callback(detailviewData);
    }
};

exports.setVrstateofdevice = function (devices, callback) {

    var Q = require("q");

    function eventually() {
        // noinspection JSAnnotator
        for (const key of devices) {
            device.getDeviceLastDatabyId(key['deviceId'], function (deviceData) {
                key['virtual'] = deviceData['virtual'];
                console.log(key['deviceId']);
            });
        }
        return Q.delay(devices, 1000);
    }

    eventually().done(function () {
        console.log(devices);
        return callback(devices);
    });

};

exports.formatData = function (data, callback) {
    try {
        var dataObj = JSON.parse(data);
        if (dataObj['response']['docs']) {
            var mac = dataObj['response']['docs'][0]['mac_s'];
            var temperature = dataObj['response']['docs'][0]['temperature'];
            var humidity = dataObj['response']['docs'][0]['humidity'];
            var co2 = dataObj['response']['docs'][0]['co2'];

            var PhaseAVoltage = parseFloat(dataObj['response']['docs'][0]['PhaseAVoltage']) / 10;
            var PhaseBVoltage = parseFloat(dataObj['response']['docs'][0]['PhaseBVoltage']) / 10;
            var PhaseCVoltage = parseFloat(dataObj['response']['docs'][0]['PhaseCVoltage']) / 10;
            var PhaseACurrent = parseFloat(dataObj['response']['docs'][0]['PhaseACurrent']) / 100;
            var PhaseBCurrent = parseFloat(dataObj['response']['docs'][0]['PhaseBCurrent']) / 100;
            var PhaseCCurrent = parseFloat(dataObj['response']['docs'][0]['PhaseCCurrent']) / 100;
            var TotalEnergy = parseFloat(dataObj['response']['docs'][0]['TotalEnergy']) / 1000;

            var dataFormated = {
                'mac_s': mac,
                'temperature': temperature,
                'humidity': humidity,
                'co2': co2,
                'PhaseAVoltage': PhaseAVoltage,
                'PhaseBVoltage': PhaseBVoltage,
                'PhaseCVoltage': PhaseCVoltage,
                'PhaseACurrent': PhaseACurrent,
                'PhaseBCurrent': PhaseBCurrent,
                'PhaseCCurrent': PhaseCCurrent,
                'TotalEnergy': TotalEnergy
            };
            return callback(dataFormated);
        }
    }
    catch (ex) {
        // console.log('ERROR : ' + ex);
    }
}
;

exports.formatDatamulty = function (data, widgettype, deviceId, callback) {
    try {
        var dataObj = JSON.parse(data);
        var dataFormated = [];
        if (dataObj['response']['docs']) {
            dataObj['response']['docs'].forEach(function (entry) {
                var mac = entry['mac_s'];
                var temperature = entry['temperature'];
                var humidity = entry['humidity'];
                var co2 = entry['co2'];

                var PhaseAVoltage = parseFloat(entry['PhaseAVoltage']) / 10;
                var PhaseBVoltage = parseFloat(entry['PhaseBVoltage']) / 10;
                var PhaseCVoltage = parseFloat(entry['PhaseCVoltage']) / 10;
                var PhaseACurrent = parseFloat(entry['PhaseACurrent']) / 100;
                var PhaseBCurrent = parseFloat(entry['PhaseBCurrent']) / 100;
                var PhaseCCurrent = parseFloat(entry['PhaseCCurrent']) / 100;
                var TotalEnergy = parseFloat(entry['TotalEnergy']) / 1000;

                if (widgettype == 'environment') {
                    dataFormated.push({
                        'device': deviceId,
                        'type': widgettype,
                        'mac_s': mac,
                        'temperature': temperature,
                        'humidity': humidity,
                        'co2': co2
                    });
                }
                if (widgettype == 'power') {
                    dataFormated.push({
                        'device': deviceId,
                        'type': widgettype,
                        'PhaseAVoltage': PhaseAVoltage,
                        'PhaseBVoltage': PhaseBVoltage,
                        'PhaseCVoltage': PhaseCVoltage,
                        'PhaseACurrent': PhaseACurrent,
                        'PhaseBCurrent': PhaseBCurrent,
                        'PhaseCCurrent': PhaseCCurrent,
                        'TotalEnergy': TotalEnergy
                    });
                }
            });
            return callback(dataFormated);
        }
    } catch (ex) {
    }
};
