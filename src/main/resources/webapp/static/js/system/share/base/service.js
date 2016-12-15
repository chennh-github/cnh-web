;
(function ($, service) {

    "use strict";

    var global = (0, eval)("this"),

        MODULAR_NAME = global["modularName"],

        FULL_PATH = global["fullPath"];

    $.extend(true, service, {
        /**
         * 执行查询列表
         * @param data 查询条件
         */
        list: function (data) {
            return $.ajax({
                url: FULL_PATH + "api/system/" + MODULAR_NAME,
                type: "GET",
                dataType: "json",
                data: data
            });
        },
        /**
         * 跟ID批量删除
         * @param ids ID，逗号分隔
         * @returns
         */
        del: function (ids) {
            return $.ajax({
                url: FULL_PATH + "api/system/" + MODULAR_NAME + "/{ids}",
                type: "DELETE",
                restData: {
                    ids: ids
                }
            });
        },
        /**
         * 保存对象
         * @param data
         * @returns {*|{trackMethods, trackWebSockets, ignoreURLs}}
         */
        save: function (data) {
            return $.ajax({
                url: FULL_PATH + "api/system/" + MODULAR_NAME,
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(master.utils.removeEmpty(data))
            });
        },
        /**
         * 更新对象
         * @param data
         * @returns {*|{trackMethods, trackWebSockets, ignoreURLs}}
         */
        update: function (data) {
            return $.ajax({
                url: FULL_PATH + "api/system/" + MODULAR_NAME,
                type: "PUT",
                contentType: "application/json",
                data: JSON.stringify(master.utils.removeEmpty(data))
            });
        },
        /**
         * 新增/更新对象
         * @param data 实体
         * @returns
         */
        saveOrUpdate: function (data) {
            return data.id ? this.update(data) : this.save(data);
        },
        /**
         * 根据ID读取实体
         * @param id
         * @returns
         */
        get: function (id) {
            return $.ajax({
                url: FULL_PATH + "api/system/" + MODULAR_NAME + "/{id}",
                type: "GET",
                dataType: "json",
                restData: {
                    id: id
                }
            });
        },
        /**
         * 导出Excel
         * @param data
         * @returns {*|{trackMethods, trackWebSockets, ignoreURLs}}
         */
        exportXls: function (data) {
            return $.ajax({
                url: fullPath + "xls/exportXls",
                type: "POST",
                data: master.utils.removeEmpty(data)
            });
        },
        /**
         * 上移
         * @param data
         * @returns {*|{trackMethods, trackWebSockets, ignoreURLs}}
         */
        moveUp: function (data) {
            return $.ajax({
                url: FULL_PATH + "api/system/" + MODULAR_NAME + "/moveUp",
                type: "PUT",
                dataType: "json",
                data: data
            });
        },
        /**
         * 下移
         * @param data
         * @returns {*|{trackMethods, trackWebSockets, ignoreURLs}}
         */
        moveDown: function (data) {
            return $.ajax({
                url: FULL_PATH + "api/system/" + MODULAR_NAME + "/moveDown",
                type: "PUT",
                dataType: "json",
                data: data
            });
        },
        /**
         * 置顶
         * @param data
         * @returns {*|{trackMethods, trackWebSockets, ignoreURLs}}
         */
        zTop: function (data) {
            return $.ajax({
                url: FULL_PATH + "api/system/" + MODULAR_NAME + "/zTop",
                type: "PUT",
                dataType: "json",
                data: data
            });
        }
    });

}(jQuery, (window.service || (window.service = {}))));