define(['js/app'], function (app) {
    'use strict';
    // console.log(app);
    app.controller('AccountCtrl', ['$scope', '$http', '$templateCache', function ($scope, $http) {
        var url = '/api/account/v1/accounts';
        $scope.accounts = {};

        var fetchAccounts = function () {
            $http({
                method: 'GET',
                url: url
            }).success(function (data) {
                $scope.accounts = data;
            }).error(function (data, status, headers, config) {
            });
        };

        fetchAccounts();
    }]);

    app.controller('UserCtrl', ['$scope', '$http', 'toaster', '$rootScope', '$location', function ($scope, $http, toaster, $rootScope, $location) {
        var meUrl = '/api/user/auth/v1/me';
        $scope.user = {};

        var fetchUser = function () {
            // $http.get(meUrl).success....
            $http({
                method: 'GET',
                url: meUrl
            }).success(function (data, status, headers, config) {
                $scope.user = data;
            }).error(function (data, status, headers, config) {
                toaster.pop('error', '获取用户信息失败', data);
            });
        };

        fetchUser();

        // 未登录无法获取用户信息，直接获取的话会重定向的Oauth2.0认证服务器登录页，从而得到的结果是登录页面HTML代码。而且这是两个url不同域（CORS），Oauth2.0认证服务器登录页并没有添加 Access-Control-Allow-Origin: http://localhost:8788，因此http://localhost:8787 并不能够获取到Oauth2.0认证服务器登录页html代码。
        // if(sessionStorage.getItem("authenticated")) {
        //     fetchUser();
        // }

        $scope.logout = function () {
            $http.post('logout', {}).success(function () {
                $rootScope.authenticated = false;
                $scope.user = {};
                $location.path("/");
                $rootScope.$broadcast('logout', "update");
            }).error(function (data) {
                console.log("Logout failed")
                $scope.user = {};
                $rootScope.$broadcast('logout', "update");
            });
        };
    }]);
});