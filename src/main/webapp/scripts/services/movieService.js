define(['services/services'], function(services) {
    'use strict';

    services.factory('Movie', ['$resource', function($resource) {
        return $resource('rest/articles/movies', { }, {
            search: {
                method:  'GET',
                params:  { search: '' },
                isArray: true,
            },
        });
    }]);

});
