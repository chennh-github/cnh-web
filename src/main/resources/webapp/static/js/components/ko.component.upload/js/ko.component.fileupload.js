;(function () {

	(function (koComponent) {
		if (typeof require === "function" && typeof exports === "object" && typeof module === "object") {
			koComponent(require("knockout"), require("text!template/ko.component.upload/templates/bootstrap.fileupload.template"));
		} else if (typeof define === 'function' && define.amd) {
			define(["knockout", "text!template/ko.component.upload/templates/bootstrap.fileupload.template"], koComponent);
		} else {
			var ko = window.ko;
			ko.components.register("fileupload", koComponent(ko, ""));
		}
	}(function (ko, templateHtml) {

		var defaults = {
			fileList: ko.observableArray([]),
			uploadedFileList: ko.observableArray([]),
			upload: {
				uploadUrl : (window.fullPath || "") + 'image/upload',
				showCaption: false,
				showUpload: false,
				showRemove: false,
				showClose: false,
				multiple: true,
				enctype: 'multipart/form-data',

				previewSettings: {
					image: {width: "auto", height: "160px"}
				},

				allowedFileTypes: ['image'],
				allowedFileExtensions : ['jpg', "jpeg", 'png', 'gif', 'JPG', "JPEG", 'PNG', 'GIF'],

				uploadExtraData: function (previewId, index) {
					return {};
				},

				maxFileSize: 4*1024, // kb
				minFileCount: 0,
				maxFileCount: 5,

				overwriteInitial: false,
				validateInitialCount: true,

				ajaxSettings: {
					type: "post",
					dataType: "json"
				},
				language: "zh"
			},
			resolveData: function (data) {
				return data['imgShowRoot'] + data['paths'][0];
			}
		};

		var objUrl = window.URL || window.webkitURL,
			cleanMemory = function ($thumb) {
				var data = $thumb.is('img') ? $thumb.attr('src') : $thumb.find('source').attr('src');
				/** @namespace objUrl.revokeObjectURL */
				objUrl.revokeObjectURL(data);
			};
		
		var viewModel = function (params, $container) {
			this.setting = $.extend(true, {}, defaults, params || {});
			this.$container = $container;
			this.$uploader = null;
			this.urlMap = {};
			this.initUpload();
		};
		
		viewModel.prototype = {
			initUpload: function (e) {
				var $file = this.$container.children(), 
					that = this,
					uploadSetting = this.setting.upload;
				
				uploadSetting.uploadExtraData = function (previewId, index) {
					return $.extend(true, {
						previewId: previewId,
						index: index
					}, that.setting.data || {});
				};
				
				// if multiple 
				if (uploadSetting.multiple ) {
					$file.attr("multiple", true);
				}
				
				this.$uploader = $file.fileinput($.extend(true, {}, uploadSetting, {
					maxFileCount: uploadSetting.maxFileCount - this.setting.fileList().length
				}));
				
				$file.on("fileuploaded", function (event, data, previewId, index) {
					that.fileuploaded(event, data, previewId, index);
				});
				$file.on("filecleared", function (event) {
					that.filecleared(event);
				});
				$file.on("filepredelete", function (event) {
					that.filepredelete(event);
				});
				$file.on("filesuccessremove", function (event, index) {
					that.filesuccessremove(event, index);
				});
				
				this.setting.fileList.subscribe(function () {
					that.refreshUpload();
				});
				
				this.refreshUpload();
			},
			removeThumb: function ($thumb) {
				var $uploader = this.$uploader,
					out = this.$uploader.fileinput("_raise", "filesuccessremove", [$thumb.attr('id'), $thumb.data('fileindex')]);
                cleanMemory($thumb);
                if (out === false) {
                    return;
                }
                $thumb.fadeOut('slow', function () {
                    $thumb.remove(); // getFileStack
                    if (!$uploader.fileinput("getFileStack").length) {
                    	$uploader.fileinput("reset");
                    }
                });
			},
			refreshUpload: function (){
				var $uploader = this.$uploader,
					uploadSetting = this.setting.upload;
				$uploader.fileinput("refreshOptions", {
					maxFileCount: uploadSetting.maxFileCount - this.setting.fileList().length
				});
				for (var url in this.urlMap) {
					this.removeThumb($("#" + this.urlMap[url]["previewId"]));
				}
				this.urlMap = {};
			},
			fileuploaded: function (event, data, previewId, index) {
				var url;
				if (url = this.setting.resolveData(data.response)) {
					this.setting.uploadedFileList.push(url);
					this.urlMap[url] = {
						index: index,
						previewId: previewId
					};
				}
			},
			filecleared: function (event) {
					
			},
			filepredelete: function (event) {
					
			},
			filesuccessremove: function (event, index) {
					
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