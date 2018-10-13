// var deviceData = require('./deviceData');
//
// exports.updateDashboard = function (callback) {
//     deviceData.getAccessTocken(function (obj) {
//         deviceData.getValuesBymac(obj, 'en000004','indata', 1, function (body) {
//             deviceData.formatData(body, function (dataFormatted) {
//                 return callback(dataFormatted);
//             });
//         });
//     });
// };
//
// exports.updateDashboardsmartmeter = function (callback) {
//     deviceData.getAccessTocken(function (obj) {
//         deviceData.getValuesBymac(obj, '861693033659236-21','Peripheral_Data', 1, function (body) {
//             deviceData.formatData(body, function (dataFormatted) {
//                 return callback(dataFormatted);
//             });
//         });
//     });
// };
//
