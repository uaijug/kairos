'use strict';

kairosApp.factory('Event', function ($resource) {
        return $resource('app/rest/events/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
