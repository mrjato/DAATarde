define(['services/services'], function(services) {
    'use strict';

    services.factory('Article', ['$resource', function($resource) {
        return $resource('rest/articles', { }, {
            search: {
                method:  'GET',
                params:  { search: '' },
                isArray: true,
            }
        });
    }]);

});
