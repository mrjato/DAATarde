define([ 'app' ], function(app) {
    'use strict';

    app.config(['$routeProvider', function($routeProvider) {

        // latest added articles
        $routeProvider.when('/', {
            templateUrl: 'partials/home.html',
            controller:  'HomeLatestController',
            resolve:     {
                articles: function(Article) { return Article.latest(); }
            }
        });

        // add new article
        $routeProvider.when('/articles/add', {
            templateUrl: 'partials/articleAdd.html',
            controller:  'ArticleAddController'
        });

        // list different article types
        $routeProvider.when('/articles', {
            templateUrl: 'partials/articleList.html',
            controller:  'ArticleListController',
            resolve :    {
                articles: function(Article) {return Article.query(); }
            }
        });
        $routeProvider.when('/books', {
            templateUrl: 'partials/articleList.html',
            controller:  'ArticleListController',
            resolve:     {
                articles: function(Book) { return Book.query(); }
            }
        });
        $routeProvider.when('/comics', {
            templateUrl: 'partials/articleList.html',
            controller:  'ArticleListController',
            resolve:     {
                articles: function(Comic) { return Comic.query(); }
            }
        });
        $routeProvider.when('/movies', {
            templateUrl: 'partials/articleList.html',
            controller:  'ArticleListController',
            resolve:     {
                articles: function(Movie) { return Movie.query(); }
            }
        });
        $routeProvider.when('/music', {
            templateUrl: 'partials/articleList.html',
            controller:  'ArticleListController',
            resolve:     {
                articles : function(Music) { return Music.query(); }
            }
        });

        // search different article types
        $routeProvider.when('/articles/search', { redirectTo : '/articles' });
        $routeProvider.when('/articles/search/:terms*', {
            templateUrl: 'partials/articleList.html',
            controller:  'ArticleListController',
            resolve:     {
                articles: function($route, Article) { return Article.search({
                    search: $route.current.params.terms,
                }); }
            },
        });
        $routeProvider.when('/books/search', { redirectTo : '/books' });
        $routeProvider.when('/books/search/:terms*', {
            templateUrl: 'partials/articleList.html',
            controller:  'ArticleListController',
            resolve:     {
                articles: function($route, Book) { return Book.search({
                    search: $route.current.params.terms
                }); }
            },
        });
        $routeProvider.when('/comics/search', { redirectTo : '/comics' });
        $routeProvider.when('/comics/search/:terms*', {
            templateUrl: 'partials/articleList.html',
            controller:  'ArticleListController',
            resolve:     {
                articles: function($route, Comic) { return Comic.search({
                    search: $route.current.params.terms
                }); }
            },
        });
        $routeProvider.when('/movies/search', { redirectTo : '/movies' });
        $routeProvider.when('/movies/search/:terms*', {
            templateUrl: 'partials/articleList.html',
            controller:  'ArticleListController',
            resolve:     {
                articles: function($route, Movie) { return Movie.search({
                    search: $route.current.params.terms
                }); }
            },
        });
        $routeProvider.when('/music/search', { redirectTo : '/music' });
        $routeProvider.when('/music/search/:terms*', {
            templateUrl: 'partials/articleList.html',
            controller:  'ArticleListController',
            resolve:     {
                articles: function($route, Music) { return Music.search({
                    search : $route.current.params.terms
                }); }
            },
        });

        // if none matches, redirect to home
        $routeProvider.otherwise({ redirectTo : '/' });

    } ]);

});
