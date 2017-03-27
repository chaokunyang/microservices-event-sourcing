define(['js/app'], function (app) {
    'use strict';
    app.config(function ($httpProvider) {
            // responseInterceptors 在1.2.6版本存在，在1.3.8已经不存在了，取而代之的是interceptors。如果继续使用原来的responseInterceptors会报错，导致模块加载失败
            $httpProvider.interceptors.push('httpInterceptor');
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