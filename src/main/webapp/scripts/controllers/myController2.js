define(['controllers/controllers'], function(controllers) {

    controllers.controller('MyController2', ['$scope', function($scope) {
        $scope.welcome = 'Esto es MyController2';
    }]);

});
