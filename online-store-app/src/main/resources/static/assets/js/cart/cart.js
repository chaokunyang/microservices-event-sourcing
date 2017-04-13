define(['js/app'], function (app) {
    'use strict';
    // console.log(app);
    app.register.controller('CartCtrl', ['$scope', '$http', '$templateCache', 'toaster', function ($scope, $http, toaster) {
        var url = '/api/shoppingcart/v1/cart';
        $scope.cart = {};
        
        var fetchCart = function () {
            $http.get(url).success(function (data) {
                $scope.cart = data;
                $scope.cart.total = 0;
                $scope.cart.totalItems = 0;

                for(var i = 0; i < $scope.cart.cartItems.length; i++) {
                    var cartItem = $scope.cart.cartItems[i];
                    cartItem.originalQuantity = cartItem.quantity;
                    $scope.cart.totalItems += cartItem.quantity;
                    $scope.cart.total += cartItem.quantity * cartItem.product.unitPrice;
                }
            }).error(function (error) {
                toaster.pop('error', '获取购物车失败', error);
            });
        };

        fetchCart();

        // 用于其他组件更新购物车，如UpdateCartCtrl
        $scope.$on('cartEvents', function (event, msg) {
            fetchCart();
        });

    }]);

    app.register.controller('CheckoutCtrl', ['$scope', '$http', '$location', 'toaster', '$state', function ($scope, $http, $location, toaster, $state) {
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
                    alert(data.resultMessage); // resultMessage是检出信息，对应CheckoutResult.resultMessage
                } else {
                    $scope.order = data.order;
                    // console.log(data.order);
                    toaster.pop('success', data.resultMessage);

                    // $location.path('/orders/' + $scope.order.orderId);
                    $state.go('order', {orderId: $scope.order.orderId});
                }
            }).error(function (data, status, headers, config) {
                toaster.pop('error', '检出购物车失败', data);
            });
        };
    }]);

    app.register.controller('UpdateCartCtrl', ['$rootScope', '$scope', '$http', function ($rootScope, $scope, $http) {
      $scope.updateCart = function () {
          var selector = '#updateProductAlert-' + $scope.item.productId;
          var delta = 0;

          // console.log($scope);
          // $scope可以获取$rootScope的属性
          // originalQuantity等于0表示已经从购物车删除
          if($scope.item.quantity >= 0 && $scope.item.originalQuantity > 0 &&
              $scope.item.quantity != $scope.item.originalQuantity) {
              var updateCount = $scope.item.quantity - $scope.item.originalQuantity;
              delta = Math.abs(updateCount);

              var req = {
                  method: 'POST',
                  url: '/api/shoppingcart/v1/events',
                  headers: {
                      'Content-Type': 'application/json'
                  },
                  data: {
                      'cartEventType': updateCount <= 0 ? 'REMOVE_ITEM' : 'ADD_ITEM',
                      'productId': $scope.item.productId,
                      'quantity': delta
                  }
              };

              $http(req).then(function () {
                  $rootScope.$broadcast('cartEvents', 'update'); // 更新购物车，获取新的产品数量。从而使当前项和产品总数产生变化
                  $scope.item.originalQuantity = $scope.item.quantity;

                  function showAlert() {
                      $(selector).find('p').text('更新成功');
                      $(selector).addClass('alert-success').addClass('in');
                  }

                  function hideAlert() {
                      $(selector).removeClass('alert-success in');
                  }

                  window.setTimeout(function () {
                      showAlert();
                      window.setTimeout(function () {
                          hideAlert();
                      }, 2000);
                  }, 20);
              })
          } else {
              window.setTimeout(function () {
                  $(selector).find('p').text('无效的购买数量');
                  $(selector).addClass('alert-error in');
                  window.setTimeout(function () {
                      $(selector).removeClass('alert-error in');
                  }, 2000);
              }, 20)
          }
      }
    }]);

});