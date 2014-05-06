define(['services/services'], function(services) {
    'use strict';

    services.factory('Music', ['$resource', function($resource) {
        return $resource('rest/articles/music', { }, {
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
