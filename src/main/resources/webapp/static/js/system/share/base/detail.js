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
        },
        /**
         * 删除后回调函数
         */
        afterDelete: function () {
            master.sweet.success("删除成功");
            master.utils.delay(2000, function () {
                location.assign('index');
            });
        },
        /**
         * 删除记录
         * @param id
         */
        deleteItem: function (isSilent) {
            var that = this,
                id = global["id"];
            if (typeof isSilent !== "boolean") {
                isSilent = false;
            }
            function doDelete() {
                service.del(id).then(function () {
                    that.afterDelete();
                });
            }
            if (isSilent) {
                doDelete();
            } else {
                $.confirm({
                    title: "确定删除该记录？",
                    content: "该操作不可恢复，点击确定继续",
                    confirm: function () {
                        doDelete();
                    }
                });
            }
        }
    });

    // 开启ko延迟更新
    ko.options.defer = true;

}(jQuery, (window.viewModel || (window.viewModel = {}))));