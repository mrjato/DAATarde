define(['services/services'], function(services) {
    'use strict';

    services.factory('Article', ['$resource', function($resource) {
        return $resource('rest/articles/latest', { }, {
            findLatest: {
                method:  'GET',
                isArray: true,
            }
        });
    }]);

});
