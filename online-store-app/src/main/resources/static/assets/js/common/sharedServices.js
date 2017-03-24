define(['angularAMD', 'angular'], function (angularAMD) {
    'use strict';
    angular.module('sharedServices', [])
        .config(function ($httpProvider) {
            $httpProvider.responseInterceptors.push('httpInterceptor');
            var loading = function (data, headersGetter) {
                $('#loading').show();
                return data;
            };
            $httpProvider.defaults.transformRequest.push(loading);
        })
        .factory('httpInterceptor', function ($q, $window) {
            return function (promise) {
                return promise.then(function (response) {
                    $('#loading').hide();
                    return response;
                }, function (error) {
                    $('#loading').hide();
                    return $q.reject(error);
                })
            }
        })

});