define(['controllers/controllers'], function(controllers) {

    controllers.controller('MyController1', ['$scope', function($scope) {
        $scope.sum = 1 + 2;
    }]);

});
