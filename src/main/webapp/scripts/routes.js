define(['app'], function(app) {
    'use strict';

    app.config(['$routeProvider', function($routeProvider) {

        $routeProvider.when('/', {
            templateUrl: 'partials/home.html',
            controller: 'HomeController'
        });

        // temporal while listing not implemented in REST API: listing redirects
        // to an empty search
        
        $routeProvider.when('/articleAdd', {
            templateUrl: 'partials/articleAdd.html',
            controller: 'articleController'
        });
        
        $routeProvider.when('/articleVerify', {
            templateUrl: 'partials/articleVerify.html',
            controller: 'articleController'
        });
        
        $routeProvider.when('/books', {
            templateUrl: 'partials/articleList.html',
            controller: 'ArticleListController',
            resolve: { articles: function(Book) { return Book.search(); } }
        });
        $routeProvider.when('/comics', {
            templateUrl: 'partials/articleList.html',
            controller: 'ArticleListController',
            resolve: { articles: function(Comic) { return Comic.search(); } }
        });
        $routeProvider.when('/movies', {
            templateUrl: 'partials/articleList.html',
            controller: 'ArticleListController',
            resolve: { articles: function(Movie) { return Movie.search(); } }
        });
        $routeProvider.when('/music', {
            templateUrl: 'partials/articleList.html',
            controller: 'ArticleListController',
            resolve: { articles: function(Music) { return Music.search(); } }
        });

        $routeProvider.when('/books/search', { redirectTo: '/books' });
        $routeProvider.when('/books/search/:terms*', {
            templateUrl: 'partials/articleList.html',
            controller: 'ArticleListController',
            resolve: { articles: function($route, Book) { return Book.search({ search: $route.current.params.terms }); }
            },
        });
        $routeProvider.when('/comics/search', { redirectTo: '/comics' });
        $routeProvider.when('/comics/search/:terms*', {
            templateUrl: 'partials/articleList.html',
            controller: 'ArticleListController',
            resolve: { articles: function($route, Comic) { return Comic.search({ search: $route.current.params.terms }); }
            },
        });
        $routeProvider.when('/movies/search', { redirectTo: '/movies' });
        $routeProvider.when('/movies/search/:terms*', {
            templateUrl: 'partials/articleList.html',
            controller: 'ArticleListController',
            resolve: { articles: function($route, Movie) { return Movie.search({ search: $route.current.params.terms }); }
            },
        });
        $routeProvider.when('/music/search', { redirectTo: '/music' });
        $routeProvider.when('/music/search/:terms*', {
            templateUrl: 'partials/articleList.html',
            controller: 'ArticleListController',
            resolve: { articles: function($route, Music) { return Music.search({ search: $route.current.params.terms }); }
            },
        });

        $routeProvider.otherwise({ redirectTo: '/' });

    }]);

});
