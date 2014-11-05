'use strict';

kairosApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/event', {
                    templateUrl: 'views/events.html',
                    controller: 'EventController',
                    resolve:{
                        resolvedEvent: ['Event', function (Event) {
                            return Event.query().$promise;
                        }],
                        resolvedSpeaker: ['Speaker', function (Speaker) {
                            return Speaker.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
