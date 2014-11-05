'use strict';

kairosApp.controller('EventController', function ($scope, resolvedEvent, Event, resolvedSpeaker) {

        $scope.events = resolvedEvent;
        $scope.speakers = resolvedSpeaker;

        $scope.create = function () {
            Event.save($scope.event,
                function () {
                    $scope.events = Event.query();
                    $('#saveEventModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.event = Event.get({id: id});
            $('#saveEventModal').modal('show');
        };

        $scope.delete = function (id) {
            Event.delete({id: id},
                function () {
                    $scope.events = Event.query();
                });
        };

        $scope.clear = function () {
            $scope.event = {name: null, description: null, asWillBe: null, startDate: null, endDate: null, id: null};
        };
    });
