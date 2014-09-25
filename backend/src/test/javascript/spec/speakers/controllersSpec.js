'use strict';

describe('Speaker Controllers Tests ', function () {

    beforeEach(module('kairosApp'));

    describe('SpeakerController', function () {
        var $scope, SpeakerService;

        beforeEach(inject(function ($rootScope, $controller, Speaker) {
            $scope = $rootScope.$new();
            SpeakerService = Speaker;
            $controller('SpeakerController', {$scope: $scope, Speaker:SpeakerService});
        }));

        it('should save speaker', function(){
        	$scope.speaker.name = 'Teste';
        	$scope.speaker.description = 'Teste desc';
        	
        	spyOn(SpeakerService,'create' );
        	
        	$scope.create();
        	
        	expect(SpeakerService.create()).toHaveBeenCalled();        	
        	
        });
       
    });

   
});
