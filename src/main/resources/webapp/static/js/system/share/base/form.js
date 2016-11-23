;
(function ($, viewModel) {

    "use strict";

    var global = (0, eval)("this");		// 取得全局对象

    $.extend(true, viewModel, {
        model: {
            entity: {			            // 实体对象
                id: global["id"]			// 有ID则为编辑，否则为新增
            },
            hasInited: false
        },
        initViewModel: function () {
            this.model = ko.mapping.fromJS(this.model);
            this.initReady();
            this.initValidator();
            this.get();
            ko.applyBindings(this, $("#bootstrap")[0]);
            this.model.hasInited(true);
        },
        /**
         * 初始化时执行的函数
         */
        initReady: $.noop,
        /**
         * 初始化jquery.validator
         */
        initValidator: $.noop,
        /**
         * 取得entity后执行
         */
        afterGet: $.noop,
        /**
         * 设置实体对象后执行
         */
        afterEntity: $.noop,
        /**
         * 设置图片上传参数
         * @param moduleName
         * @returns {{}}
         */
        setImageData: function (moduleName) {
            return {};
        },
        /**
         * 提交之前设置data，并做数据验证
         * 返回假值则不提交
         */
        beforeSaveOrUpdate: function (data) {
            return true;
        },
        /**
         * 读取实体对象
         */
        get: function () {
            var id = ko.unwrap(this.model.entity.id), that = this;
            if (id) {
                service.get(id).then(function (entity) {
                    that.afterGet(entity);
                    master.ko.fromJS(that.model.entity, entity);
                    that.afterEntity(entity);
                });
            }
        },
        /**
         * 新增/更新实体对象
         * 传递一个promise对象和true/false参数给父页面判断当前操作结果是否成功
         */
        saveOrUpdate: function () {
            var that = this,
                data = ko.mapping.toJS(this.model.entity);
            if (!this.beforeSaveOrUpdate(data)) {
                return;
            }
            $.each(data, function (k, v) {
                if ($.isArray(v)) {
                    data[k] = v.join(',');
                }
            });
            return service.saveOrUpdate(data).then(function () {
                master.sweet.success("操作成功");
            });
        },
        /**
         * 执行提交form表单的方法，可以重写做逻辑验证
         */
        submitForm: function () {
            var $form = $("#form");
            if (!$form.valid()) {
                return;
            }
            return this.saveOrUpdate();
        },
        /**
         * 图片上传
         * @param fileInput
         * @param businessType
         * @param data
         * @returns {Boolean}
         */
        upload: function (fileInput, businessType, data) {
            var that = this;
            if (!this.validateUpload(fileInput)) {
                return false;
            }
            $("body").cover();
            $.ajaxFileUpload({						// 异步保存图片   addForm.attr("action")
                url: fullPath + "image/upload",
                secureuri: false,
                fileElementId: fileInput.id,
                dataType: 'json',
                data: {
                    businessType: businessType
                },
                success: function (resJson, status) {
                    if (resJson.status === 1) {
                        if ($.isFunction(that.afterUpload)) {
                            that.afterUpload(resJson.data, data, fileInput);
                        }
                        master.toast.success("上传成功!");
                    } else {
                        master.toast.warning("上传失败!");
                    }
                    $("body").uncover();
                },
                error: function (data, status, e) {
                    master.toast.warning("上传异常!");
                    $("body").uncover();
                }
            });
        },
        /**
         * 验证图片
         * @param file
         * @returns {Boolean}
         */
        validateUpload: function (file) {
            var ua = window.navigator.userAgent;
            var src = file.value;
            var ext = (src.substring(src.indexOf(".") + 1) || "").toLowerCase();
            var fileSize = 0;
            // 验证图片格式
            if (ko.utils.arrayIndexOf(this.store.imgExts, ext) === -1) {
                master.toast.warning("只允许上传 " + this.store.imgExts.join(",") + " 格式的图片!");
                return false;
            }
            //验证图片大小
            if (ua.indexOf("MSIE") >= 1) {
                var dynImg = document.getElementById("dynImg");
                dynImg.dynsrc = src;
                fileSize = dynImg.fileSize;
            } else if (file.files) {
                fileSize = file.files[0].size;
            }
            if (fileSize == -1) {
                master.toast.warning("请选择图片!");
                return false;
            } else if (fileSize > this.store.imgSize) {
                master.toast.warning("图片不得大于 " + Math.round(this.store.imgSize / 1024) + " KB");
                return false;
            }
            return true;
        },
        /**
         * 图片上传成功回调函数
         */
        afterUpload: function (data, options) {
            if (options["koRefer"]) {
                var o = master.fmt.referKo(type, this);
                if (master.ko.isKoArray(o)) {
                    o.push(data.path[0]);
                } else {
                    o(data.path[0]);
                }
            }
        },
        /**
         * 移除图片回调函数
         * @param data
         * @param options
         */
        removeUpload: function (data, options) {
            if (options["koRefer"]) {
                var o = master.fmt.referKo(type, this);
                if (master.ko.isKoArray(o)) {
                    o.remove(data.path[0]);
                } else {
                    o("");
                }
            }
        }
    });

    // 允许父iframe调用submitForm
    global['submitForm'] = function () {
        return new $.Deferred(function () {
            var that = this,
                promise = viewModel.submitForm();
            if (promise && promise.then) {
                return promise.then(function () {
                    that.resolve();
                });
            } else {
                this.reject();
            }
        });
    };

    $(function () {
        viewModel.initViewModel();
    });

}(jQuery, (window.viewModel || (window.viewModel = {}))));