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

});
