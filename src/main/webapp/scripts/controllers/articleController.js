define(['controllers/controllers'], function(controllers) {

    var articleList = ['$scope', 'articles', function($scope, articles) {
        articles.$promise.then(function(articles) {
            articles.forEach(function(article) {
                article.date = article.date.join('/');
            });
            $scope.articles = articles;
        });
    }];

    var articleAdd = [
        '$scope', '$location', 'Book', 'Comic', 'Movie', 'Music',
        function($scope, $location, Book, Comic, Movie, Music) {
            $scope.categories = [
                { name: 'Libros',    type: 'book',  service: Book  },
                { name: 'Cómics',    type: 'comic', service: Comic },
                { name: 'Películas', type: 'movie', service: Movie },
                { name: 'Música',    type: 'music', service: Music },
            ];

            $scope.add = function(category) {
                $scope.article.type = category.type;
                $scope.article.date = $scope.article.date.split('/').map(
                    function(num) { return parseInt(num, 10); }
                );

                category.service.save({ }, $scope.article);

                // maybe better to redirect and show message in the redirected
                // page (a "flash" message), instead of using a plain old alert?
                window.alert('Artículo ' + $scope.article.name + ' pendiente de moderación.');
                $location.path("/");
            };
        }
    ];

    controllers.controller('ArticleListController', articleList)
               .controller('ArticleAddController',  articleAdd);

});
