define(['services/services'], function(services) {
    'use strict';

    services.factory('Music', ['$resource', function($resource) {
        return $resource('rest/musicstorages', { }, {
            search: {
                method:  'GET',
                params:  { search: '' },
                isArray: true,
            },
        });
    }]);

});
