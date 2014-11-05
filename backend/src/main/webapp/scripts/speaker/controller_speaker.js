'use strict';

kairosApp.controller('SpeakerController', function ($scope, $location, resolvedSpeaker, resolvedSpeakers, Speaker) {

        $scope.speakers = resolvedSpeakers;
        $scope.speaker = resolvedSpeaker;

        $scope.create = function () {
            Speaker.save($scope.speaker,
                function () {
                    $scope.speakers = Speaker.query();
                    $location.path("speaker");
                });
        };

        $scope.update = function (id) {
            $scope.speaker = Speaker.get({id: id});
            $('#saveSpeakerModal').modal('show');
        };

        $scope.delete = function (id) {
            Speaker.delete({id: id},
                function () {
                    $scope.speakers = Speaker.query();
                });
        };

        $scope.clear = function () {
            $scope.speaker = {id: null, name: null, description: null};
        };
    });
