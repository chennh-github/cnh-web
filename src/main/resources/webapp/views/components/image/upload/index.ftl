<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>文件上传</title>
    <style type="text/css">
        .container { margin-top: 50px; }
        .file-drop-zone { height: auto; max-height: 600px; overflow: auto; }
        .kv-file-upload, .kv-file-remove { padding: 5px; }
    </style>
</head>
<body>
<!--breadcrumbs end-->
<div class="container">
    <div class="section">

        <!--file-upload-->
        <div class="form-group" data-bind="
				component: {
					name: 'x-uploader',
					params: {
						fileList: model.fileList,
						upload: {
						    maxFileCount: ${maxCount!5},
						},
						data: {
						    businessType: '${businessType}'
						}
					}
				}">
        </div>
    </div>
</div>
<script>
    var urls = "${urls!}";
    var callback = "${callback!}";
    var imgShowRoot = "${imgShowRoot}";
    var staticVersion = "${staticVersion}";
    var fullPath = "<@path path=''/>"
</script>
<@script src="static/js/asserts/jquery/v2.1.4/js/jquery.js,
              static/js/global/require/require-config.js" />
<script src="<@path path='static/js/asserts/requirejs/v2.1.11/js/require.js' />"
        data-main="<@path path='static/js/global/require/require-main' />"
        data-app="<@path path='static/js/components/image/upload/app' />"
        async="async" defer="defer" ></script>
</body>
</html>