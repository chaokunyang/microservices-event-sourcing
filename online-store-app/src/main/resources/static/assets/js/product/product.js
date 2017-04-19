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

    app.register.controller('ProductItemCtrl', ['$scope', '$http', '$templateCache', 'toaster', '$stateParams', 'Product', '$q', function ($scope, $http, $templateCache, toaster, $stateParams, Product, $q) {
        $scope.productItemUrl = 'api/catalog/v1/products/' + $stateParams.productId;
        var fetchProduct = function () {
            return Product.getProductByProductId({productId: $stateParams.productId}, function (product) {
                $scope.product = product;
            }, function (error) {
                toaster.pop('error', '获取产品信息失败', error);
            });
        };

        // 获取相关商品
        $scope.products = [];
        var fetchRelatedProducts = function () {
            Product.getCatalog(function (catalog) {
                if($scope.product) {
                    var counter = 0, prod;
                    for(var i = 0; i < catalog.products.length; i++) {
                        prod = catalog.products[i];
                        // 当前产品不展示为相关产品
                        if(prod.productId != $scope.product.productId) {
                            $scope.products[counter++] = prod;
                        }
                        if(counter == 4) break; // 只展示4个相关产品
                    }
                }
            });
        };
        fetchRelatedProducts();
        $q.all([fetchProduct().$promise]).then(function () {
            fetchRelatedProducts();
        })
    }]);

});