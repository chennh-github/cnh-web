<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=yes">

    <@link href="static/css/system/frame/css@family=Open+Sans.css,
                 static/js/asserts/bootstrap/v3.3.5/css/bootstrap.css,
                 static/js/asserts/jquery-cover/v1.0.0/css/jquery.cover.css,
                 static/js/asserts/jquery-confirm/v2.5.1/css/jquery-confirm.css,
                 static/js/asserts/jquery-mCustomScrollbar/v3.1.5/css/jquery.mCustomScrollbar.css,
                 static/css/system/login/login.css" />
    <style>
        .app__top {
            height: 24rem;
        }
        .app__bot {
            height: 29rem;
        }
        .app__meetings {
            height: 100%;
            padding: 0 0 5%;
            overflow-x: hidden;
            overflow-y: auto;
        }
    </style>
</head>
<body>
<div class="cont">
    <div class="demo">
        <div class="login">
            <div class="login__check"></div>
            <form class="login__form">
                <div class="login__row">
                    <svg class="login__icon name svg-icon" viewBox="0 0 20 20">
                        <path d="M0,20 a10,8 0 0,1 20,0z M10,0 a4,4 0 0,1 0,8 a4,4 0 0,1 0,-8" />
                    </svg>
                    <input type="text" class="login__input name" placeholder="Username" data-bind="attr: {disabled: model.animating()}, textinput: model.admin.account"/>
                </div>
                <div class="login__row">
                    <svg class="login__icon pass svg-icon" viewBox="0 0 20 20">
                        <path d="M0,20 20,20 20,8 0,8z M10,13 10,16z M4,8 a6,8 0 0,1 12,0" />
                    </svg>
                    <input type="password" class="login__input pass" placeholder="Password" data-bind="attr: {disabled: model.animating()}, textinput: model.admin.password"/>
                </div>
                <button type="button" class="login__submit" data-bind="click: login">Sign in</button>
            </form>
        </div>
        <div class="app">
            <div class="app__top">
                <div class="app__menu-btn">
                    <span></span>
                </div>
                <svg class="app__icon search svg-icon" viewBox="0 0 20 20">
                    <path d="M20,20 15.36,15.36 a9,9 0 0,1 -12.72,-12.72 a 9,9 0 0,1 12.72,12.72" />
                </svg>
                <p class="app__hello">welcome back</p>
                <div class="app__user">
                    <img src="<@path path="static/images/system/user.jpg"/>" class="app__user-photo" />
                    <!--<span class="app__user-notif">3</span>-->
                </div>
            </div>
            <div class="app__bot">
                <div class="app__meetings">
                    <div class="app__meeting">
                        <img src="http://s3-us-west-2.amazonaws.com/s.cdpn.io/142996/profile/profile-80_5.jpg" alt="" class="app__meeting-photo" />
                        <p class="app__meeting-name">Feed the cat</p>
                        <p class="app__meeting-info">
                            <span class="app__meeting-time">8 - 10am</span>
                            <span class="app__meeting-place">Real-life</span>
                        </p>
                    </div>
                    <div class="app__meeting">
                        <img src="//s3-us-west-2.amazonaws.com/s.cdpn.io/142996/profile/profile-512_5.jpg" alt="" class="app__meeting-photo" />
                        <p class="app__meeting-name">Feed the cat!</p>
                        <p class="app__meeting-info">
                            <span class="app__meeting-time">1 - 3pm</span>
                            <span class="app__meeting-place">Real-life</span>
                        </p>
                    </div>
                    <div class="app__meeting">
                        <img src="//s3-us-west-2.amazonaws.com/s.cdpn.io/142996/profile/profile-512_5.jpg" alt="" class="app__meeting-photo" />
                        <p class="app__meeting-name">FEED THIS CAT ALREADY!!!</p>
                        <p class="app__meeting-info">
                            <span class="app__meeting-time">This button is just for demo ></span>
                        </p>
                    </div>
                    <div class="app__meeting">
                        <img src="//s3-us-west-2.amazonaws.com/s.cdpn.io/142996/profile/profile-512_5.jpg" alt="" class="app__meeting-photo" />
                        <p class="app__meeting-name">FEED THIS CAT ALREADY!!!</p>
                        <p class="app__meeting-info">
                            <span class="app__meeting-time">This button is just for demo ></span>
                        </p>
                    </div>
                    <div class="app__meeting">
                        <img src="//s3-us-west-2.amazonaws.com/s.cdpn.io/142996/profile/profile-512_5.jpg" alt="" class="app__meeting-photo" />
                        <p class="app__meeting-name">FEED THIS CAT ALREADY!!!</p>
                        <p class="app__meeting-info">
                            <span class="app__meeting-time">This button is just for demo ></span>
                        </p>
                    </div>
                </div>
            </div>
            <div class="app__logout" title="退出" data-bind="click: logout">
                <svg class="app__logout-icon svg-icon" viewBox="0 0 20 20">
                    <path d="M6,3 a8,8 0 1,0 8,0 M10,0 10,12"/>
                </svg>
            </div>
        </div>
    </div>
</div>

<script>
    var fullPath = "<@path path=''/>";
    var exponent = "${exponent}";
    var modulus = "${modulus}";
</script>
<@script src="static/js/asserts/jquery/v1.11.0/js/jquery.min.js,
              static/js/asserts/knockout/v3.4.0/js/knockout.js,
              static/js/asserts/knockout-mapping/v2.4.1/js/knockout.mapping.js,
              static/js/asserts/jquery-cover/v1.0.0/js/jquery.cover.js,
              static/js/asserts/jquery-ajax/v1.0.0/js/jquery.ajax.js,
              static/js/asserts/jquery-confirm/v2.5.1/js/jquery-confirm.js,
              static/js/asserts/jquery-mCustomScrollbar/v3.1.5/js/jquery.mCustomScrollbar.concat.min.js,
              static/js/asserts/rsa/v1.0/js/security.js,
              static/js/system/login/login.js"/>
</body>
</html>