;
(function ($) {

    "use strict";

    var global = this || (0, eval)("this");

    /**
     * global handler of jquery ajax error.
     * can be cancel by option['global'] = false
     */
    (function () {
        var helper = {
            showError: function (jqAjax, jqXHR, textStatus, errorThrown) {
                if (jqXHR.readyState == 4) {
                    var exception = this.parseException(jqAjax, jqXHR, textStatus, errorThrown);
                    var content = exception.friendly ? [
                        $.trim(exception.message)
                    ] : [
                        "<b>错误类型:</b> " + $.trim(exception.status) + "<br />",
                        "<b>错误时间:</b> " + this.formatDate(new Date(exception.timestamp), "yyyy-MM-dd hh:mm:ss") + "<br />",
                        "<b>访问路径:</b> " + $.trim(exception.path) + "<br />",
                        "<b>错误信息:</b> " + $.trim(exception.message) + "<br />",
                        "<b>错误详情:</b> " + $.trim(exception.error) + "<br />"
                    ];
                    if ($.alert) {
                        $.alert({
                            title: exception.friendly ? "系统提示" : "出错了",
                            content: "<div style='max-height: 500px; overflow-x: hidden; overflow-y: auto; word-break: break-word;'>" +
                            "<p>" + content.join("</p><p>") + "</p>" +
                            "</div>",
                            closeIcon: true,
                            confirmButton: "确定"
                        });
                    } else {
                        alert(content.join(""));
                    }
                }
            },
            formatDate: function (date, format) {
                var o = {
                    "M+": date.getMonth() + 1, 						// month
                    "d+": date.getDate(), 								// day
                    "h+": date.getHours(), 							// hour
                    "m+": date.getMinutes(), 							// minute
                    "s+": date.getSeconds(), 							// second
                    "q+": Math.floor((date.getMonth() + 3) / 3),		// quarter
                    "S": date.getMilliseconds() 						// millisecond
                }
                if (/(y+)/.test(format)) {
                    format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
                }

                for (var k in o) {
                    if (new RegExp("(" + k + ")").test(format)) {
                        format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
                    }
                }
                return format;
            },
            parseException: function (jqAjax, jqXHR, textStatus, errorThrown) {
                var exception = {
                    type: "warning",
                    status: "",
                    error: "",
                    message: "",
                    timestamp: "",
                    path: "",
                    friendly: false
                };
                try {
                    if (!jqXHR.responseJSON) {
                        jqXHR.responseJSON = $.parseJSON(jqXHR.responseText);
                    }
                } catch (e) {}

                if (jqXHR.responseJSON) {
                    var responseJSON = jqXHR.responseJSON;
                    exception.status = responseJSON.status;
                    exception.error = responseJSON.error;
                    exception.message = responseJSON.message;
                    exception.timestamp = responseJSON.timestamp;
                    exception.path = responseJSON.path;
                    exception.friendly = responseJSON.friendly;
                } else {
                    exception.type = jqXHR.statusText.toLowerCase() == "ok" ? "error" : jqXHR.statusText;
                    exception.status = 400;
                    exception.error = ['contentType：', jqAjax.contentType, '<br />', 'url：', jqAjax.url].join('');
                    exception.message = ['向服务器执行', jqAjax.type, '请求时出错', jqXHR.responseText].join('');
                    exception.timestamp = new Date().getTime();
                    exception.path = jqAjax.url;
                }

                return exception;
            }
        };

        $.ajaxSetup({
            dataType: "json",
            error: function (jqXHR, textStatus, errorThrown) {
                helper.showError(this, jqXHR, textStatus, errorThrown);
            }
        });
    }());

    /**
     * global prevent multiple request.
     * it dependents on options['url'] as the key, so parameters will be ignored
     */
    (function () {
        var currentRequests = {};
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            var key = options.type + ":" + options.url;
            if (!currentRequests[key]) {
                currentRequests[key] = jqXHR;
            } else {
                jqXHR.abort();
                return;
            }

            var customComplete = options.complete;
            options.complete = function (jqXHR, textStatus) {
                currentRequests[key] = null;
                if ($.isFunction(customComplete)) {
                    customComplete.apply(this, arguments);
                }
            };
        });
    }());

    /**
     * 如果已经引用了$.fn.cover，则启用全局遮罩
     * $.fn.cover必须引用在该文件之前
     */
    (function (hasCover) {
        if (hasCover) {
            $(document).ajaxStart(function () {
                $("body").cover("show");
            }).ajaxStop(function () {
                $("body").cover("hide");
            });
        }
    }(!!$.fn.cover));


    /**
     * form restful data
     * replace {param} to real data
     */
    (function () {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            var m, url = options.url,
                restData = options.restData;
            if (url && restData && (m = url.match(/\{(.+?)\}/g)) && m.length) {
                $.each(m, function (i, k) {
                    var v = restData[k.replace(/\{|\}/g, "")];
                    url = url.replace(new RegExp(k, "g"), v === undefined ? k : v);
                });
                options.url = url;
            }
        });
    }());

}(jQuery));