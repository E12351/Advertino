var express = require('express');
var db = require('../models/userDashboard');
var device = require('../Services/Device');
var deviceDataservice = require('../Services/deviceData');

var router = express.Router();
//log configuration----------------------------------------------------------------------------------------------------
var util = require('util');
var deviceData = require("../Services/deviceData");

console.log = function (d) { //
    var datetime = new Date();
    global.log_file.write(datetime + " : " + util.format(d) + '\n');
};
//---------------------------------------------------------------------------------------------------------------------

/**
 * @api {get} widgets/entity/:entityId Request device by entityId
 * @apiName get devices by entityId
 * @apiGroup Dashboard
 *
 * @apiParam {Number} entityId entity of the building.
 *
 * @apiSuccess {array} device list of the entity.
 *
 * @apiSuccessExample Success-Response:
 *     HTTP/1.1 200 OK
 *     [
 *       10,
 *       20
 *     ]
 */

router.get('/widgets/entity/:entity', function (req, res) {
    var entity = req.params.entity;
    db.getDevicesbyEntity(entity, function (devices) {
        deviceData.setVrstateofdevice(devices, function (data) {
            res.send(devices);

        });
    });
});

/**
 * @api {get} /widgets/device/:device?count=1 data by number of count (deprecated API)
 * @apiName get data for widgets
 * @apiGroup Dashboard
 *
 * @apiParam {Number} device deviceId of the device (BMS database).
 * @apiParam {Number} count number of events.
 *
 * @apiSuccess {array} data of the device.
 *
 * @apiSuccessExample Success-Response:
 *
 *     HTTP/1.1 200 OK
 *     {
 *     }
 */

router.get('/widgets/device/:device', function (req, res) {
    try {
        var deviceId = req.params.device;
        var count = req.query.count;

        if (count == undefined) {
            count = 1;
        }
        device.getDeviceLastDatabyId(deviceId, function (deviceData) {
            if (deviceData['events'][0]) {
                var event = deviceData['events'][0]['name'];
                var widgettype = deviceData['events'][0]['type'];
                var mac = deviceData['macAddress'];

                deviceDataservice.getAccessTocken(function (obj) {
                    deviceDataservice.getValuesBymac(obj, mac, event, parseInt(count), function (body) {
                        deviceDataservice.formatDatamulty(body, widgettype, deviceId, function (dataFormatted) {
                            res.send(dataFormatted);
                        });
                    });
                });
            } else {
                console.log('Event names undefined.');
            }
        });
    } catch (ex) {
        console.log(ex);
    }
});

/**
 * @api {get} db/detailview/device/:deviceId?type='widgetType'&count='nEvents'&stDate='startDate'&enDate='endDate'&minMaxflag='0' dataQuary
 * @apiName get history data
 * @apiGroup Dashboard
 *
 * @apiParam {Number} deviceId deviceId of the device.
 * @apiParam {Number} count number of events.
 * @apiParam {String} startDate (should URL encoded).
 * @apiParam {String} endDate (should URL encoded).
 * @apiParam {String} minMaxflag (can be 0 or 1. if 1 it will calculate the min/ max/ avg).
 * @apiParam {String} type of the widget.
 *
 * @apiSuccess {array} data of the device.
 *
 * @apiSuccessExample Success-Response:
 *     http://localhost:3000/BMS/db/detailview/device/17?type=environment&count=1&stDate=2018-08-08%2012%3A00%3A00&enDate=2018-08-10%2012%3A00%3A00
 *
 *     HTTP/1.1 200 OK
 *     {
 *         "2507":[{"stateName_s":"none","timestamp_s":"1533796157429","time_s":"2018-08-09T11:59:17.429","mac_s":"en000004"}]
 *     }
 */
router.get('/detailview/device/:device', ensureAuthenticated, function (req, res) {
    try {
        var deviceId = req.params.device;
        var type = req.query.type;
        var count = req.query.count;
        var stDate = req.query.stDate;
        var enDate = req.query.enDate;

        var minMaxflag = req.query.minMaxflag;

        device.getDeviceLastDatabyId(deviceId, function (deviceData) {

            console.log(deviceData);

            var deviceIdIot = deviceData['iotID'];
            var eventParams = null;
            var eventParamsArry = [];
            var eventName = null;

            if (deviceData['events']) {
                deviceData['events'].forEach(function (entry) {
                    eventName = entry['name'];
                    if (entry['type']) {
                        if (type == entry['type']) {
                            if (entry['eventParameters']) {
                                entry['eventParameters'].forEach(function (item) {
                                    eventParamsArry.push(item['mappedName']);
                                    if (eventParams == null) {
                                        if (item['mappedName']) {
                                            eventParams = item['mappedName'];
                                        }
                                    } else {
                                        if (item['mappedName']) {
                                            eventParams = eventParams + ',' + item['mappedName'];
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
                deviceDataservice.getAccessTocken(function (obj) {
                    deviceDataservice.getAccessX_JWT(obj, function (dataAuth) {
                        // X_JWT, data, deviceIds, event, eventParams, noEvents, startDate, endDate, zoneId, callback
                        deviceDataservice.getdeviceData(obj.access_token, dataAuth, deviceIdIot, eventName, eventParams, count, stDate, enDate, 'Asia/Colombo', function (data) {
                            // if(data != null){
                            deviceDataservice.dataFormation(data, deviceIdIot, minMaxflag, eventParamsArry, function (resData) {
                                console.log(resData);
                                res.send(resData);
                            });
                            // }else{
                            //     res.send({
                            //         type : 'ERROR',
                            //         data:'No data'
                            //     });
                            // }
                        });
                    });
                });
            } else {
                console.log('deviceData Error');
            }
        });
    } catch (ex) {
        console.log(ex);
    }

});

/**
 * @api {get} /widgets/:nodeId get widget data by entityId
 * @apiName get widget data
 * @apiGroup Dashboard
 *
 * @apiParam {Number} entityId of the building.
 *
 * @apiSuccess {array} data of the device.
 *
 * @apiSuccessExample Success-Response:
 *
 *     HTTP/1.1 200 OK
 *     {
 *      [["enviroment",1,1],["power",2,1]]
 *     }
 *
 *     [widget type, x coordinate, y coordinate]
 */
router.get('/widgets/:nodeId', ensureAuthenticated, function (req, res) {
    db.getWidgetsDatabyEntity(req.params.nodeId, function (data) {
        console.log('widgets ' + data);
        res.send(data);
    });
});

function ensureAuthenticated(req, res, next) {
    if (req.isAuthenticated()) {
        return next();
    } else {
        req.flash('error_msg', 'You are not logged in');
        res.redirect('/BMS/users/login');
    }
}

module.exports = router;
