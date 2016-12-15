/**
 * Author: Administrator
 * Date: 2016/8/25
 * Description:
 *      1. created
 */
;
(function ($) {

    "use strict";

    var global = (0, eval)("this"),

        ready = global["ready"] || (global["ready"] = {}),

        sequence = 1;


    /**
     * parse JSON
     * @param jsonString
     * @returns {*}
     */
    function parseJSON(jsonString) {
        return new Function("return " + jsonString)();
    }

    /**
     * default $dom
     * @param obj
     * @param jquerySelector
     * @returns {*|HTMLElement}
     */
    function defaultDom(obj, jquerySelector) {
        if (obj && obj.selector) {
            return obj;
        }
        return $(jquerySelector);
    }

    /**
     * execute plugin
     * @param $dom
     * @param fnName
     * @param options
     * @param optionName
     * @returns {*}
     */
    function executePlugin($dom, fnName, options, optionName) {
        return $dom.each(function () {
           var $this = $(this),
               domOptionsText = $this.attr(optionName);
            if ($.isFunction(fnName)) {
                return fnName.call(this, options, domOptionsText);
            } else {
                return $this[fnName]($.extend(true, parseJSON(domOptionsText || "{}"), options));
            }
        });
    }

    $.extend(true, ready, {

        components: {
            /**
             * 时钟 picker
             * @param $dom
             * @param options
             * @returns {*}
             */
            clockPicker: function ($dom, options) {
                if (!$.fn.clockpicker) {
                    return;
                }
                return executePlugin(defaultDom($dom, ".clock-picker"), "clockpicker", $.extend(true, {
                    autoclose: true,
                    donetext: "确定"
                }, options), "data-clock");
            },
            /**
             * 日期 picker
             * @param $dom
             * @param options
             * @returns {*}
             */
            datePicker: function ($dom, options) {
                if (!$.fn.datepicker) {
                    return;
                }
                return executePlugin(defaultDom($dom, ".date-picker"), "datepicker", $.extend(true, {
                    autoclose: true,
                    format: "yyyy-mm-dd",
                    language: "zh-CN",
                    todayBtn: true,
                    clearBtn: true,
                    todayHighlight: true
                }, options), "data-date");
            },
            /**
             * jquery-datetimepicker初始化
             * @param $dom
             * @param options
             * @returns {*}
             */
            datetimePicker: function ($dom, options) {
                if (!$.fn.datetimepicker) {
                    return;
                }
                $.datetimepicker.setLocale('zh');
                return executePlugin(defaultDom($dom, ".datetime-picker"), "datetimepicker", $.extend(true, {
                    format: 'Y-m-d',
                    formatTime: 'H:i',
                    formatDate: 'Y-m-d',
                    closeOnDateSelect: true,
                    timepicker: false,
                    dayOfWeekStart: 1
                }, options), "data-datetime");
            },
            /**
             * bootstrap-select picker
             * @param $dom
             * @param options
             * @returns {*}
             */
            selectPicker: function ($dom, options) {
                if (!$.fn.selectpicker) {
                    return;
                }
                return executePlugin(defaultDom($dom, ".select-picker"), "selectpicker", $.extend(true, {}, options), "data-select");
            },
            /**
             * jquery-select2 picker
             * @param $dom
             * @param options
             * @returns {*}
             */
            select2Picker: function ($dom, options) {
                if (!$.fn.select2) {
                    return;
                }
                return executePlugin(defaultDom($dom, ".select2-picker"), "select2", $.extend(true, {}, options), "data-select2");
            },
            /**
             * 密码强度提示 picker
             * @param $dom
             * @param options
             * @returns {*}
             */
            passwordPicker: function ($dom, options) {
                if (!window['deePassword']) {
                    return;
                }
                return defaultDom($dom, ".password-picker").each(function () {
                    var $this = $(this);
                    $this.parent().addClass("dp-wrapper")
                        .append('<div class="dp-bar"></div>')
                        .append('<div class="dp-result"></div>');
                    $this.on("keyup", function () {
                        deePassword($this);
                    });
                });
            },
            /**
             * jquery-knob picker
             * @param $dom
             * @param options
             * @returns {*}
             */
            knobPicker: function ($dom, options) {
                if (!$.fn.knob) {
                    return;
                }
                return executePlugin(defaultDom($dom, ".knob-picker").css({
                    top: 0,
                    left: ((options && options.width || 100) - 12) * 5 / 4 + "px"
                }), function (options, domOptions) {
                    var $this = $(this);
                    $this.knob($.extend(true, {
                        width: 100,
                        height: 100
                    }, domOptions, options));
                }, options, "data-knob");
            },
            /**
             * jquery-maskedinput picker
             * @param $dom
             * @param options
             * @returns {*}
             */
            maskPicker: function ($dom, options) {
                if (!$.fn.mask) {
                    return;
                }
                return executePlugin(defaultDom($dom, ".mask-picker"), function (options, domOptionsText) {
                    return $(this).mask(domOptionsText);
                }, options, "data-mask");
            },
            /**
             * jquery-touchspin picker
             * @param $dom
             * @param options
             * @returns {*}
             */
            spinPicker: function ($dom, options) {
                if (!$.fn.TouchSpin) {
                    return;
                }
                return executePlugin(defaultDom($dom, ".spin-picker"), "TouchSpin", options, "data-spin");
            },
            /**
             * Dropzone picker
             * @param $dom
             * @param options
             * @returns {*}
             */
            filePicker: function ($dom, options) {
                if (!$.fn.dropzone) {
                    return;
                }
                return defaultDom($dom, ".file-picker").each(function () {
                    var $this = $(this),
                        customOptions = parseJSON($this.attr("data-dropzone") || "{}");
                    if (!customOptions.url) {
                        customOptions.url = fullPath + 'image/upload';
                    }
                    customOptions.params = customOptions.params || {};
                    if (!customOptions.params.businessType && customOptions.businessType) {
                        customOptions.params.businessType = customOptions.businessType;
                    }
                    $this.dropzone($.extend(true, {}, Dropzone.options.dropZone, customOptions, options));
                });
            },
            validatePicker: function ($dom, options) {
                if (!$.fn.validate) {
                    return;
                }
                return defaultDom($dom, ".validate-picker").each(function () {
                    var $form = $(this);
                    var validator = $form.data("validator");
                    var $errorForm = $form.find(".errorForm");
                    var opts = {
                        errorElement: "span",
                        ignore: ":hidden:not(.bs-select-hidden)",
                        submitHandler: function (form) {
                            $(form).submit();
                        },
                        highlight: function (element) {
                            jQuery(element).closest('.form-group')
                                .removeClass('has-success')
                                .addClass('has-error');
                        },
                        success: function (element) {
                            jQuery(element).closest('.form-group')
                                .removeClass('has-error');
                        }
                    };
                    if (validator) {
                        return;
                    }
                    if ($errorForm.length) {
                        opts.errorLabelContainer = $errorForm;
                    } else {
                        opts.errorPlacement = function ($error, $element) {
                            // error是错误提示元素span对象  element是触发错误的input对象
                            // 发现即使通过验证 本方法仍被触发
                            // 当通过验证时 error是一个内容为空的span元素
                            var label = ( $element.parents(".form-group").find("label:first").text() || "" ).replace(/[\*|\:|\：]/g, " ");
                            var $parent = $element.parent(".input-group");
                            var isSelect = $element.hasClass("select2-picker") || $element.hasClass("select-picker");

                            $error.text(label + $error.text());
                            if ($parent && $parent.length) {
                                $error.insertAfter($parent);
                            } else if (isSelect) {
                                $element.parent().append($error);
                            } else {
                                $error.insertAfter($element);
                            }
                        }
                    }
                    $(this).validate(opts);
                });
            },
            foxiboxPicker: function ($dom, options) {
                if (!$.fn.foxibox) {
                    return;
                }
                return defaultDom($dom, ".foxibox-picker").foxibox();
            },
            imgPicker: function ($dom, options) {
                if (!$.fn.viewer) {
                    return;
                }
                return defaultDom($dom, ".img-picker").viewer('destroy').viewer();
            }
        },

        initReady: function () {

            $.extend(true, $.ajaxSettings, {
                dataType: "text"
            });

            // jconfirm.pluginDefaults
            $.extend(true, jconfirm.pluginDefaults, {
                title: '系统提示',
                confirmButton: '确定',
                cancelButton: '关闭',
                confirmButtonClass: 'btn-theme'
            });

            // 自动给file添加name和id
            $('input:file').each(function () {
                var $this = $(this),
                    uniqueNo = 'file__auto__sequence__' + sequence++;
                if (!$this.attr('id')) {
                    $this.attr('id', uniqueNo);
                }
                if (!$this.attr('name')) {
                    $this.attr('name', uniqueNo);
                }
            });

            // data-target=dialog
            $('[data-toggle=dialog]').each(function () {
                $(this).on('click.dialog', function () {
                    master.utils.showDialog($($(this).attr('data-target')))
                });
            });

            // panel
            $('.panel').each(function () {
                var $panel = $(this),
                    $panelBody = $panel.find('.panel-body');
                $panel.find('[data-toggle=collapse]').on('click.dialog.collapse', function () {
                    $(this).find('.fa').toggleClass('fa-minus fa-plus');
                    $panelBody.slideToggle();
                });
                $panel.find('[data-toggle=close]').on('click.dialog.close', function () {
                    var $dialog = $panel.parents('.dialog');
                    if ($dialog.length) {
                        master.utils.hideDialog($dialog);
                    } else {
                        master.utils.animate($panel, "bounceOut", function () {
                            $panel.removeClass("animated bounceOut");
                        });
                    }
                });
            });
        },
        /**
         * 初始化组件
         * 1、传递数组，初始化components中对应名称的组件
         * 2、传递字符串，初始化该名称对应的组件，并传递其余参数
         */
        init: function () {
            var readyComponents = this.components;
            if ($.isArray(arguments[0])) {
                var components = arguments[0];
                $.each(components, function (i, componentName) {
                    if (readyComponents[componentName]) {
                        readyComponents[componentName]();
                    }
                });
            } else if (readyComponents[arguments[0]]) {
                var args = [];
                Array.prototype.push.apply(args, arguments);
                readyComponents[arguments[0]].apply(readyComponents, args.slice(1));
            }

        }


    });


    $(function () {
        ready.initReady();
    });


    // Date 扩展
    if (!Date.prototype.format) {
        // 日期格式化,yyyy-MM-dd hh:mm:ss
        Date.prototype.format = function (format) {
            var o = {
                "M+": this.getMonth() + 1, 						    // month
                "d+": this.getDate(), 								// day
                "h+": this.getHours(), 							    // hour
                "m+": this.getMinutes(), 							// minute
                "s+": this.getSeconds(), 							// second
                "q+": Math.floor((this.getMonth() + 3) / 3),		// quarter
                "S": this.getMilliseconds() 						// millisecond
            }
            if (/(y+)/.test(format)) {
                format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            }

            for (var k in o) {
                if (new RegExp("(" + k + ")").test(format)) {
                    format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
                }
            }
            return format;
        }
    }

}(jQuery));