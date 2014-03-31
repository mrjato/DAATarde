define(['app'], function(app) {
    'use strict';

    app.config(['$routeProvider', function($routeProvider) {

        $routeProvider.when('/', {
            templateUrl: 'partials/home.html',
            controller: 'HomeController'
        });

        // temporal while listing not implemented in REST API: listing redirects
        // to an empty search
        $routeProvider.when('/books', {
            templateUrl: 'partials/bookList.html',
            controller: 'BookListController',
            resolve: {
                books: function(Book) { return Book.search(); }
            }
        });

        $routeProvider.when('/books/search', { redirectTo: '/books' });
        $routeProvider.when('/books/search/:terms*', {
            templateUrl: 'partials/bookList.html',
            controller: 'BookListController',
            resolve: {
                books: function($route, Book) {
                    return Book.search({ search: $route.current.params.terms });
                }
            },
        });

        $routeProvider.otherwise({ redirectTo: '/' });

    }]);

});
