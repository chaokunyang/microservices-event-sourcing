define(['js/app'], function (app) {
    'use strict';
    // console.log(app);
    app.register.controller('ProductItemCtrl', ['$scope', '$http', '$templateCache', 'toaster', '$stateParams', 'Product', function ($scope, $http, $templateCache, toaster, $stateParams, Product) {
        $scope.productItemUrl = 'api/catalog/v1/products/' + $stateParams.productId;
        var fetchProduct = function () {
            Product.getProductByProductId({productId: $stateParams.productId}, function (product) {
                $scope.product = product;
            }, function (error) {
                toaster.pop('error', '获取产品信息失败', error);
            });
        }
        fetchProduct();
    }]);

});

// contentApp.controller('ProductItemCtrl', ['$scope', '$routeParams', '$http',
//     function ($scope, $routeParams, $http) {
//         $scope.productItemUrl = '/api/catalog/v1/products/' + $routeParams.productId;
//         $scope.productsUrl = '/api/catalog/v1/catalog';
//         $scope.products = [];
//
//         $scope.$on('logout', function (event, msg) {
//             fetchProduct();
//         });
//
//         var fetchProduct = function () {
//
//             $http({
//                 method: 'GET',
//                 url: $scope.productsUrl
//             }).success(function (data, status, headers, config) {
//                 $scope.products = data.products;
//             }).error(function (data, status, headers, config) {
//             });
//
//             $http({
//                 method: 'GET',
//                 url: $scope.productItemUrl
//             }).success(function (data, status, headers, config) {
//                 $scope.product = data;
//                 $scope.product.poster_image = '/assets/img/posters/' + $scope.product.productId + '.png';
//
//             }).error(function (data, status, headers, config) {
//             });
//         };
//
//         fetchProduct();
//     }]);