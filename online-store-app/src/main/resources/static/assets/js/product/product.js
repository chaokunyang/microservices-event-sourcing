define(['js/app'], function (app) {
    'use strict';
    // console.log(app);
    app.register.controller('ProductListCtrl', ['$scope', '$http', '$templateCache', 'toaster', 'Product', function ($scope, $http, $templateCache, toaster, Product) {
        $scope.products = [];
        var fetchProducts = function () {
            Product.getCatalog(function (catalog) {
                $scope.products = catalog.products;
            });
        };
        fetchProducts();
    }]);

    app.register.controller('AddToCartCtrl', ['$scope', '$http', 'Cart', 'toaster', '$timeout', function ($scope, $http, Cart, toaster, $timeout) {
        $scope.qty = 0;

        $scope.addToCart = function () {
            if ($scope.qty && $scope.qty > 0) {
                var cartEvent = {
                    'cartEventType': 'ADD_ITEM',
                    'productId': $scope.product.productId, // 访问parent scope 的属性
                    'quantity': $scope.qty
                };
                Cart.addCartEvent({}, cartEvent, function (res) {
                    console.log(res);
                    $scope.qty = 0;
                    function showAlert() {
                        $("#addToCartAlert").addClass("in");
                    }
                    function hideAlert() {
                        $("#addToCartAlert").removeClass("in");
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

    app.register.controller('ProductItemCtrl', ['$scope', '$http', '$templateCache', 'toaster', '$stateParams', 'Product', function ($scope, $http, $templateCache, toaster, $stateParams, Product) {
        $scope.productItemUrl = 'api/catalog/v1/products/' + $stateParams.productId;
        var fetchProduct = function () {
            Product.getProductByProductId({productId: $stateParams.productId}, function (product) {
                $scope.product = product;
            }, function (error) {
                toaster.pop('error', '获取产品信息失败', error);
            });
        };
        fetchProduct();
    }]);

});