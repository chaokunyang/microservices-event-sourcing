define('js/app', function (app) {
    app.register.controller('OrderListCtrl', ['$scope', '$http', '$location', 'orderService', '$routeParams',
        function ($scope, $http, $location, orderService, $routeParams) {
            // Get account
            $scope.accountsUrl = '/api/account/v1/accounts';
            $scope.accounts = {};

            var fetchAccounts = function () {
                $http({
                    method: 'GET',
                    url: $scope.accountsUrl
                }).success(function (accountData) {
                    $scope.accounts = accountData;
                    var defaultAccount = {};
                    // Get default account
                    for (var i = 0; i < $scope.accounts.length; i++) {
                        if ($scope.accounts[i].defaultAccount) {
                            defaultAccount = $scope.accounts[i];
                        }
                    }

                    $scope.orderListUrl = '/api/order/v1/accounts/' + defaultAccount.accountNumber + "/orders";

                    var fetchOrders = function () {
                        $http({
                            method: 'GET',
                            url: $scope.orderListUrl
                        }).success(function (orderData, status, headers, config) {
                            $scope.orders = orderData;
                            // Calculate total
                            for (var j = 0; j < $scope.orders.length; j++) {
                                $scope.orders[j].total = 0.0;
                                for (var i = 0; i < $scope.orders[j].lineItems.length; i++) {
                                    $scope.orders[j].total += (($scope.orders[j].lineItems[i].price)
                                        + ($scope.orders[j].lineItems[i].price * $scope.orders[j].lineItems[i].tax))
                                        * $scope.orders[j].lineItems[i].quantity;
                                }
                            }
                        }).error(function (data, status, headers, config) {
                            $location.path('/');
                        });
                    };

                    fetchOrders();

                }).error(function (data, status, headers, config) {
                });
            };

            fetchAccounts();


        }]);
    app.register.controller('OrderCtrl', ['$scope', '$http', '$location', 'orderService', '$routeParams',
        function ($scope, $http, $location, orderService, $routeParams) {
            $scope.orderItemUrl = '/api/order/v1/orders/' + $routeParams.orderId;
            var fetchOrder = function () {
                $http({
                    method: 'GET',
                    url: $scope.orderItemUrl
                }).success(function (data, status, headers, config) {
                    $scope.order = data;
                    // Calculate total
                    $scope.order.total = 0.0;
                    for (var i = 0; i < $scope.order.lineItems.length; i++) {
                        $scope.order.total += (($scope.order.lineItems[i].price)
                            + ($scope.order.lineItems[i].price * $scope.order.lineItems[i].tax))
                            * $scope.order.lineItems[i].quantity;
                    }
                }).error(function (data, status, headers, config) {
                    $location.path('/');
                });
            };

            fetchOrder();
        }]);
});