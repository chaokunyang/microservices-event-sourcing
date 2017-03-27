define(['js/app'], function (app) {
    'use strict';
    // console.log(app);
    app.register.controller('homeCtrl', ['$scope', '$http', '$templateCache', 'toaster', 'Product', function ($scope, $http, $templateCache, toaster, Product) {
        $scope.products = [];
        var fetchProducts = function () {
            Product.getCatalog(function (catalog) {
                $scope.products = catalog.products;
            });
        };
        fetchProducts();
    }]);

});
