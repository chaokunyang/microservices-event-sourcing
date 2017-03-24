define(['angularAMD', 'angular', 'angular-ui-router', 'angular-resource', 'common/sharedServices'], function (angularAMD) {
    'use strict';
    var app = angular.module('storeApp', ['ui.router', 'ngResource']);
    app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('home', angularAMD.route({
                url: '/home',
                templateUrl: '/assets/views/home/home.html',
                controllerUrl: 'js/home/homeController'
            }))
            .state('user', angularAMD.route({
                url: 'user',
                templateUrl: 'assets/views/user/user.html',
                controllerUrl: 'assets/js/user/user'
            }))
            .state('product', angularAMD.route({
                url: 'product',
                templateUrl: 'assets/views/product/product.html',
                controllerUrl: 'assets/js/product/product'
            }))
            .state('cart', angularAMD.route({
                url: 'cart',
                templateUrl: 'assets/views/cart/cart.html',
                controllerUrl: 'assets/js/cart/cart'
            }))
            .state('order', angularAMD.route({
                url: 'order',
                templateUrl: 'assets/views/order.html',
                controllerUrl: 'assets/js/order/order'
            }));

        // Else
        $urlRouterProvider.otherwise('/home');
    }]);

    app.filter('rawHtml', ['$sce', function ($sce) {
        return function (val) {
            return $sce.trustAsHtml(val);
        };
    }]);

    app.factory('orderService', function () {
        var completedOrder = {};

        function set(data) {
            completedOrder = data;
        }

        function get() {
            return completedOrder;
        }

        return {
            set: set,
            get: get
        }
    });


    angularAMD.bootstrap(app);
    return app;
});
