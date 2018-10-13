var app = angular
    .module('dashboard', ['ui.bootstrap'])
    .config(function ($interpolateProvider) {
        $interpolateProvider.startSymbol('{[{');
        $interpolateProvider.endSymbol('}]}');
        // $interpolateProvider.errorOnUnhandledRejections(false);
    })
    .controller('dashboardController', function ($scope, $http, $compile, $q) {

        $scope.tabs = [
            {title: 'DAYWISE', content: '../detail_view_includes/env_daywise_tab_content.html'}
            // {title: 'DEVICEWISE', content: '../detail_view_includes/env_devicewise_tab_content.html'}
            // { title:'MAX/MIN/AVG', content:'tab2.html'}
        ];


        $scope.gridster = $(".gridster").gridster({
            widget_margins: [10, 10],
            widget_base_dimensions: ['auto', 208],
            avoid_overlapped_widgets: false,
            max_cols: 2,
            serialize_params: function ($w, wgd) {
                return {
                    id: wgd.el[0].id,
                    device: $('#' + wgd.el[0].id).data('id'),
                    col: wgd.col,
                    row: wgd.row,
                    htmlContent: $($w).html()
                };
            }
        }).data('gridster');

        $scope.date = new Date();

        $scope.i = 0;
        $scope.powerWidgetName = "power_file.html";
        $scope.enviromentWidgetName = "enviorenment_file.html";

        $scope.addWidgets = function (gridster, widegt, type) {
            gridster.add_widget.apply(gridster, [widegt]);
            $scope.addDeatailViewTab(type);
            $scope.i++;
        };

        $scope.loadDevicesAndVariables = function () {
            var active = getActiveObj();

            var deviceList = [];
            active.devices.forEach(function (item) {
                if (item['widget'] == 'environment') {
                    deviceList.push(item['deviceId']);
                }
            });
            $scope.devices = deviceList;
        };

        $scope.generateDataGraph = function () {


            $(".graph-loader-outter").show();
            $(".loader-graph").show();
            var active = getActiveObj();
            if ($("input[name='rbDateType']:checked").val() === 'SINGLE_DATE') {
                var date = $('.date-input').val().toString();
                var singleDate = moment(date).format('YYYY-MM-DD');
                var dateWithTime = singleDate + ' 00:00:00';
                var endDateTime = singleDate + ' 23:59:00';
            }
            else {
                var daterange = $('.date-range-input').val().split(" - ");
                console.log($('.date-range-input').val(), daterange);
                var dateWithTime = daterange[0] + ' 00:00:00';
                var endDateTime = daterange[1] + ' 23:59:00';
            }

            var device = $('.device_id').val();
            var parameter = $('.parameter_select').val();

            main.getDatabyDate($http, 'environment', device, dateWithTime, endDateTime, "0", function (data) {

                var detailviewData = data;
                var time = [];

                detailviewData['time_s'].forEach(function (element) {
                    time.push(moment(element).format('hh:mm'));
                });

                if (parameter == 'co2') {
                    detailview.series[0].update({
                        data: detailviewData['co2']
                    }, true);
                    detailview.yAxis[0].update({
                        title: {
                            text: "CO2 (ppm)"
                        }
                    });
                    detailview.series[0].update({
                        tooltip: {
                            valueSuffix: ' ppm'
                        }
                    });
                    detailview.setTitle({text: "VARIATION OF CARBON DIOXIDE"});
                    detailview.series[0].update({name: " "});
                }
                if (parameter == 'temperature') {
                    detailview.series[0].update({
                        data: detailviewData['temperature']
                    }, true);
                    detailview.yAxis[0].update({
                        title: {
                            text: "TEMPERATURE (C)"
                        }
                    });
                    detailview.setTitle({text: "VARIATION OF TEMPERATURE"});
                    detailview.series[0].update({name: " "});
                    detailview.series[0].update({
                        tooltip: {
                            valueSuffix: ' C'
                        }
                    });
                    // detailview.series[0].update({
                    //     color: color ? null : Highcharts.getOptions().colors[3]
                    // });
                    // color = !color;
                }
                if (parameter == 'humidity') {
                    detailview.series[0].update({
                        data: detailviewData['humidity']
                    }, true);
                    detailview.yAxis[0].update({
                        title: {
                            text: "HUMIDITY (%)"
                        }
                    });
                    detailview.series[0].update({
                        tooltip: {
                            valueSuffix: ' %'
                        }
                    });
                    detailview.setTitle({text: "VARIATION OF HUMIDITY"});
                    detailview.series[0].update({name: " "});
                }
                detailview.xAxis[0].update({
                    categories: time

                }, true);

                setTimeout(function () {
                    $(".graph-loader-outter").hide();
                    $(".loader-graph").hide();
                }, 2000);
            }).catch(function (data) {
                console.log(data);

            });
        };

        var cordinates;

        $scope.fillActiveObject = function (nodeid) {
            var active = getActiveObj();
            $http({
                method: "GET",
                url: "/BMS/data/widgets/entity/" + nodeid,
                headers: {'Content-Type': 'application/json'}
            }).then(function mySuccess(response) {
                main.fillAvgMaxMin(response, active, $http, $scope, $q, function (data) {
                    console.log(data);

                });
            }).then(function mySuccess(response) {
            }, function myError(error) {
                console.log(error);
            });
        };

        $scope.saveWidgets = function (gridster) {
            cordinates = gridster.serialize();
            var data = [];
            cordinates.forEach(function (element) {
                data.push([element.id, element.device, element.col, element.row])
            });
            var active = getActiveObj();
            $http({
                method: "POST",
                url: "/BMS/data/save/" + active.entity,
                data: data
            }).then(function mySuccess(response) {
            }, function myError(response) {
            });
        };

        $scope.removeWidgets = function (gridster) {
            gridster.remove_widget(document.getElementById('enviroment'));
        };

        $scope.removeWidgetsAll = function (gridster) {
            gridster.remove_widget(document.getElementById('environment'));
            gridster.remove_widget(document.getElementById('power'));
        };

        $scope.loadsavedWidgets = function (gridster, nodeid) {

            var filterVal = 'blur(10px)';
            var nonBlur = 'blur(0px)';

            $(".loader-wrapper").show();
            $(".main-content-area").css('filter', filterVal)
                .css('webkitFilter', filterVal)
                .css('mozFilter', filterVal)
                .css('oFilter', filterVal)
                .css('msFilter', filterVal);

            $http({
                method: "GET",
                url: "/BMS/data/widgets/" + nodeid,
                headers: {'Content-Type': 'application/json'}
            }).then(function mySuccess(response) {
                var dataHtml = response.data;
                var active = getActiveObj();
                active.widgets = dataHtml;

                dataHtml.forEach(function (element) {
                    if (element[0] == "enviroment") {
                        widgetLib.getWidgetHTML($scope.enviromentWidgetName, gridster, element, function (data) {
                            console.log('success');
                            widgetLib.setHTMLtoWidgets();
                        });

                        angular.element(document.getElementById('detailViewTab')).append($compile("<div data-toggle=\"modal\" data-target=\"#myModal\" ng-click=\"loadsavedDetailView(); loadDevicesAndVariables()\" class=\"singal-widget-item\">Enviroment</div>")($scope));
                    }
                    if (element[0] == "power") {
                        widgetLib.getWidgetHTML($scope.powerWidgetName, gridster, element, function (data) {
                            console.log('success');

                            widgetLib.setHTMLtoWidgets();
                        });

                        angular.element(document.getElementById('detailViewTab')).append($compile("<div ng-click=\"detailViewAction('power')\" class=\"singal-widget-item\">power</div>")($scope));
                    }
                });

                setTimeout(function () {
                    $(".loader-wrapper").hide();
                    $(".main-content-area").css('filter', nonBlur)
                        .css('webkitFilter', nonBlur)
                        .css('mozFilter', nonBlur)
                        .css('oFilter', nonBlur)
                        .css('msFilter', nonBlur);
                }, 2000);


            }, function myError(response) {
                console.log(response);
                setTimeout(function () {
                    $(".loader-wrapper").hide();
                    $(".main-content-area").css('filter', nonBlur)
                        .css('webkitFilter', nonBlur)
                        .css('mozFilter', nonBlur)
                        .css('oFilter', nonBlur)
                        .css('msFilter', nonBlur);
                }, 2000);
            });
        };

        $scope.loadsavedDetailView = function () {

            main.setAvgMinMaxValuesDetailView();

            $("#dashboarWidgets").hide();
            $("#detailViewContent").show();
        };

        $scope.homeWidgets = function () {
            $("#detailViewContent").hide();
            $("#dashboarWidgets").show();
        };

        $scope.addDeatailViewTab = function (type) {
            console.log(type);
            if (type == 'enviroment') angular.element(document.getElementById('detailViewTab')).append($compile("<div ng-click=\"detailViewAction('enviroment')\" id=\"showDetail\" class=\"singal-widget-item\">Enviroment</div>")($scope));
            if (type == 'power') angular.element(document.getElementById('detailViewTab')).append($compile("<div ng-click=\"detailViewAction('power')\" class=\"singal-widget-item\">power</div>")($scope));
            if (type == 'generator') angular.element(document.getElementById('detailViewTab')).append($compile("<div ng-click=\"detailViewAction('generator')\" class=\"singal-widget-item\">generator</div>")($scope));
            if (type == 'floor') angular.element(document.getElementById('detailViewTab')).append($compile("<div ng-click=\"detailViewAction('floor')\" class=\"singal-widget-item\">floor</div>")($scope));
            if (type == 'lift') angular.element(document.getElementById('detailViewTab')).append($compile("<div ng-click=\"detailViewAction('lift')\" class=\"singal-widget-item\">lift</div>")($scope));
            if (type == 'smartmeter') angular.element(document.getElementById('detailViewTab')).append($compile("<div ng-click=\"detailViewAction('Smartmeter')\" class=\"singal-widget-item\">Smartmeter</div>")($scope));
            hideContent();
        };

        $scope.getWidgetHTML = function (data, gridster, element) {

        };

        $('#treeview-searchable').on('nodeSelected', function (e, event) {
            setActiveObj(event.id, event.text);

            var nodeid = getActiveObj().entity;
            console.log('node selected : ' + nodeid);
            $("#detailViewTab").empty();
            hideContent();
            $scope.removeWidgetsAll($scope.gridster);
            $scope.fillActiveObject(nodeid);
            $scope.loadsavedWidgets($scope.gridster, nodeid);

            $("#detailViewContent").hide();

        });
    });
