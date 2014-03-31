define(['controllers/controllers'], function(controllers) {

    var articleList = ['$scope', 'articles', function($scope, articles) {
        articles.$promise.then(function(articles) {
            articles.forEach(function(article) {
                article.date = article.date.join('/');
            });
            $scope.articles = articles;
        });
    }];

    controllers.controller('ArticleListController', articleList);

});
