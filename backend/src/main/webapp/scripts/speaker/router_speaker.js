'use strict';

kairosApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/speaker', {
                    templateUrl: 'views/speakers/list.html',
                    controller: 'SpeakerController',
                    resolve:{
                        resolvedSpeaker: ['Speaker', function (Speaker) {
                            return Speaker.query();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
