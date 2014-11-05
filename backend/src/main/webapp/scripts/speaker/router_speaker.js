'use strict';

kairosApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/speaker', {
                    templateUrl: 'views/speakers/list.html',
                    controller: 'SpeakerController',
                    resolve:{
                        resolvedSpeakers: ['Speaker', function (Speaker) {
                            return Speaker.query();
                        }],
                        resolvedSpeaker: function () {
                            return {};
                        }
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                }).
                when('/speaker/create', {
                    templateUrl: 'views/speakers/form.html',
                    controller: 'SpeakerController',
                    resolve:{
                        resolvedSpeakers: function (Speaker) {
                            return {};
                        },
                        resolvedSpeaker: function () {
                            return {id: null, name: null, description: null};
                        }

                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                }).
                when('/speaker/:speakerId', {
                    templateUrl: 'views/speakers/view.html',
                    controller: 'SpeakerController',
                    resolve:{
                        resolvedSpeakers: ['Speaker', function (Speaker) {
                            return {};
                        }],
                        resolvedSpeaker: ['$route', 'Speaker', function ($route, Speaker) {
                            return Speaker.get({id: $route.current.params.speakerId});
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                }).
                 when('/speaker/:speakerId/update', {
                    templateUrl: 'views/speakers/form.html',
                    controller: 'SpeakerController',
                    resolve:{
                        resolvedSpeakers: ['Speaker', function (Speaker) {
                            return {};
                        }],
                        resolvedSpeaker: ['$route', 'Speaker', function ($route, Speaker) {
                            return Speaker.get({id: $route.current.params.speakerId});
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
