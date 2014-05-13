'use strict';

// Declare app level module which depends on filters, and services
angular.module('monocycleApp', [
        'ui.router', 'restangular', 'ngAnimate', 'angular-flot',
        'monocycleApp.filters',
        'monocycleApp.services',
        'monocycleApp.directives',
        'monocycleApp.controllers'

    ]).run(
        [        '$rootScope', '$state', '$stateParams',
            function ($rootScope, $state, $stateParams) {

                // It's very handy to add references to $state and $stateParams to the $rootScope
                // so that you can access them from any scope within your applications.For example,
                // <li ui-sref-active="active }"> will set the <li> // to active whenever
                // 'contacts.list' or one of its decendents is active.
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
            }
        ])
    .config(function ($stateProvider, $urlRouterProvider, RestangularProvider) {

        RestangularProvider.setBaseUrl('http://localhost:8080/');

        $urlRouterProvider.otherwise("/servers");
        $stateProvider
            .state('servers', {
                abstract: true,
                url: "/servers",
                template: '<div ui-view></div>'
            })
            .state('servers.list', {
                url: "/list",
                templateUrl: "./partials/servers/list.html",
                controller: "ListServersCtrl"
            })
            .state('servers.add', {
                url: "/add",
                templateUrl: "partials/servers/add.html",
                controller: "AddServerCtrl"
            })
            .state('servers.dashboard', {
                url: "/dashboard/{id}",
                templateUrl: "partials/servers/dashboard.html",
                controller: "ServerDashboardCtrl"
            })
            .state('settings', {
                url: "/settings",
                templateUrl: "partials/settings.html",
                controller: "SettingsCtrl"
            })
    });
