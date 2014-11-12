 'use strict';

kairosApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/event', {
                    templateUrl: 'views/events/list.html',
                    controller: 'EventController',
                    resolve:{
                        resolvedEvents: ['Event', function (Event) {
                            return Event.query().$promise;
                        }],
                        resolvedEvent: function () {
                            return {};
                        }
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                }).
                when('/event/create', {
                    templateUrl: 'views/events/form.html',
                    controller: 'EventController',
                    resolve:{
                        resolvedEvents: ['Event',function (Event) {
                            return {};
                        }],
                        resolvedEvent: function () {
                            return {id: null, name: null, description: null, asWillBe: null, startDate: null, endDate:null };
                        }

                    },
                    access: {
                        authorizedRoles: [USER_ROLES.admin]
                    }
                }).
                when('/event/:eventId', {
                    templateUrl: 'views/events/view.html',
                    controller: 'EventController',
                    resolve:{
                        resolvedEvents: ['Event', function (Event) {
                            return {};
                        }],
                        resolvedEvent: ['$route', 'Event', function ($route, Event) {
                            return Event.get({id: $route.current.params.eventId});
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                }).
                 when('/event/:eventId/edit', {
                    templateUrl: 'views/events/form.html',
                    controller: 'EventController',
                    resolve:{
                        resolvedEvents: ['Event', function (Event) {
                            return {};
                        }],
                        resolvedEvent: ['$route', 'Event', function ($route, Event) {
                            return Event.get({id: $route.current.params.eventId});
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.admin]
                    }
                })
        });
