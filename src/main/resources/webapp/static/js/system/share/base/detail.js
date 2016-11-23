;(function ($, viewModel) {

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
         * 读取实体对象
         */
        get: function () {
            var id = ko.unwrap(this.model.entity.id),that = this;
            if (id) {
                service.get(id).then(function (entity) {
                    that.afterGet(entity);
                    master.ko.fromJS(that.model.entity, entity);
                });
            }
        }
    });

    $(function () {
        viewModel.initViewModel();
    });

}(jQuery, (window.viewModel || (window.viewModel = {}))));