// define(['js/app'], function (app) {
//     'use strict';
//     // console.log(app);
//     app.register.controller('ProductItemCtrl', ['$scope', '$http', '$templateCache', 'toaster', '$stateParams', 'Product', function ($scope, $http, $templateCache, toaster, $stateParams, Product) {
//         $scope.productItemUrl = 'api/catalog/v1/products/' + $stateParams.productId;
//         var fetchProduct = function () {
//             Product.getProductByProductId({productId: $stateParams.productId}, function (product) {
//                 $scope.product = product;
//             }, function (error) {
//                 toaster.pop('error', '获取产品信息失败', error);
//             });
//         };
//         fetchProduct();
//     }]);
//
// });