define(['controllers/controllers'], function(controllers) {

    var articleList = ['$scope', 'articles', function($scope, articles) {
        articles.$promise.then(function(articles) {
            articles.forEach(function(article) {
                article.date = article.date.join('/');
            });
            $scope.articles = articles;
        });
    }];

    var articleAdd = ['$scope', '$resource', '$location',
        function($scope, $resource, $location) {
            $scope.categories = [
                { name: 'Libros',    path: 'books'         },
                { name: 'Cómics',    path: 'comics'        },
                { name: 'Películas', path: 'movies'        },
                { name: 'Música',    path: 'musicstorages' },
            ];

            $scope.add = function( ) {
                $resource('rest/' + $scope.category.path).save(
                    $scope.article
                );
                $location.path("/" + $scope.category.path);
            };
        }
    ];

    var articleVerify = ['$scope', 'articles', function($scope, articles) {
        articles.$promise.then(function(articles) {
            articles.forEach(function(article) {
                article.date = article.date.join('/');
            });
            $scope.articles = articles;
        });
    }];

    controllers
        .controller('ArticleListController',   articleList)
        .controller('ArticleAddController',    articleAdd)
        .controller('ArticleVerifyController', articleVerify);

});
