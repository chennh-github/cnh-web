;(function () {
	(function (koComponent) {
		if (typeof require === "function" && typeof exports === "object" && typeof module === "object") {
			koComponent(require("knockout"),
				require("text!template/ko.component.upload/templates/bootstrap.uploader.template"),
				"fileinput/v4.3.2/js/fileinput",
				require("components/ko.component.upload/js/ko.component.fileupload"),
				require("components/ko.component.upload/js/ko.component.fileinitial"));
		} else if (typeof define === 'function' && define.amd) {
			define(["knockout",
				"text!template/ko.component.upload/templates/bootstrap.uploader.template",
				// 载入fileinput插件
				"fileinput/v4.3.2/js/fileinput",
				"components/ko.component.upload/js/ko.component.fileupload",
				"components/ko.component.upload/js/ko.component.fileinitial"], koComponent);
		} else {
			var ko = window.ko;
			ko.components.register("x-uploader", koComponent(ko, ""));
		}
	}(function (ko, templateHtml) {

		var defaults = {
			fileList: ko.observableArray([]),
			uploadedFileList: ko.observableArray([]),
			upload: {
				maxFileCount: 5
			},
			data: {}
		};


		var viewModel = function (params, $container) {
			this.setting = $.extend(true, {}, defaults, params || {});
			this.$container = $container;
			this.init();
		};

		viewModel.prototype = {
			init: function (e) { },
			// 切换显示详细提示
			toggleDetailTip: function (data, e) {
				e = e || window.event;
				$(e.target).toggleClass("glyphicon-chevron-down")
					.toggleClass("glyphicon-chevron-up")
					.next().slideToggle();
			},
			// 隐藏保存文件的错误提示
			hideSaveWarning: function (data, e) {
				$(e.target).parents(".alert-warning").slideUp();
			},
			// 将已上传的文件保存到原文件列表并从已上传文件列表移除(会同步移除预览区域的文件)
			saveUploadedFile: function (data, e) {
				e = e || window.event;
				var uploadedFileList = this.setting.uploadedFileList;
				var $tip = $(e.target).parents(".btn-groups").find(".alert-warning:last");
				if (uploadedFileList().length) {
					var that = this;
					// 拷贝已上传文件列表到原文件列表
					$.each(uploadedFileList(), function (i, v) {
						that.setting.fileList.push(v);
					});
					// 清空已上传文件列表
					uploadedFileList.splice(0, uploadedFileList().length);
					$tip.slideUp();
				} else {
					$tip.slideDown();
				}
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