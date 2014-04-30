define(['controllers/controllers'], function(controllers) {
    'use strict';

    controllers.controller(
        'SearchController',
        ['$scope', '$location', function($scope, $location) {

            $scope.terms = '';

            $scope.categories = [
                { name: 'Todo',    	 path: 'articles'  },
                { name: 'Libros',    path: 'books'  },
                { name: 'Cómics',    path: 'comics' },
                { name: 'Películas', path: 'movies' },
                { name: 'Música',    path: 'music'  },
            ];
            $scope.selectedCategory = $scope.categories[0];
            
            $scope.search = function(category) {
                $location.path("/" + category.path + "?search=" + $scope.terms);
            };

            $scope.enter = function(ev) {
            	 if (ev.which==13){
            		 if ($scope.selectedCategory.name == 'Todo') {
            			 $scope.selectedCategory = $scope.categories[0];
            		 }
            	 $location.path("/" + $scope.selectedCategory.path + "?search=" + $scope.terms);
            	 }
            };

        }]
    );

});
