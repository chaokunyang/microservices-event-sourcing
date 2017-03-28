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

    app.factory('Order', ['$resource', function ($resource) {
        return $resource('/api/order/v1/orders/:orderId', {}, {

        })
    }]);
});