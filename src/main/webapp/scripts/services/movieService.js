define(['services/services'], function(services) {
    'use strict';

    services.factory('Movie', ['$resource', function($resource) {
        return $resource('rest/movies', { }, {
            search: {
                method:  'GET',
                params:  { search: '' },
                isArray: true,
            },
        });
    }]);

});
