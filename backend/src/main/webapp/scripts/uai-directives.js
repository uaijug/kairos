'use strict';
angular.module('kairosApp').directive('uaiFormGroup', function($translate) {
    return {
        restrict: 'E',
        templateUrl: '../uai-directives/uai-form-group.html',
        replace: true,
        transclude: true,
        require: "^form", // Tells Angular the control-group must be within a form

        scope: {
            label: "@",
            labelTranslate: "@",
            showCustomErrorMessages: "@?"
        },

        link: function(scope, element, attrs, formController) {

            scope.showErrorMessages = angular.isDefined(scope.showCustomErrorMessages) ? scope.showCustomErrorMessages !== 'true' : true;

			$translate(scope.labelTranslate).then(function (labelTranslateValue) {
			    scope.labelTranslateValue = labelTranslateValue;
			});

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

            var errorsExpression = [formController.$name, inputName, "$error"].join(".");

            var errorExpression = invalidExpression + " && " + dirtyExpression;
            // Watch the parent scope, because current scope is isolated.
            scope.$parent.$watch(errorExpression, function(isError) {
                scope.isError = isError;
            });

             scope.$parent.$watch(errorsExpression, function(errors) {
                scope.errors = errors;
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
}).directive('uaiConfirm', function($modal, $translate) {
     var ModalInstanceCtrl = function($scope, $modalInstance, messageTranslate) {
        $scope.messageTranslate = messageTranslate;

        $scope.ok = function() {
            $modalInstance.close();
        };

        $scope.cancel = function() {
            $modalInstance.dismiss('cancel');
        };
      };

    return {
        restrict: 'A',
        scope: {
            confirmMessageTranslate: '@',
            confirmOk: '&',
            item:'='
        },
        link: function(scope, element, attrs) {
            element.bind('click', function() {
                var modalInstance = $modal.open({
                    templateUrl: '../uai-directives/uai-confirm.html',
                    controller: ModalInstanceCtrl,
                    resolve: {
                        messageTranslate: function () {
                            return scope.confirmMessageTranslate;
                        }
                    }
                });

                modalInstance.result.then(function() {
                    scope.confirmOk(scope.item);
                }, function() {
            });

          });
        }
    };
});