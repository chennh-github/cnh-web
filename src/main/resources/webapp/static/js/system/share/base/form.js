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
         * @param businessType
         * @param data
         * @returns {{}}
         */
        setImageData: function (businessType, data) {
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
         * 保存/更新成功后回调函数
         */
        afterSaveOrUpdate: function () {
            master.sweet.success("操作成功");
            master.utils.delay(2000, function () {
                location.assign('index');
            });
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
                if (arguments[0] !== false) {
                    that.afterSaveOrUpdate();
                }
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
            return this.saveOrUpdate.apply(this, arguments);
        },
        /**
         * 显示图片上传弹出框
         * @param koRefer
         * @param options
         */
        showUploadDialog: function (koRefer, options) {
            var that = this,
                $uploadDialog = $('#uploadDialog');
            options = $.extend({
                urls: master.fmt.refer(koRefer, this),
                koRefer: koRefer
            }, options);
            $uploadDialog.data("upload-options", options);
            master.utils.showIframeDialog($uploadDialog, master.utils.compatUrl(fullPath + 'component/image/upload/index', options));
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
            master.work.uploadImage(fileInput, {
                data: $.extend(true, {
                    businessType: businessType
                }, data, this.setImageData(businessType, data)),
                afterUpload: function (result, data, fileInput) {
                    that.afterUpload(result.path, data, fileInput);
                }
            });
        },
        /**
         * 图片上传成功回调函数
         */
        afterUpload: function (response, data, fileInput) {
            master.work.afterUpload(response, data, this);
        },
        /**
         * 移除图片回调函数
         * @param koRefer
         * @param index
         */
        removeUpload: function (koRefer, index) {
            master.work.removeUpload(koRefer, index, this);
        }
    });

    // 允许父iframe调用submitForm
    global['submitForm'] = function () {
        return new $.Deferred(function () {
            var that = this,
                promise = viewModel.submitForm(false);
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
        ready.init(['selectPicker', 'select2Picker', 'datetimePicker', 'validatePicker']);
    });

    // 开启ko延迟更新
    ko.options.defer = true;

}(jQuery, (window.viewModel || (window.viewModel = {}))));