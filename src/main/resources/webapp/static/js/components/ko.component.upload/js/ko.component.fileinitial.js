;(function () {

	(function (koComponent) {
		if (typeof require === "function" && typeof exports === "object" && typeof module === "object") {
			koComponent(require("knockout"), require("text!template/ko.component.upload/templates/bootstrap.fileinitial.template"));
		} else if (typeof define === 'function' && define.amd) {
			define(["knockout", "text!template/ko.component.upload/templates/bootstrap.fileinitial.template"], koComponent);
		} else {
			var ko = window.ko;
			ko.components.register("x-fileinitial", koComponent(ko, ""));
		}
	}(function (ko, templateHtml) {

		var defaults = {
			fileList: ko.observableArray([]),
			imgShowRoot: window["imgShowRoot"] || "http://127.0.0.1:8080/",
			property: {
				title: "title",
				imgUrl: "url"
			}
		};

		var viewModel = function (params, $container) {
			this.setting = $.extend(true, {}, defaults, params || {});
			this.$container = $container;
			this.init();
		};
		
		viewModel.prototype = {
			init: function (e) { },
			getImgUrl: function (item) {
				var imgUrl;
				if (!item) {
					return "";
				} else if ($.type(item) === "string") {
					imgUrl = item;
				} else if (!(getImgUrl = item[this.setting.property.imgUrl])) {
					return "";
				}
				
				if (/^[https|https]/.test(imgUrl)) {
					return imgUrl;
				} else {
					return this.setting.imgShowRoot + imgUrl;
				}
			},
			removeImg: function (index) {
				this.setting.fileList.splice(index, 1);
			}
		};
		
		return {
			viewModel: {
				createViewModel: function (params, componentInfo) {
					return new viewModel(params, $(componentInfo.element)); 
				}
			},
			template: templateHtml
		};
	}));
	
}());