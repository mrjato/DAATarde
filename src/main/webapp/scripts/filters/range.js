define(['filters/filters'], function(filters) {
    'use strict';

    filters.filter('range', function() {
        return function(input, total) {
            total = parseInt(total, 10);
            for (var i = 1; i < total + 1; i++)
                input.push(i);
            return input;
        };
    });

});
