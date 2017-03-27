define(['angularAMD', 'angular', 'angular-ui-router', 'angular-resource', 'angular-toaster', 'js/service'], function (angularAMD) {
    'use strict';
    var app = angular.module('storeApp', ['ui.router', 'ngResource', 'toaster', 'storeService']);
    app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('home', angularAMD.route({
                url: '/home',
                templateUrl: '/assets/views/home/home.html',
                controller: 'homeCtrl',
                controllerUrl: 'js/home/homeCtrl'
            }))
            .state('user', angularAMD.route({
                url: '/user',
                templateUrl: 'assets/views/user/user.html',
                controllerUrl: 'assets/js/user/user'
            }))
            .state('ProductItem', angularAMD.route({
                url: '/product/:productId',
                templateUrl: 'assets/views/product/product-detail.html',
                controllerUrl: 'js/product/ProductItemCtrl'
            }))
            .state('cart', angularAMD.route({
                url: '/cart',
                templateUrl: 'assets/views/cart/cart.html',
                controllerUrl: 'assets/js/cart/cart'
            }))
            .state('order', angularAMD.route({
                url: '/order',
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

    angularAMD.bootstrap(app);
    return app;
});
