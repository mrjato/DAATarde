define(['services/services'], function(services) {
    'use strict';

    services.factory('Article', ['$resource', function($resource) {
        return $resource('rest/articles', { }, {
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
            latest: {
                method:  'GET',
                url:     'rest/articles/latest',
                isArray: true,
            },
        });
    }]);

});
