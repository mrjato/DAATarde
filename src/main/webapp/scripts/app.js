define([
    'angular',
    'angularRoute',
    'angularResource',
    'controllers/index',
    'directives/index',
    'filters/index',
    'services/index',
], function (angular) {
    'use strict';

    return angular.module('daaTarde', [
        'daaTarde.controllers',
        'daaTarde.directives',
        'daaTarde.filters',
        'daaTarde.services',
        'ngRoute',
        'ngResource',
    ]);

});
