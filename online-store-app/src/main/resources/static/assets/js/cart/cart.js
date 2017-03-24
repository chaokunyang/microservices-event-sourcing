define('js/app', function (app) {
    app.register.controller('CartCtrl', ['$scope', '$http', '$templateCache',
        function ($scope, $http) {
            $scope.url = '/api/shoppingcart/v1/cart';
            $scope.cart = {};

            $scope.$on('cartEvents', function (event, msg) {
                fetchCart();
            });

            var fetchCart = function () {
                $http({
                    method: 'GET',
                    url: $scope.url
                }).success(function (data) {
                    $scope.cart = data;
                    $scope.cart.total = 0;
                    $scope.cart.totalItems = 0;
                    for (var i = 0; i < $scope.cart.lineItems.length; i++) {
                        $scope.cart.lineItems[i].posterImage = '/assets/img/posters/' + $scope.cart.lineItems[i].product.productId + '.png';
                        $scope.cart.lineItems[i].originalQuantity = $scope.cart.lineItems[i].quantity;
                        $scope.cart.total += $scope.cart.lineItems[i].quantity * $scope.cart.lineItems[i].product.unitPrice;
                        $scope.cart.totalItems += $scope.cart.lineItems[i].quantity;
                    }
                }).error(function (data, status, headers, config) {
                });
            };

            fetchCart();
        }]);
    app.register.controller('CheckoutCtrl', ['$scope', '$http', '$location', 'orderService', function ($scope, $http, $location, orderService) {
        $scope.checkout = function () {
            var req = {
                method: 'POST',
                url: '/api/shoppingcart/v1/checkout',
                headers: {
                    'Content-Type': "application/json"
                },
                data: {}
            };

            $http(req).success(function (data, status, headers, config) {
                if (data.order == null) {
                    alert(data.resultMessage);
                } else {
                    $scope.order = data.order;
                    console.log(data.order);
                    orderService.set(data.order);
                    $location.path('/orders/' + $scope.order.orderId);
                }
            }).error(function (data, status, headers, config) {
                alert("Checkout could not be completed");
            });
        };
    }]);
    app.register.controller('UpdateCartCtrl', ['$rootScope', '$scope', '$http', '$location', function ($rootScope, $scope, $http, $location) {
        $scope.productId = "";

        $scope.updateCart = function () {
            var delta = 0;
            console.log($scope);
            if ($scope.item.quantity >= 0 && $scope.item.originalQuantity > 0 &&
                $scope.item.quantity != $scope.item.originalQuantity) {
                var updateCount = $scope.item.quantity - $scope.item.originalQuantity;
                delta = Math.abs(updateCount);
                if (delta >= 0) {
                    var req = {
                        method: 'POST',
                        url: '/api/shoppingcart/v1/events',
                        headers: {
                            'Content-Type': "application/json"
                        },
                        data: {
                            "cartEventType": updateCount <= 0 ? "REMOVE_ITEM" : "ADD_ITEM",
                            "productId": $scope.item.productId,
                            "quantity": delta
                        }
                    };

                    var selector = "#updateProductAlert." + $scope.item.productId;

                    $http(req).then(function () {

                        if (updateCount <= 0) {
                            $rootScope.$broadcast('cartEvents', "update");
                        }

                        $scope.item.originalQuantity = $scope.item.quantity;

                        function showAlert() {
                            $(selector).find("p").text("Cart updated");
                            $(selector).removeClass("alert-error")
                                .addClass("alert-success")
                                .addClass("in");
                        }

                        function hideAlert() {
                            $(selector).removeClass("in");
                        }

                        window.setTimeout(function () {
                            showAlert();
                            window.setTimeout(function () {
                                hideAlert();
                            }, 2000);
                        }, 20);
                    });
                }
            } else {
                $rootScope.item.quantity = $scope.item.originalQuantity;
                if ($scope.item.quantity <= 0) {
                    $scope.$broadcast('cartEvents', "update");
                }
                window.setTimeout(function () {
                    $(selector).find("p").text("Invalid quantity");
                    $(selector).removeClass("alert-success")
                        .addClass("alert-error")
                        .addClass("in");
                    window.setTimeout(function () {
                        $(selector).removeClass("in");
                    }, 2000);
                }, 20);
            }
        };
    }]);
});