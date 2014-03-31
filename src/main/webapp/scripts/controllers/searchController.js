define(['controllers/controllers'], function(controllers) {
    'use strict';

    controllers.controller(
        'SearchController',
        ['$scope', '$location', function($scope, $location) {

            $scope.terms = '';

            $scope.categories = [
                { name: 'Libros', path: 'books' },
                // { name: 'Cómics', path: 'comics' },
                // { name: 'Películas', path: 'movies' },
                // { name: 'Música', path: 'music' },
            ];

            $scope.search = function(category) {
                $location.path("/" + category.path + "/search/" + $scope.terms);
            };

        }]
    );

});
