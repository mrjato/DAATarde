define(['controllers/controllers'], function(controllers) {

    var homeLatest = ['$scope', 'articles', function($scope, articles) {
        articles.$promise.then(function(articles) {
            articles.forEach(function(article) {
                article.date = article.date.join('/');
            });
            $scope.articles = articles;
        });
    }];

    controllers.controller('HomeLatestController', homeLatest);

});
