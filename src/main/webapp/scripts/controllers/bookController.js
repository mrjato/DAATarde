define(['controllers/controllers'], function(controllers) {

    var bookList = ['$scope', 'books', function($scope, books) {
        books.$promise.then(function(books) {
            books.forEach(function(book) {
                book.date = book.date.join('/');
            });
            $scope.books = books;
        });
    }];

    controllers.controller('BookListController', bookList);

});
