define(['services/services'], function(services) {
    'use strict';

    services.factory('Book', ['$resource', function($resource) {
        return $resource('rest/articles/books', { }, {
            search: {
                method:  'GET',
                params:  { search: '' },
                isArray: true,
            }
        });
    }]);

});
