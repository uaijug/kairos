'use strict';

kairosApp.factory('Speaker', function ($resource) {
        return $resource('app/rest/speakers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
