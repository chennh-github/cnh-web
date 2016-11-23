/**
 * @file: Javascript文件描述
 * @author: Administrator
 * @history: 2016/2/25
 */
define(["knockout"], function (ko) {
	var koComponents = {
        	"x-uploader": "components/ko.component.upload/js/ko.component.uploader",
            "x-fileupload": "components/ko.component.upload/js/ko.component.fileupload",
            "x-fileinitial": "components/ko.component.upload/js/ko.component.fileinitial"
        };
	
    return {
        register: function (koComponents) {
            for (var componentName in koComponents) {
                if (koComponents.hasOwnProperty(componentName)) {
                    ko.components.register(componentName, { require: koComponents[componentName] });
                }
            }
        },
        registerComponent: function (name){
        	if (koComponents[name]){
        		 var component = {};
                 component[name] = koComponents[name];
                 this.register(component);
        	}
        },
        registerAllComponents: function (){
        	this.register(koComponents);
        }
    };
});