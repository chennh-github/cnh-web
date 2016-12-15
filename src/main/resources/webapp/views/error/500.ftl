<#import "/system/share/macro.ftl" as macro>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>500</title>
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
<body data-theme="default" class="error-500">
<div class="error-top animated bounce">
        <span class="pre-error-code">
            糟糕，出错啦！
        </span>
        <span class="error-code">
            500
        </span>
</div>
<div class="error-bottom">
        <span class="post-error-code">
            内部服务错误,<button class="btn btn-danger" data-toggle="modal" data-target="#errModal">详情</button>
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
                        <button class="btn btn-inverse" type="button" onclick="goUrl()">Go!</button>
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

<@macro.modal id="errModal" title="错误详情" class="modal-danger">
<div class="modal-body">
    <div class="well" style="color: #333; text-align: left;">
        <p><strong>错误：</strong>${error!}</p>
        <p><strong>状态：</strong>${status!}</p>
        <p><strong>异常：</strong>${exception!}</p>
        <p><strong>路径：</strong>${path!}</p>
        <p><strong>时间：</strong>${timestamp?string('yyyy-MM-dd hh:mm:ss')}</p>
        <div>
            <strong class="pull-left">详情：</strong>
            <div style="max-height: 200px; overflow-x: hidden; overflow-y: auto;">${message!}</div>
        </div>
    </div>
</div>
</@macro.modal>

<@script src="static/js/asserts/jquery/v2.1.4/js/jquery.min.js,
              static/js/asserts/bootstrap/v3.3.5/js/bootstrap.js,
              static/js/asserts/pace/v1.0.2/js/pace.js"/>
</body>
</html>