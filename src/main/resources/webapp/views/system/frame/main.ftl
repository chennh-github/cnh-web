<#macro header title="后台管理">
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>${title}</title>

    <!-- Fonts -->
    <@link href="css/system/frame/css@family=Raleway.css,
                 css/system/frame/css@family=Open+Sans.css" />
    <!-- / Fonts -->

    <!-- Style Sheets -->
    <@link href="static/css/asserts/animate/v3.2.0/animate.css,
                 static/css/asserts/font-awesome/v4.4.0/css/font-awesome.css,
                 static/js/asserts/bootstrap/v3.3.5/css/bootstrap.css,
                 static/js/asserts/jquery-cover/v1.0.0/css/jquery.cover.css,
                 static/js/asserts/jquery-confirm/v2.5.1/css/jquery-confirm.css,
                 static/js/asserts/pace/v1.0.2/css/themes/black/pace.css,
                 static/js/asserts/toastr/v2.1.2/css/toastr.css,
                 static/js/asserts/sweetalert/v1.0.1/css/sweetalert.css,
                 static/css/system/frame/themes/theme-all.css,
                 static/css/system/frame/style.css,
                 static/css/system/frame/setting.css" />
    <!-- / Style Sheets -->
    <#nested/>
    <@link href="static/css/system/frame/custom.css" />
    <script>
        var imgShowRoot = "${imgShowRoot}";
        var staticVersion = "${staticVersion}";
        var fullPath = "<@path path=''/>"
    </script>
</head>
</#macro>

<#macro body class="" modular="">
<body data-theme="default" class="${class}">
<div id="wrapper">
    <nav id="sidebar-nav" class="sidebar-nav">
        <div id="sidebar-wrapper" class="sidebar-wrapper" data-background="default">
            <#include "/system/frame/left.ftl">
        </div>
    </nav>


    <div id="container-wrapper" class="container-wrapper">

        <!-- Include the container navbar -->
        <div id="container-navbar" class="container-navbar navbar">
            <#include "/system/frame/header.ftl">
        </div>

        <!-- Include the container-header -->
        <#if modular != "">
            <div id="container-header" class="container-header">
                <div class="row">
                    <div class="col-md-12">
                        <h2>${modular}</h2>
                    </div>
                </div>
            </div>
        </#if>

        <!-- main container -->
        <div id="container-content" class="container-content">
            <div id="bootstrap">
                <#nested />
            </div>
        </div>

        <!-- Include the demo-settings -->
        <div>
            <#include "/system/frame/setting.ftl">
        </div>


        <!-- container-footer -->
        <div id="container-footer" class="container-footer">
           <#include "/system/frame/footer.ftl">
        </div>

        <!-- Scroll up -->
        <a href="#" class="fa scrollup"></a>
    </div>

</div>
</#macro>

<#macro block class="" modular="">
<body data-theme="default" class="${class}">
<div id="bootstrap" style="background: #fff; padding: 30px 0 50px;">
    <#nested />
</div>
</#macro>

<#macro footer>
    <@script src="static/js/asserts/json2/v1.0.0/js/json2.js,
                  static/js/asserts/jquery/v2.1.4/js/jquery.min.js,
                  static/js/asserts/bootstrap/v3.3.5/js/bootstrap.js,

                  static/js/asserts/moment/v2.10.6/js/moment.js,
                  static/js/asserts/moment/v2.10.6/js/moment.zh-cn.js,

                  static/js/asserts/knockout/v3.4.0/js/knockout.js,
                  static/js/asserts/knockout-mapping/v2.4.1/js/knockout.mapping.js,


                  static/js/asserts/jquery-cover/v1.0.0/js/jquery.cover.js,
                  static/js/asserts/jquery-ajax/v1.0.0/js/jquery.ajax.js,
                  static/js/asserts/jquery-confirm/v2.5.1/js/jquery-confirm.js,


                  static/js/asserts/toastr/v2.1.2/js/toastr.js,
                  static/js/asserts/pace/v1.0.2/js/pace.js,
                  static/js/asserts/metisMenu/v1.1.3/js/metisMenu.js,
                  static/js/asserts/jquery-slimScroll/v1.3.3/js/jquery.slimscroll.js,
                  static/js/asserts/sweetalert/v1.0.1/js/sweetalert.js,


                  static/js/system/frame/setting.js,
                  static/js/system/frame/main.js,
                  static/js/system/share/initReady.js,
                  static/js/system/share/master.js" />
    <#nested />
</body>
</#macro>





