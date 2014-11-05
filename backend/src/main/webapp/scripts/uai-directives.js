'use strict';
angular.module('kairosApp').directive('uaiFormGroup', function() {
    return {
        restrict: 'E',
        templateUrl: '../uai-directives/uai-form-group.html',
        replace: true,
        transclude: true,
        require: "^form", // Tells Angular the control-group must be within a form

        scope: {
            label: "@",
            labelTranslate: "@"
        },

        link: function(scope, element, attrs, formController) {
            // The <label> should have a `for` attribute that links it to the input.
            // Get the `id` attribute from the input element
            // and add it to the scope so our template can access it.
            var id = element.find(":input").attr("id");
            scope.for = id;

            // Get the `name` attribute of the input
            var inputName = element.find(":input").attr("name");

            var isRequired = element.find(":input").attr("required");
            if(undefined != isRequired && null != isRequired) {
                scope.isRequired = true;
            }

            // Build the scope expression that contains the validation status.
            // e.g. "form.example.$invalid"
            var invalidExpression = [formController.$name, inputName, "$invalid"].join(".");

            var dirtyExpression = [formController.$name, inputName, "$dirty"].join(".");

            var errorExpression = invalidExpression + " && " + dirtyExpression;
            // Watch the parent scope, because current scope is isolated.
            scope.$parent.$watch(errorExpression, function(isError) {
                scope.isError = isError;
            });
        }
    };
}).directive('uaiShowError', function() {
    return {
        restrict: 'E',
        templateUrl: '../uai-directives/uai-show-error.html',
        replace: true,
        transclude: true,  
        require: "^form",      
        scope: {
            validationErrorFor: "@", 
            validationErrorType: "@"
        },

        link: function(scope, element, attrs, formController) {
            var invalidExpression = [formController.$name, scope.validationErrorFor, "$error", scope.validationErrorType].join(".");

            var dirtyExpression = [formController.$name, scope.validationErrorFor, "$dirty"].join(".");

            var errorExpression = invalidExpression + " && " + dirtyExpression;

            scope.$parent.$watch(errorExpression, function(isError) {
                scope.isError = isError;
            });


        }
    };
})