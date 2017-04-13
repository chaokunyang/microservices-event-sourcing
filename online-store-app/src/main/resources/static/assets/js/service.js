define(['angular-resource'], function () {
   'use strict';
    var app = angular.module('storeService', ['ngResource']);

    // $resource factory 依赖于 '$http', '$q'

    app.factory('Product', ['$resource', function ($resource) {
        return $resource('/api/catalog/v1/catalog', {}, {
            getCatalog: {
                url: '/api/catalog/v1/catalog',
                method: 'GET',
                isArray: false // 返回的
            },
            getProductByProductId: {
                url: 'api/catalog/v1/products/:productId',
                method: 'GET'
            }
        })
    }]);

    app.factory('Cart', ['$resource', function ($resource) {
        return $resource('/api/shoppingcart/v1/events', {}, {
            addCartEvent: {
                url: '/api/shoppingcart/v1/events',
                method: 'POST'
            }
        })
    }]);

    app.factory('Order', ['$resource', function ($resource) {
        return $resource('/api/order/v1/orders/:orderId', {}, {
            fetchOrders: {
                url: '/api/order/v1/accounts/:accountNumber/orders',
                method: 'GET',
                isArray: true
            },
            fetchOrder: {
                isArray: false
            }
        })
    }]);

    app.factory('Account', ['$resource', function ($resource) {
        return $resource('/api/account/v1/accounts', {}, {

        })
    }])
});