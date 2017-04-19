define(['js/app'], function (app) {
    'use strict';
    app.config(['$httpProvider', function ($httpProvider) {
            // responseInterceptors 在1.2.6版本存在，在1.3.8已经不存在了，取而代之的是interceptors。如果继续使用原来的responseInterceptors会报错，导致模块加载失败
            $httpProvider.interceptors.push('httpInterceptor');
            var loading = function (data, headersGetter) {
                $('#loading').show();
                return data;
            };
            $httpProvider.defaults.transformRequest.push(loading);
        }])
        .factory('httpInterceptor', ['$q', function ($q) {
            return {
                request: function (req) {
                    $('#loading').show();
                    return req;
                },
                requestError: function (rejection) {
                    $('#loading').hide();
                    return $q.reject(rejection);
                },
                response: function (res) {
                    $('#loading').hide();
                    // console.log(res.headers());
                    if(res.headers()['authenticated']) {
                        sessionStorage.setItem("authenticated", true);
                    }else {
                        sessionStorage.removeItem("authenticated");
                    }
                    return res;
                },
                responseError: function (rejection) {
                    $('#loading').hide();
                    return rejection;
                }
            }
        }])

    app.controller('AddToCartCtrl', ['$scope', '$http', 'Cart', 'toaster', '$timeout', function ($scope, $http, Cart, toaster, $timeout) {
        $scope.qty = 1;

        $scope.addToCart = function () {
            if ($scope.qty && $scope.qty > 0) {
                var cartEvent = {
                    'cartEventType': 'ADD_ITEM',
                    'productId': $scope.product.productId, // 访问parent scope 的属性
                    'quantity': $scope.qty
                };
                Cart.addCartEvent({}, cartEvent, function (res) {
                    console.log(res);
                    $scope.qty = 1;
                    function showAlert() {
                        $("#add-to-cart-alert-" + $scope.product.productId).addClass("in");
                    }
                    function hideAlert() {
                        $("#add-to-cart-alert-" + $scope.product.productId).removeClass("in");
                    }
                    $timeout(function () {
                        showAlert();
                        $timeout(function () {
                            hideAlert()
                        }, 2000, false); // false表示跳过 model dirty checking
                    }, 20, false)
                }, function (error) {
                    toaster.pop('error', '添加产品到购物车失败', error);
                });
            }
        };
    }]);
});