define('js/app', function (app) {
    app.register.controller('ProductListCtrl', ['$scope', '$http', '$templateCache',
        function ($scope, $http, $templateCache) {
            $scope.url = '/api/catalog/v1/catalog';
            $scope.products = [];

            var fetchProducts = function () {
                $http({
                    method: 'GET',
                    url: $scope.url,
                    cache: $templateCache
                }).success(function (data) {
                    $scope.products = data.products;
                }).error(function (data, status, headers, config) {
                });
            };

            fetchProducts();
        }]);
    app.register.controller('AddToCartCtrl', ['$scope', '$http', function ($scope, $http) {
        $scope.qty = 0;
        $scope.productId = "";
        $scope.addToCart = function () {
            if ($scope.qty && $scope.qty > 0) {
                var req = {
                    method: 'POST',
                    url: '/api/shoppingcart/v1/events',
                    headers: {
                        'Content-Type': "application/json"
                    },
                    data: {
                        "cartEventType": "ADD_ITEM",
                        "productId": $scope.product.productId,
                        "quantity": $scope.qty
                    }
                };

                $http(req).then(function () {
                    $scope.qty = 0;
                    function showAlert() {
                        $("#addProductAlert").addClass("in");
                    }

                    function hideAlert() {
                        $("#addProductAlert").removeClass("in");
                    }

                    window.setTimeout(function () {
                        showAlert();
                        window.setTimeout(function () {
                            hideAlert();
                        }, 2000);
                    }, 20);
                });
            }
        };
    }]);
    app.register.controller('ProductItemCtrl', ['$scope', '$routeParams', '$http',
        function ($scope, $routeParams, $http) {
            $scope.productItemUrl = '/api/catalog/v1/products/' + $routeParams.productId;
            $scope.productsUrl = '/api/catalog/v1/catalog';
            $scope.products = [];

            $scope.$on('logout', function (event, msg) {
                fetchProduct();
            });

            var fetchProduct = function () {

                $http({
                    method: 'GET',
                    url: $scope.productsUrl
                }).success(function (data, status, headers, config) {
                    $scope.products = data.products;
                }).error(function (data, status, headers, config) {
                });

                $http({
                    method: 'GET',
                    url: $scope.productItemUrl
                }).success(function (data, status, headers, config) {
                    $scope.product = data;
                    $scope.product.poster_image = '/assets/img/posters/' + $scope.product.productId + '.png';

                }).error(function (data, status, headers, config) {
                });
            };

            fetchProduct();
        }]);
});