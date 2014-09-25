'use strict';

kairosApp.controller('SpeakerController', function ($scope, resolvedSpeaker, Speaker) {

        $scope.speakers = resolvedSpeaker;

        $scope.create = function () {
            Speaker.save($scope.speaker,
                function () {
                    $scope.speakers = Speaker.query();
                    $('#saveSpeakerModal').modal('hide');
                    $scope.clear();
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
