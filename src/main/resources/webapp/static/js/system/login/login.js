/**
 * Author: Administrator
 * Date: 2016/9/8
 * Description:
 *      1. created
 */
;
(function () {

    var service = {
            login: function (data) {
                RSAUtils.setMaxDigits(200);
                // 第一个参数为加密指数、第二个参数为解密参数、第三个参数为加密系数
                var key = new RSAUtils.getKeyPair(window['exponent'], '', window['modulus']);
                data.password = RSAUtils.encryptedString(key, data.password);
                return $.ajax({
                    url: fullPath + 'api/system/login',
                    type: 'POST',
                    dataType: 'json',
                    global: false,
                    data: data
                });
            }
        },
        ripple = {
            rip: function (elem, e) {
                $('.ripple').remove();
                var elTop = elem.offset().top,
                    elLeft = elem.offset().left,
                    x = e.pageX - elLeft,
                    y = e.pageY - elTop;
                var $ripple = $("<div class='ripple'></div>");
                $ripple.css({top: y, left: x});
                elem.append($ripple);
            },
            IMG_LIST: [fullPath + 'static/images/system/login/1.jpg',
                fullPath + 'static/images/system/login/2.jpg',
                fullPath + 'static/images/system/login/3.jpg'],
            autoBackground: function () {
                var $cont = $('.cont'),
                    index = 0,
                    IMG_LIST = this.IMG_LIST,
                    l = IMG_LIST.length;
                for (var i = 0; i < l; i++) {
                    (function (src) {
                        var img = new Image();
                        img.onload = function () {
                            img.onload = null;
                        };
                        img.src = src;
                    }(IMG_LIST[i]));
                }

                function switchBackground() {
                    index = index % l;
                    setTimeout(function () {
                        $cont.css('background-image', 'url(' + IMG_LIST[index] + ')');
                    }, 300);

                }

                setInterval(function () {
                    switchBackground(index++);
                }, 6000);
                switchBackground(0);
            }
        };

    var viewModel = {
        model: {
            animating: false,
            admin: {
                account: "",
                password: ""
            }
        },
        initViewModel: function () {
            this.model = ko.mapping.fromJS(this.model);

            this.initEvents();
            ko.applyBindings(this);
        },
        initEvents: function () {

            $('.login__form').delegate('.login__icon', 'click', function () {
                $(this).siblings('.login__input').focus().select();
            });

            $('.app__meetings').mCustomScrollbar({
                theme: 'minimal-dark'
            });

            ripple.autoBackground();
        },
        login: function (context, e) {
            var that = this;
            if (!this.model.animating()) {
                var $submit = $('.login__submit');
                var $app = $('.app');
                this.model.animating(true);
                ripple.rip($submit, e);
                $submit.blur().addClass('processing');


                var admin = ko.mapping.toJS(this.model.admin);
                ko.utils.objectForEach(admin, function (k, v) {
                    admin[k] = $.trim(v);
                });

                if (!admin.account) {
                    return $.alert('请输入帐号');
                }
                if (!admin.password) {
                    return $.alert('请输入密码');
                }


                service.login(admin).then(function (data) {
                    $submit.addClass('success');

                    setTimeout(function () {
                        $app.show();
                        $app.css('top');
                        $app.addClass('active');
                    }, 330);
                    setTimeout(function () {
                        $('.login').hide().addClass('inactive');
                        $submit.removeClass('processing success');
                        that.model.animating(false);
                    }, 400);
                }, function (err) {
                    $.alert(err.responseJSON.message);
                    $submit.removeClass('processing');
                    that.model.animating(false);
                });
            }
        },
        logout: function () {
            var that = this;
            if (!this.model.animating()) {
                var $louout = $('.app__logout');
                var $app = $('.app');
                var $login = $('.login');

                this.model.animating(true);
                $(".ripple").remove();
                $louout.addClass('clicked');

                setTimeout(function () {
                    $app.removeClass('active');
                    $login.show();
                    $login.css('top');
                    $login.removeClass('inactive');
                }, 680);

                setTimeout(function () {
                    $app.hide();
                    $louout.removeClass('clicked');
                    that.model.animating(false);
                }, 800);
            }
        }
    };


    $(function () {
        viewModel.initViewModel();
    });


}());