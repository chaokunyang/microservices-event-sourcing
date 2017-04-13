define(['js/app'], function (app) {
    'use strict';
    // console.log(app);
    app.register.controller('OrderListCtrl', ['$scope', 'Order', '$stateParams', 'Account', 'toaster', '$q', function ($scope, Order, $stateParams, Account, toaster, $q) {
        $scope.accounts = {};
        $scope.defaultAccount = {};

        var fetchAccounts = function () {
            return Account.query({}, function (data) {
                $scope.accounts = data;
                var account;
                for( var i = 0; i < $scope.accounts.length; i++) {
                    account = $scope.accounts[i];
                    if(account.defaultAccount) {
                        $scope.defaultAccount = account;
                        break;
                    }
                }
            }, function (error) {
                toaster.pop('error', '获取账户信息失败', error);
            })
        };

        var fetchOrders = function () {
            Order.fetchOrders({accountNumber: $scope.defaultAccount.accountNumber}, function (data) {
                $scope.orders = data;
                // 计算每张订单总金额
                for(var i = 0; i < $scope.orders.length; i++) {
                    var order = $scope.orders[i];
                    order.total = 0.0;

                    for(var j = 0; j < order.orderItems.length; j++) {
                        var orderItem = order.orderItems[j];
                        // 每个订单项金额 = (价格+税) * 数量
                        order.total += orderItem.price * (1 + orderItem.tax) * orderItem.quantity;
                    }
                }
            }, function (error) {
                toaster.pop('error', '获取订单列表失败', error);
            })
        };

        $q.all([fetchAccounts().$promise]).then(function () {
            fetchOrders(); // fetchOrders必须在fetchAccounts完成之后执行，因为它依赖defaultAccount的信息
        })

    }]);

    app.register.controller('OrderCtrl', ['$scope', 'Order', '$stateParams', 'toaster', function ($scope, Order, $stateParams, toaster) {
        var fetchOrder = function () {
            Order.fetchOrder({orderId: $stateParams.orderId}, function (data) {
                $scope.order = data;
                // 计算总金额
                $scope.order.total = 0.0;
                for(var i = 0; i < $scope.order.orderItems.length; i++) {
                    var orderItem = $scope.order.orderItems[i];
                    // 每个订单项金额 = (价格+税) * 数量
                    $scope.order.total += orderItem.price * (1 + orderItem.tax) * orderItem.quantity;
                }
            }, function (error) {
                toaster.pop('error', '获取订单信息失败', error);
            })
        };
        fetchOrder();
    }]);
});