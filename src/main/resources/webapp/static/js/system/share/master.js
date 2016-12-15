;
(function () {

    "use strict";

    var global = (0, eval)("this"),

        ko = global["ko"],

        $ = global["jQuery"],

        master = global["master"] || (global["master"] = {}),

        toastr = global["toastr"] || (global['toastr'] = {}),

        swal = global["swal"] || (global['swal'] = {}),

        FULL_PATH = global["fullPath"],

        IMG_SHOW_ROOT = global["imgShowRoot"],

        DEFAULT_IMG_URL = FULL_PATH + "images/photo.png",

        TRANSITION_END = ['webkitTransitionEnd.flip', 'mozTransitionEnd.flip', 'MSTransitionEnd.flip', 'otransitionend.flip', 'transitionend.flip'].join(' '),

        ANIMATION_END = ['webkitAnimationEnd.animate', 'mozAnimationEnd.animate', 'MSAnimationEnd.animate', 'oanimationend.animate', 'animationend.animate'].join(' '),

        koUnwrap = ko.unwrap;

    var format = {

            /**
             * 格式化日期
             * @param date java时间戳/.net时间戳/mysql字符串/字符串
             * @param fmt 格式化格式，默认: yyyy-MM-dd hh:mm:ss
             * @returns {*}
             */
            date: function (date, fmt) {
                fmt = fmt || "yyyy-MM-dd hh:mm:ss";
                date = koUnwrap(date);
                // java格式的timestamp
                if (/^\d+$/.test(date)) {
                    date = new Date(+date);
                    return !date ? "##" : date.format(fmt);
                }
                // java格式object
                else if (typeof date === "object" && date.time) {
                    date = new Date(+date.time);
                    return !date ? "##" : date.format(fmt);
                }
                // .net格式的timestamp
                else if ((/date/i).test(date)) {
                    date = new Date(+date.match(/\d+/)[0]);
                    return !date ? "##" : date.format(fmt);
                }
                // mysql字符串格式转化
                else if (date && $.type(date) === "string" && /\.\d$/.test(date)) {
                    date = new Date(date.replace(/\.\d$/, "").replace(/-/g, "/"));
                    return !date ? "##" : date.format(fmt);
                }
                return date;
            },
            /**
             * 返回img图像
             * @param url
             * @param defaultUrl
             * @returns {String}
             */
            img: function (url, defaultUrl) {
                url = koUnwrap(url);
                if (!url) {
                    url = defaultUrl || DEFAULT_IMG_URL;
                } else if (!/^http/.test(url)) {
                    url = IMG_SHOW_ROOT + url;
                }
                return "<img src='" + url + "' class='table-img' data-src='" + url + "' />";
            },
            /**
             * 返回多个img图像
             * @param urls
             * @param defaultUrl
             * @returns
             */
            imgs: function (urls, defaultUrl) {
                var ret = [], that = this;
                urls = koUnwrap(urls);
                if (urls) {
                    $.each(urls.split(","), function (i, url) {
                        ret.push(that.img(url, defaultUrl));
                    });
                }
                return ret.join("");
            },
            /**
             * 组装imgUrl
             * @param url
             * @returns
             */
            imgUrl: function (url) {
                url = koUnwrap(url);
                return !/^http/.test(url) ? IMG_SHOW_ROOT + url : url;
            },
            /**
             * 取对象值
             * @param key
             * @param obj
             * @param defaultValue
             * @returns
             */
            value: function (key, obj, defaultValue) {
                if (defaultValue == null) {
                    defaultValue = ""
                }
                var value = obj[koUnwrap(key)];
                return value == null ? defaultValue : value;
            },
            /**
             * 货币格式化
             * @param money
             * @param opt
             * @returns
             */
            currency: function (money, opt) {
                opt = $.extend({
                    places: 2,
                    symbol: "￥",
                    thousand: ", ",
                    decimal: "."
                }, opt);
                var number = koUnwrap(money),
                    negative = number < 0 ? "-" : "",
                    i = parseInt(number = Math.abs(+number || 0).toFixed(opt.places), 10) + "",
                    l = i.length,
                    j = l > 3 ? l % 3 : 0;
                return opt.symbol + negative + (j ? i.substr(0, j) + opt.thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + opt.thousand) +
                    (opt.places ? opt.decimal + Math.abs(number - i).toFixed(opt.places).slice(2) : "");
            },
            /**
             * 输出obj的子属性值
             * @param keyPath 属性，以"."分隔
             * @param obj 对象
             * @param defaultValue 默认值
             * @returns {*}
             */
            refer: function (keyPath, obj, defaultValue) {
                keyPath = koUnwrap(keyPath);
                if ($.type(keyPath) === "string") {
                    var keys = keyPath.split("."),
                        key,
                        refer = koUnwrap(obj),
                        i, l = keys.length;
                    try {
                        for (i = 0; refer && (key = keys[i]) && i < l; i++) {
                            refer = koUnwrap(refer[key]);
                        }
                    } catch (e) {
                    }
                    return i === l && (refer = koUnwrap(refer)) != null ? refer : defaultValue;
                } else {
                    return defaultValue;
                }
            },
            /**
             * 返回obj对应keyPath的对象
             * @param keyPath
             * @param obj
             * @returns {*}
             */
            referKo: function (keyPath, obj) {
                try {
                    var keys = keyPath.split("."),
                        key,
                        referKo = obj;
                    for (var i = 0, l = keys.length; referKo != undefined && (key = keys[i]) && i < l; i++) {
                        referKo = referKo[key];
                    }
                    return referKo;
                } catch (e) {
                }
                return null;
            },
            /**
             * 判断两个值是否相等
             * @param value 值1
             * @param equalValue 值2
             * @param isStrongEqual 是否全等于
             * @returns {boolean}
             */
            equal: function (value, equalValue, isStrongEqual) {
                value = koUnwrap(value);
                return isStrongEqual ? value === equalValue : value == equalValue;
            },
            /**
             * 是否包含在数组中，返回数组下标，不包含则返回-v4.3.0
             * @param value 值
             * @param arr 数组
             * @param prop 如果数组是对象，则可以通过对象的属性来判断是否包含，此处使用全等判断
             * @returns {*|number}
             */
            index: function (value, arr, prop) {
                value = koUnwrap(value);
                arr = koUnwrap(arr);
                prop = koUnwrap(prop);
                return !(prop && $.type(prop) === "string") ? $.inArray(value, arr) : (function () {
                    var index = -1;
                    value = $.type(value) === "object" ? value[prop] : value;
                    $.each(arr, function (i, v) {
                        if (v[prop] === value) {
                            index = i;
                            return false;
                        }
                    });
                    return index;
                }());
            },
            /**
             * 是否包含在数组中
             * @param value
             * @param arr
             * @param prop
             * @returns {boolean}
             */
            inArray: function (value, arr, prop) {
                return this.index(value, arr, prop) > -1;
            },
            /**
             * 是否为null、undefined或空字符串
             * @param value
             * @returns {boolean}
             */
            nullOrEmpty: function (value) {
                value = koUnwrap(value);
                return value == null ? true : value === "";
            },
            /**
             * 取较小值
             * @param value
             * @param minValue
             * @returns {number}
             */
            min: function (value, minValue) {
                value = +koUnwrap(value);
                return value === value ? Math.min(value, minValue) : minValue;
            },
            /**
             * 取较大值
             * @param value
             * @param maxValue
             * @returns {number}
             */
            max: function (value, maxValue) {
                value = +koUnwrap(value);
                return value === value ? Math.max(value, maxValue) : maxValue;
            },
            /**
             * 取范围内的值
             * @param value 操作数
             * @param minValue 最小值
             * @param maxValue 最大值
             * @returns {number}
             */
            range: function (value, minValue, maxValue) {
                var tempValue;
                value = +koUnwrap(value);
                minValue = +koUnwrap(minValue);
                maxValue = +koUnwrap(maxValue);
                if (minValue > maxValue) {
                    tempValue = minValue;
                    minValue = maxValue;
                    maxValue = tempValue;
                }
                return value === value ? Math.max(minValue, Math.min(value, maxValue)) : minValue;
            },
            /**
             * 保留小数位数
             * @param value 操作数
             * @param figures 小数位数
             * @param defaultValue 默认值
             * @returns {*}
             */
            toFixed: function (value, figures, defaultValue) {
                value = +koUnwrap(value);
                figures = +koUnwrap(figures) || 2;
                if (typeof defaultValue !== "number") {
                    defaultValue = 0;
                }
                return (value === value ? value : defaultValue)["toFixed"](figures);
            },
            /**
             * 处理长文本
             * @param value 操作文本
             * @param len 保留长度，默认20
             * @returns
             */
            longText: function (value, len) {
                value = koUnwrap(value);
                len = +koUnwrap(len) || 20;
                if (!value)
                    return "";
                if (value.length > len) {
                    return value.substring(0, len) + "...";
                } else {
                    return value;
                }
            }
        },
        toast = {
            info: function (text) {
                return toastr.info(text, "( ⊙_⊙ )", {
                    "timeOut": "5000"
                });
            },
            success: function (text) {
                return toastr.success(text, "( ^_^ )", {
                    "timeOut": "5000"
                });
            },
            warning: function (text) {
                return toastr.warning(text, "( ⊙o⊙ )", {
                    "timeOut": "10000"
                });
            },
            error: function (text) {
                return toastr.error(text, "( >＿< )", {
                    "timeOut": "30000"
                });
            }
        },
        sweet = {
            info: function (setting) {
                if ($.type(setting) === "string") {
                    setting = {
                        title: setting
                    }
                }
                return swal($.extend(true, {
                    title: "系统提示",
                    text: "( ⊙_⊙ )",
                    type: "info",
                    confirmButtonText: "确定"
                }, setting));
            },
            success: function (setting) {
                if ($.type(setting) === "string") {
                    setting = {
                        title: setting
                    }
                }
                return swal($.extend(true, {
                    title: "操作成功",
                    text: "( ^_^ )",
                    type: "success",
                    confirmButtonText: "确定"
                }, setting));
            },
            warning: function (setting) {
                if ($.type(setting) === "string") {
                    setting = {
                        title: setting
                    }
                }
                return swal($.extend(true, {
                    title: "警告",
                    text: "( ⊙o⊙ )",
                    type: "warning",
                    confirmButtonText: "确定"
                }, setting));
            },
            confirm: function () {
                var setting, confirmFn;
                if ($.isFunction(arguments[0])) {
                    setting = {};
                    confirmFn = arguments[0];
                } else {
                    setting = arguments[0];
                    confirmFn = arguments[1];
                }
                return swal($.extend(true, {
                    title: "确认继续？",
                    text: "( ⊙o⊙ )",
                    showCancelButton: true,
                    confirmButtonText: "确定",
                    cancelButtonText: "取消"
                }, setting), confirmFn);
            },
            prompt: function () {
                var setting, confirmFn;
                if ($.isFunction(arguments[0])) {
                    setting = {};
                    confirmFn = arguments[0];
                } else {
                    setting = arguments[0];
                    confirmFn = arguments[1];
                }
                return swal($.extend(true, {
                    title: "请输入",
                    text: "( ⊙o⊙ )",
                    type: "input",
                    confirmButtonText: "确定",
                    closeOnConfirm: false,
                    animation: "slide-from-top",
                    inputPlaceholder: "请输入"
                }, setting), function (inputValue) {
                    if (inputValue === false) return false;
                    if (inputValue === "") {
                        swal.showInputError("请输入内容!");
                        return false
                    }
                    confirmFn(inputValue);
                });
            }
        },
        koWrap = {
            /**
             * 是否ko观察对象
             * @param o
             * @returns {*}
             */
            isKo: function (o) {
                return ko.isObservable(o);
            },
            /**
             * 是否ko观察数组
             * @param o
             * @returns {*|boolean}
             */
            isKoArray: function (o) {
                return this.isKo(o) && $.isArray(o.peek());
            },
            /**
             * 将data中的值赋给model，{data中没有对应值的则清空}
             * @param model ko对象
             * @param data js对象
             * @param isClear 是否清空data中没有对应值的属性
             * @returns {*}
             */
            fromJS: function (model, data) {
                if (!model || !data) {
                    return model;
                }
                var isClear = $.type(arguments[2]) === "boolean" ? arguments[2] : true,
                    that = this;
                $.each(model, function (name, observableObj) {
                    var v = data[name];
                    if (that.isKo(observableObj)) {
                        if (that.isKoArray(observableObj)) {
                            if (!$.isArray(v)) {
                                v = v ? (v + "").split(",") : (isClear ? [] : observableObj.peek());
                            }
                        } else if (v == null) {
                            v = isClear ? "" : observableObj.peek();
                        }
                        observableObj(v);
                    }
                });
            }
        },
        utils = {
            /**
             * 重置remote验证
             * @param $dom
             * @returns {utils}
             */
            resetRemote: function ($dom) {
                if ($dom) {
                    $dom.removeData("previousValue");
                }
                return this;
            },
            /**
             * 重置$form的remote验证
             * @param $form
             * @returns {utils}
             */
            resetFormRemote: function ($form) {
                var that = this;
                if ($form) {
                    $form.find("[remote]").each(function () {
                        that.resetRemote($(this));
                    });
                }
                return this;
            },
            /**
             * 重置表单验证
             * @param $form
             * @returns {utils}
             */
            resetForm: function ($form) {
                var validator = $form.data("validator"),
                    that = this;
                if (validator) {
                    validator.resetForm();
                }
                this.resetFormRemote($form);
                $form.find(".form-group").removeClass("has-success has-error");
                return this;
            },
            /**
             * 转换为日期格式
             * @param value
             * @returns {*}
             */
            toDate: function (value) {
                value = koUnwrap(value);
                return $.type(value) === "date" ? value :
                    new Date((value + "").replace(/-/g, "/")    // yyyy-MM-dd => // yyyy/MM/dd
                        .replace(/\.\d$/, ""));                 // 处理mysql取出的未格式化datetime尾部携带.0的问题
            },
            /**
             *  移除data对象中的null/undefined/""
             * @param data
             * @returns {*}
             */
            removeEmpty: function (data) {
                var that = this;
                $.each(data, function (k, v) {
                    v = koUnwrap(v);
                    var type = $.type(v);
                    if (v == null) {
                        delete data[k];
                    } else if (type === "object") {
                        that.removeEmpty(data[k]);
                    } else if (type === "string" && (v = $.trim(v)) === "") {
                        delete data[k];
                    } else {
                        data[k] = v;
                    }
                });
                return data;
            },
            /**
             * json对象转为url条件
             * @param json
             * @param flag
             * @returns {*|string}
             */
            jsonToQueryString: function (json, flag) {
                var that = this;
                return json && ( function () {
                        var query = [];
                        $.each(that.removeEmpty(json), function (k, v) {
                            query.push(k + "=" + v);
                        });
                        return query.join(flag || "&");
                    }() ) || "";
            },
            /**
             * 添加参数到url
             * @param url
             * @returns {*}
             */
            compatUrl: function (url) {
                var json = {};
                if ($.type(arguments[1]) !== "object") {
                    json[arguments[1]] = arguments[2];
                } else {
                    json = arguments[1];
                }
                if (url != null) {
                    var query = this.jsonToQueryString(json, "&");
                    if (url.indexOf("?") == -1) {
                        url += "?" + query;
                    } else {
                        url += "&" + query;
                    }
                }
                return url;
            },
            /**
             * post方式提交form表单
             * @param url
             * @param data
             * @param target
             * @returns {utils}
             */
            postForm: function (url, data, target) {
                var $form = $("<form method='post' style='position: absolute; left: -999px; top: -999px; visibility: hidden; display: none;'></form>");
                if (data) {
                    $.each(data, function (name, value) {
                        $form.append("<input type='hidden' name='" + name + "' + value='" + value + "' />");
                    });
                }
                if (target) {
                    $form.attr("target", target);
                }
                $form.attr("action", url).appendTo($("body")).submit();
                setTimeout(function () {
                    $form.remove();
                }, 0);
                return this;
            },
            /**
             * 拼接restful风格的URL
             * @param url
             * @param data
             * @returns {*}
             */
            restfulApi: function (url, data) {
                if (!url || !data) {
                    return url;
                }
                var m = url.match(/\{(.+?)\}/g);
                if (m && m.length) {
                    $.each(m, function (i, k) {
                        url = url.replace(new RegExp(k, "g"), data[k.replace(/\{|\}/g, "")] || k);
                    });
                }
                return url;
            },
            /**
             * 判断是否Excel文件
             * @param file
             * @returns {boolean}
             */
            isExcel: function (file) {
                var ua = window.navigator.userAgent,
                    src = file.value || "",
                    ext = (src.substring(src.indexOf(".") + 1) || "").toLowerCase(),
                    fileSize = 0;

                // 验证格式
                if ($.inArray(ext, ["xls"])) {
                    master.toast("只允许上传 .xls 格式的文件!", 10000, "warning");
                    return false;
                }
                return true;
            },
            /**
             * 判断文件大小是否合法
             * @param file
             * @param maxSize
             * @returns {boolean}
             */
            isFileSizeValid: function (file, maxSize) {
                var ua = window.navigator.userAgent,
                    fileSize = 0;
                maxSize = maxSize || 2 * 1024 * 1024;
                //验证图片大小
                if (ua.indexOf("MSIE") >= 1) {
                    try {
                        var dynImg = document.getElementById("dynImg");
                        dynImg.dynsrc = src;
                        fileSize = dynImg.fileSize;
                    } catch (e) {
                    }
                } else if (file.files) {
                    fileSize = file.files[0].size;
                }
                if (fileSize == -1) {
                    toast.warning("请选择文件!");
                    return false;
                } else if (fileSize > maxSize) {
                    sweet.warning({
                        text: "文件不得大于 " + maxSize + " Byte"
                    });
                    return false;
                }
                return true;
            },
            /**
             * md5加密
             * @param value
             * @param count
             * @returns {*}
             */
            md5: function (value, count) {
                value = koUnwrap(value);
                count = count || 3;
                while (count--) {
                    value = $.md5(value);
                }
                return value;
            },
            /**
             * 缓存ajax请求
             * @param uniqueKey
             * @param cacheKey
             * @parma fn
             * @return promise
             */
            cacheAjax: (function () {
                var CacheFn = function (cacheKey, cacheData, callback) {
                    var that = this;
                    this.promise = $.Deferred(function () {
                        var deferred = this,
                            deferredResolve = deferred.resolve;
                        that.resolve = function (data) {
                            cacheData[cacheKey] = data;
                            deferredResolve.apply(deferred, arguments);
                        };
                        callback.apply(that, arguments);
                    }).promise();

                }, cacheObj = {};
                return function (uniqueKey, cacheKey, fn) {
                    function doFn(cacheData) {
                        var cacheFn = new CacheFn(cacheKey, cacheData, fn);
                        return cacheFn.promise;
                    }

                    if (cacheObj[uniqueKey]) {
                        var cacheData = cacheObj[uniqueKey];
                        if (cacheData[cacheKey]) {
                            return $.Deferred(function () {
                                this.resolve(cacheData[cacheKey]);
                            }).promise();
                        } else {
                            return doFn(cacheObj[uniqueKey]);
                        }
                    } else {
                        return doFn(cacheObj[uniqueKey] = {});
                    }
                };
            }()),
            /**
             * 显示dialog
             * @param $dialog
             * @param fn
             */
            showDialog: function ($dialog, fn) {
                var $panel = $dialog.children(".panel");
                $dialog.show();
                $panel.css({
                    "margin-left": -$panel.outerWidth() / 2 + "px",
                    "margin-top": -$panel.outerHeight() / 2 + "px"
                }).addClass("animated bounceIn");
                this.animate($panel, "bounceIn", function () {
                    $panel.removeClass("animated bounceIn");
                    if ($.isFunction(fn)) {
                        fn();
                    }
                });
            },
            /**
             * 隐藏dialog
             * @param $dialog
             * @param fn
             */
            hideDialog: function ($dialog, fn) {
                var $panel = $dialog.children(".panel");
                this.animate($panel, "bounceOut", function () {
                    $panel.removeClass("animated bounceOut");
                    $dialog.hide();
                    if ($.isFunction(fn)) {
                        fn();
                    }
                });
            },
            /**
             * 显示带iframe的dialog
             * @param $dialog
             * @param src
             */
            showIframeDialog: function ($dialog, src) {
                var $body = $dialog.find(".panel-body"),
                    $loading = $('<div class="panel-refreshing"><div class="spinner"><div class="se rotating-plane"></div></div></div>'),
                    $iframe = $('<iframe style="border: none; display: none; position: absolute; left: 0;top: 0; width: 100%; height: 100%; overflow: auto;"></iframe>'),
                    iframe = $iframe[0],
                    $selectBtn = $dialog.find(".btn-select");

                function iframeLoaded() {
                    if (!iframe.src) {
                        return;
                    }
                    iframe.contentWindow.$dialog = $dialog;
                    $body.removeClass("panel-loading");
                    $loading.remove();
                    if (iframe.detachEvent) {
                        iframe.detachEvent("onload", iframeLoaded);
                    } else {
                        iframe.onload = null;
                    }
                    $selectBtn.on("click.select", function () {
                        var data = koUnwrap(iframe.contentWindow.getFileList());
                        if (window["viewModel"] && $.isFunction(window["viewModel"]["afterUpload"])) {
                            window["viewModel"]["afterUpload"]([].concat.apply(data), $dialog.data("upload-options"));
                        }
                        utils.hideDialog($dialog);
                    });
                    $iframe.show();
                }

                function load() {
                    $body.addClass("panel-loading").empty().append($loading).append($iframe);
                    $selectBtn.off("click.select");
                    iframe.src = src;
                }

                $body.addClass("panel-loading").empty();
                if (iframe.attachEvent) {
                    iframe.attachEvent("onload", iframeLoaded);
                } else {
                    iframe.onload = iframeLoaded;
                }
                if ($dialog.is(":hidden")) {
                    this.showDialog($dialog, load);
                } else {
                    load();
                }
            },
            /**
             * 载入初始的图片
             * @param $dom
             * @param imgList
             */
            loadDropImages: function ($dom, imgList) {
                imgList = koUnwrap(imgList);
                if ($dom && imgList) {
                    var drop = $dom.data("dropzone");
                    if (drop) {
                        drop.loadImages(imgList);
                    }
                }
            },
            /**
             * 执行animate动画
             * @param $dom
             * @param animation
             * @param fn
             */
            animate: function ($dom, animation, fn) {
                $dom.show().addClass(animation + ' animated')
                    .off(ANIMATION_END).on(ANIMATION_END, function () {
                        $(this).removeClass(animation + ' animated');
                        if (fn) {
                            fn.call(this);
                        }
                    });
            },
            /**
             * 延迟执行
             * @param time
             * @param fn
             */
            delay: function (time, fn) {
                setTimeout(fn, time);
            }
        },
        mode = {
            /**
             * 通用单例模式
             * var getBall = mode.singleton(ballConstructor);
             * var ball = getBall();
             * @param objConstructor
             * @returns {Function}
             */
            singleton: function (objConstructor) {
                var instance;
                return function () {
                    return instance || (instance = objConstructor.apply(this, arguments));
                }
            }
        },
        work = {
            FILE: {
                IMAGE: {
                    EXTS: ["jpg", "jpeg", "png", "gif"],
                    MAX_SIZE: 2 * 1024 * 1024
                },
                XLS: {
                    EXTS: ['xls'],
                    MAX_SIZE: 10 * 1024 * 1024
                }
            },
            /**
             * 导入excel
             * @param fileInput
             * @param options
             */
            uploadXls: function (fileInput, options) {
                options = $.extend(true, {
                    url: fullPath + "xls/importXls",
                    typeOrValidateFn: "XLS",
                    success: function (result, status) {
                        if (result.status) {
                            master.toast.success("导入成功", "请从下载的Excel中查证记录是否导入");
                            if ($.isFunction(options.afterUpload)) {
                                options.afterUpload(result.data, options.data, fileInput);
                            }
                        } else {
                            $.alert(result.message);
                        }
                        $("body").uncover();
                    },
                    error: function (data, status, e) {
                        master.toast.error("导入失败");
                        $("body").uncover();
                    }
                }, options);
                $("body").cover();
                work.upload(fileInput, options);
            },
            /**
             * 上传图片
             * @param fileInput
             * @param options
             */
            uploadImage: function (fileInput, options) {
                options = $.extend(true, {
                    url: fullPath + "image/upload",
                    typeOrValidateFn: "IMAGE",
                    success: function (result, status) {
                        if (result.status === 1) {
                            master.toast.success("上传成功!");
                            if ($.isFunction(options.afterUpload)) {
                                options.afterUpload(result.data, options.data, fileInput);
                            }
                        } else {
                            master.toast.warning("上传失败!");
                        }
                        $("body").uncover();
                    },
                    error: function (data, status, e) {
                        master.toast.warning("上传异常!");
                        $("body").uncover();
                    }
                }, options);
                $("body").cover();
                work.upload(fileInput, options);
            },
            /**
             * 文件上传
             * @param fileInput
             * @param options
             */
            upload: function (fileInput, options) {
                options = $.extend(true, {
                    url: "",
                    data: {},
                    typeOrValidateFn: "IMAGE",
                    success: $.noop,
                    error: $.noop
                }, options);
                if ($.isFunction(options.typeOrValidateFn)) {
                    if (options.typeOrValidateFn() !== true) {
                        return;
                    }
                } else {
                    var fileType = work.FILE[options.typeOrValidateFn] || work.FILE.IMAGE;
                    if (work.validateUpload(fileInput, fileType.EXTS, fileType.MAX_SIZE) !== true) {
                        return;
                    }
                }
                $.ajaxFileUpload({						// 异步上传
                    url: options.url,
                    secureuri: false,
                    fileElementId: fileInput.id,
                    dataType: 'json',
                    data: options.data,
                    success: options.success,
                    error: options.error
                });
            },
            validateUpload: function (file, exts, maxSize) {
                var ua = window.navigator.userAgent;
                var src = file.value;
                var ext = (src.substring(src.indexOf(".") + 1) || "").toLowerCase();
                var fileSize = 0;
                // 验证文件格式
                if (ko.utils.arrayIndexOf(exts, ext) === -1) {
                    master.toast.warning("只允许上传 " + exts.join(",") + " 格式的文件!");
                    return false;
                }
                //验证文件大小
                if (maxSize > 0) {
                    if (ua.indexOf("MSIE") >= 1) {
                        var dynImg = document.getElementById("dynImg");
                        dynImg.dynsrc = src;
                        fileSize = dynImg.fileSize;
                    } else if (file.files) {
                        fileSize = file.files[0].size;
                    }
                    if (fileSize == -1) {
                        master.toast.warning("请选择文件!");
                        return false;
                    } else if (fileSize > maxSize) {
                        master.toast.warning("文件不得大于 " + Math.round(maxSize / 1024) + " KB");
                        return false;
                    }
                }
                return true;
            },
            /**
             * 上传后回调函数
             * @param response
             * @param data
             * @param viewModel
             */
            afterUpload: function (response, data, viewModel) {
                if (!response.length) {
                    return;
                }
                if (data["koRefer"]) {
                    var o = master.fmt.referKo(data["koRefer"], viewModel);
                    if (master.ko.isKoArray(o)) {
                        if (response.length > 1) {
                            $.each(response, function (i, v) {
                                response[i] = (v || "").replace(IMG_SHOW_ROOT, '');
                            });
                            o(response);
                        } else {
                            o.push(response[0]);
                        }
                    } else {
                        o(response[0]);
                    }
                }
            },
            /**
             * 移除图片回调函数
             * @param koRefer
             * @param index
             * @param viewModel
             */
            removeUpload: function (koRefer, index, viewModel) {
                var o = master.fmt.referKo(koRefer, viewModel);
                if (master.ko.isKoArray(o)) {
                    o.splice(index);
                } else {
                    o("");
                }
            }
        };


    $.extend(true, master, {
        fmt: format,
        toast: toast,
        sweet: sweet,
        utils: utils,
        ko: koWrap,
        mode: mode,
        work: work
    });


}());