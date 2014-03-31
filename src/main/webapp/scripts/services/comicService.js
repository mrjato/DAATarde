define(['services/services'], function(services) {
    'use strict';

    services.factory('Comic', ['$resource', function($resource) {
        return $resource('rest/comics', { }, {
            search: {
                method:  'GET',
                params:  { search: '' },
                isArray: true,
            },
        });
    }]);

});
