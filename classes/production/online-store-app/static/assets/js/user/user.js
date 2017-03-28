define(['js/app'], function (app) {
    'use strict';
    // console.log(app);
    app.controller('AccountCtrl', ['$scope', '$http', '$templateCache', function ($scope, $http) {
        $scope.url = '/api/account/v1/accounts';
        $scope.accounts = {};

        var fetchAccounts = function () {
            $http({
                method: 'GET',
                url: $scope.url
            }).success(function (data) {
                $scope.accounts = data;
            }).error(function (data, status, headers, config) {
            });
        };

        fetchAccounts();
    }]);

    app.controller('UserCtrl', ['$scope', '$http', function ($scope, $http) {
        $scope.authUrl = '/api/user/auth/v1/me';
        $scope.meUrl = '/api/user/auth/v1/me';
        $scope.user = {};

        $scope.logout = function () {
            $http.post('logout', {}).success(function () {
                $rootScope.authenticated = false;
                $scope.user = {};
                $location.path("/");
                $location.reload($location.path);
                $rootScope.$broadcast('logout', "update");
            }).error(function (data) {
                $scope.user = {};
                $rootScope.$broadcast('logout', "update");
            });
        };

        var fetchUser = function () {
            $http({
                method: 'GET',
                url: $scope.authUrl
            }).success(function (data, status, headers, config) {
                $http({
                    method: 'GET',
                    url: $scope.meUrl
                }).success(function (data, status, headers, config) {
                    $scope.user = data;
                }).error(function (data, status, headers, config) {
                });
                $scope.user = data;
            }).error(function (data, status, headers, config) {
                $scope.user = {};
            });
        };

        fetchUser();
    }]);
});