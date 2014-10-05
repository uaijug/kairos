'use strict';

angular.module('kairosApp')
    .directive('uaiInputText', function() {
        return {
            restrict: 'E',
            templateUrl: '../uai-directives/uai-input-text.html',
            scope: {
                name: '@',
                label: '@',
                ngModel: '=ngModel'
            },
        };
    })