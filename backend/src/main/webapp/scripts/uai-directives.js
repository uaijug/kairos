'use strict';
angular.module('kairosApp').directive('uaiInputText', function() {
    return {
        restrict: 'E',
        require: 'ngModel',
        templateUrl: '../uai-directives/uai-input-text.html',
        scope: {
            name: '@',
            label: '@',
            ngModel: '=',
            ngMinlength: '=?',
            ngMaxlength: '=?',
            ngRequired: '=?',
            ngPattern: '=?',
            ngChange: '=?',
            ngTrim: '=?'
        }
    };
})