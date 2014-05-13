'use strict';

/* Controllers */

function clone(obj) {
    // Handle the 3 simple types, and null or undefined
    if (null == obj || "object" != typeof obj) return obj;

    // Handle Date
    if (obj instanceof Date) {
        var copy = new Date();
        copy.setTime(obj.getTime());
        return copy;
    }

    // Handle Array
    if (obj instanceof Array) {
        var copy = [];
        for (var i = 0, len = obj.length; i < len; i++) {
            copy[i] = clone(obj[i]);
        }
        return copy;
    }

    // Handle Object
    if (obj instanceof Object) {
        var copy = {};
        for (var attr in obj) {
            if (obj.hasOwnProperty(attr)) copy[attr] = clone(obj[attr]);
        }
        return copy;
    }

    throw new Error("Unable to copy obj! Its type isn't supported.");
}

angular.module('monocycleApp.controllers', [])
    .controller('ListServersCtrl', function ($scope, Restangular) {

        var servers = Restangular.all('servers');

        servers.getList().then(function (servers) {
            $scope.servers = servers;
        });

    })
    .controller('AddServerCtrl', function ($scope, Restangular) {

        $scope.save = function () {
            Restangular.all('servers').post($scope.server).then(function (project) {
                $scope.go('servers.list');
            });
        }

    })
    .controller('ServerDashboardCtrl', function ($scope, charting, Restangular, $stateParams) {

        Restangular.one('servers', $stateParams.id).get()
            .then(function (server) {
                $scope.server = server;
            });

        Restangular.one('servers', $stateParams.id).all('cpu').getList()
            .then(function (metrics) {
                $scope.chartData = [{
                    label: "CPU",
                    color: "#428bca",
                    data: metrics,
                    lines: { show: true },
                    hoverable: true
                }]
            });

        Restangular.one('servers', $stateParams.id).all('memory').getList()
            .then(function (metrics) {
                $scope.chartData2 = [{
                    label: "Memory (Used)",
                    color: "#428bca",
                    data: metrics,
                    lines: { show: true },
                    hoverable: true
                }]
            });

        $scope.chartOptions = {
            colors: ["#111"],
            tooltip: true,
            tooltipOpts: {
                content: "%x<br/>Avg: %y",
                xDateFormat: "%d/%m/%Y %H:%M:%S",
                shifts: {
                    x: 10,
                    y: 20
                },
                defaultTheme: false
            },
            xaxis: {
                mode: "time",
                tickSize: [10, "minute"],

            },
            yaxis: {
                ticks: [0, 50, 100],
                tickFormatter: function (v, axis) {
                    return Math.round(v, 0) + "%";
                }
            },
            grid: {
                backgroundColor: { colors: ["#fff", "#fff"] },
                hoverable: true
            }
        };

        $scope.chartOptions2 = {
            colors: ["#111"],
            tooltip: true,
            tooltipOpts: {
                content: "%x<br/>Used: %y",
                xDateFormat: "%d/%m/%Y %H:%M:%S",
                shifts: {
                    x: 10,
                    y: 20
                },
                defaultTheme: false
            },
            xaxis: {
                mode: "time",
                tickSize: [10, "minute"]
            },
            yaxis: {
                ticks: [0, 5120, 10240],
                tickFormatter: function (v, axis) {
                    return Math.round(v) + ' MB';
                }
            },
            grid: {
                backgroundColor: { colors: ["#fff", "#fff"] },
                hoverable: true
            }
        };

    })
    .controller('SettingsCtrl', function ($scope) {

    });