define(['js/app'], function (app) {
    'use strict';
    // console.log(app);
    app.controller('homeController', ['$scope', '$state', function ($scope, $state) {
        $scope.isTabActive = function (tabName) {
            // Check if there is sub-states
            var stateName = $state.current.name,
                subStatePos = stateName.indexOf('.');

            if (subStatePos > -1) {
                stateName = stateName.substring(0,subStatePos);
            }

            if (tabName === stateName) {
                return 'active';
            }
        };
    }]);

});
