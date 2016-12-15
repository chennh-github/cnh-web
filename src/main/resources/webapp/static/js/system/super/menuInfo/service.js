;
(function ($, service) {

    "use strict";

    $.extend(true, service, {
        /**
         * ���²˵�����
         * @returns {*|{trackMethods, trackWebSockets, ignoreURLs}}
         */
        updateDependency: function () {
            return $.ajax({
                url: fullPath + 'api/system/menuInfo/update/dependency',
                type: 'POST'
            });
        },
        /**
         * zTree��ק����
         * @param data
         * @returns {*|{trackMethods, trackWebSockets, ignoreURLs}}
         */
        drop: function (data) {
            return $.ajax({
                url: fullPath + 'api/system/menuInfo/drop',
                type: 'PUT',
                data: data
            });
        }
    });

}(jQuery, (window.service || (window.service = {}))));