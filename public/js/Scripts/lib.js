var main = {
    fillAvgMaxMin: function (response, active, $http, $scope, $q, callback) {
        active.devices = response['data'];

        active.devices.forEach(function (entry) {
            main.getDatabycount(entry, function (data) {
                if (data[0]['type'] == 'environment') {
                    dashboard.setEnviromentValue(data);
                }
                if (data[0]['type'] == 'power') {
                    dashboard.setValueSmartmeter(data);
                }
            });

            //Filling max min avg in the current entity.
            var date = new Date();
            var singleDate = moment(date).format('YYYY-MM-DD');
            // var singleDate = '2018-08-07';
            var H = date.getHours();
            var M = date.getMinutes() - 1;

            var dateWithTime = singleDate + ' 00:00:00';
            var endDateTime = singleDate + ' ' + H + ':' + M + ':00';

            // console.log(dateWithTime + ' ' + endDateTime);

            var device = entry.deviceId;

            if (entry.widget == 'environment') {
                main.getDatabyDate($http, 'environment', device, dateWithTime, endDateTime, "1", function (data) {
                    var deferred = $q.defer();
                    if (data != 'ERROR') {
                        // console.log(data);
                        entry['data'] = data;
                        if (entry['data']) deferred.resolve();
                        else deferred.reject(console.log('ERROR'));
                    } else {
                        alert('No data');
                    }

                }, function myError(err) {
                    console.log(err);
                });
            }
        });
        return callback(active);
    },

    //get data method from node js backend.
    getDatabyDate: function ($http, type, device, dateWithTime, endDateTime, minMaxFlag, callback) {
        $http({
            method: "GET",
            url: "/BMS/data/detailview/device/" + device + "?type=" + type + "&count=24&stDate=" + encodeURI(dateWithTime) + "&enDate=" + encodeURI(endDateTime) + "&minMaxflag=" + minMaxFlag,
            headers: {'Content-Type': 'application/json'}
        }).then(function mySuccess(response) {
            // console.log(response['data']);
            if (response['data']['type']) {
                alert('need to popup no data alert here.');
            } else {
                home.loadDateRange();
                home.loadSingleDate();
                home.switchDateRange();

                return callback(response['data']);
            }

        }).catch(function (err) {
            console.log(err);
        });
    },

    getMultipleDeviceData: function (devices, $http, type, dateWithTime, endDateTime, minMaxFlag, callback) {
        devices.forEach(function (item) {
            main.getDatabyDate($http, 'environment', item, dateWithTime, endDateTime, "0", function (data) {
                //update the graph.
            })
        });
    },

    getDatabycount: function (entry, callback) {
        $.ajax({
            type: "GET",
            url: "/BMS/data/widgets/device/" + entry.deviceId + "?count=1",
            cache: false,
            success: function (data) {
                // console.log(data);
                return callback(data);
            }
        });
    },

    setAvgMinMaxValuesDetailView: function () {
        var active = getActiveObj();

        // console.log(active.devices.virtual);
        // console.log($('#co2_min').textContent);
        // console.log(document.getElementById('co2_min').textContent);
        // var co2min_device = '';
        // var co2max_device = '';

        var co2min = 99999;
        var co2max = 0;

        var tempMin = 99999;
        var tempMax = 0;

        var humidMin = 99999;
        var humidMax = 0;

        active.devices.forEach(function (item) {
            if(item.widget == 'environment'){
                console.log(item.widget);

                if(co2min > Math.round(item.data.co2.min)){
                    co2min = Math.round(item.data.co2.min);
                    document.getElementById('co2maxDevice').textContent = ' ' + item.deviceId
                }

                if(co2max < Math.round(item.data.co2.max)){
                    co2max = Math.round(item.data.co2.max);
                    document.getElementById('co2minDevice').textContent = ' ' + item.deviceId
                }

                if(tempMin > Math.round(item.data.temperature.min)){
                    tempMin = Math.round(item.data.temperature.min);
                    document.getElementById('tempMaxDevice').textContent = ' ' + item.deviceId
                }

                if(tempMax < Math.round(item.data.temperature.max)){
                    tempMax = Math.round(item.data.temperature.max);
                    document.getElementById('tempMinDevice').textContent = ' ' + item.deviceId
                }

                if(humidMin > Math.round(item.data.humidity.min)){
                    humidMin = Math.round(item.data.humidity.min);
                    document.getElementById('humidmaxDevice').textContent = ' ' + item.deviceId
                }

                if(humidMax < Math.round(item.data.humidity.max)){
                    humidMax = Math.round(item.data.humidity.max);
                    document.getElementById('humidminDevice').textContent = ' ' + item.deviceId
                }
            }
        });

        active.devices.forEach(function (item) {
            if(item.widget == 'environment'){

                document.getElementById('co2_min').textContent = co2min;
                document.getElementById('co2_max').textContent = co2max;
                document.getElementById('co2_avg').textContent = Math.round(item.data.co2.avg);


                document.getElementById('temp_min').textContent = tempMin;
                document.getElementById('temp_max').textContent = tempMax;
                document.getElementById('temp_avg').textContent = Math.round(item.data.temperature.avg);


                document.getElementById('humid_min').textContent = humidMin;
                document.getElementById('humid_max').textContent = humidMax;
                document.getElementById('humid_avg').textContent = Math.round(item.data.humidity.avg);
                // }
            }
        });


    }

};