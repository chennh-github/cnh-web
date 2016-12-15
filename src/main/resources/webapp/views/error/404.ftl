<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>404</title>
    <!-- Fonts -->
    <@link href="css/system/frame/css@family=Raleway.css,
                 css/system/frame/css@family=Open+Sans.css" />
    <!-- / Fonts -->

    <!-- Style Sheets -->
    <@link href="static/css/asserts/animate/v3.2.0/animate.css,
                 static/css/asserts/font-awesome/v4.4.0/css/font-awesome.css,
                 static/js/asserts/bootstrap/v3.3.5/css/bootstrap.css,
                 static/js/asserts/pace/v1.0.2/css/themes/black/pace.css,
                 static/css/system/frame/themes/theme-all.css,
                 static/css/system/frame/style.css" />
    <!-- / Style Sheets -->
    <@link href="static/css/system/frame/error.css" />
    <script>
        function goUrl() {
            var url = (document.getElementById('search-input').value || '').replace(/\s+/g, '');
            if (url) {
                window.location = url;
            }
        }
    </script>
</head>
<body data-theme="default" class="error-404">
<div class="error-top animated bounce">
        <span class="pre-error-code">
            斯人已如风，页面亦羽歌。
        </span>
        <span class="error-code">
            404
        </span>
</div>
<div class="error-bottom">
        <span class="post-error-code">
            页面不存在
        </span>
        <span class="help">
            <p>输入特定的URL地址并前往</p>
            <form>
                <div class="input-group">
                    <span class="search-input-icon">
                        <input id="search-input"
                               class="search-input"
                               type="text"
                               placeholder="url...">
                        <i class="fa fa-search search-icon"></i>
                    </span>
                    <span class="input-group-btn">
                        <button class="btn btn-inverse" type="button" onclick="goUrl()">确定!</button>
                    </span>
                </div>
            </form>
            <p>
                前往 <a href="${fullPath}system/index">主页</a>，或者返回
                <a href="javascript: void(0);" onclick="window.history.back()">上一页</a>
            </p>
        </span>

    <br/>
</div>
<@script src="static/js/asserts/jquery/v2.1.4/js/jquery.min.js,
              static/js/asserts/bootstrap/v3.3.5/js/bootstrap.js,
              static/js/asserts/pace/v1.0.2/js/pace.js"/>
</body>
</html>