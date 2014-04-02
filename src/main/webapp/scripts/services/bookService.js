define(['services/services'], function(services) {
    'use strict';

    services.factory('Book', ['$resource', function($resource) {
        return $resource('rest/books', { }, {
            search: {
                method:  'GET',
                params:  { search: '' },
                isArray: true,
            }
        });
    }]);

});
