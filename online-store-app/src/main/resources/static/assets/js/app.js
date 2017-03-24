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
            // .state('user', angularAMD.route({
            //     url: 'user',
            //     templateUrl: 'assets/views/user/user.html',
            //     controllerUrl: 'assets/js/user/userController'
            // }))
            // .state('product', angularAMD.route({
            //     url: 'product',
            //     templateUrl: 'assets/views/product/product.html',
            //     controllerUrl: 'assets/js/product/productController'
            // }))
            // .state('cart', angularAMD.route({
            //     url: 'cart',
            //     templateUrl: 'assets/views/cart/cart.html',
            //     controllerUrl: 'assets/js/cart/cartController'
            // }))
            // .state('order', angularAMD.route({
            //     url: 'order',
            //     templateUrl: 'assets/views/order.html',
            //     controllerUrl: 'assets/js/order/orderController'
            // }));

        // Else
        $urlRouterProvider.otherwise('/home');
    }]);

    angularAMD.bootstrap(app);
    return app;
});
