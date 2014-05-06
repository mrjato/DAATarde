define(['services/services'], function(services) {
    'use strict';

    services.factory('Movie', ['$resource', function($resource) {
        return $resource('rest/articles/movies', { }, {
            search: {
                method:  'GET',
                params:  { search: '', page: '' },
                isArray: true,
            },
            countSearch: {
                method:  'GET',
                params:  { search: '', count: true },
                isArray: false,
            },
        });
    }]);

});
