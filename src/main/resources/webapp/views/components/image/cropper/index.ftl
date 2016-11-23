<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>图片裁剪</title>
    <!-- Style Sheets -->
<@link href="static/css/asserts/font-awesome/v4.4.0/css/font-awesome.css,
                 static/js/asserts/bootstrap/v3.3.5/css/bootstrap.css,
                 static/js/asserts/jquery-cover/v1.0.0/css/jquery.cover.css,
                 static/js/asserts/jquery-confirm/v2.5.1/css/jquery-confirm.css,
                 static/js/asserts/pace/v1.0.2/css/themes/black/pace.css,
                 static/js/asserts/toastr/v2.1.2/css/toastr.css,
                 static/js/asserts/sweetalert/v1.0.1/css/sweetalert.css,
                 static/js/asserts/cropper/v2.3.3/css/cropper.css,
                 static/css/components/image/cropper/index.css" />
    <!-- / Style Sheets -->
</head>
<body>
<!-- Content -->
<div class="container" id="cropperContainer">
    <div class="row">
        <div class="col-md-9">
            <!-- <h3 class="page-header">Demo:</h3> -->
            <div class="img-container">
                <img id="image">
            </div>
        </div>
        <div class="col-md-3">
            <!-- <h3 class="page-header">Preview:</h3> -->
            <div class="docs-preview clearfix">
                <div class="img-preview preview-lg"></div>
                <div class="img-preview preview-md"></div>
                <div class="img-preview preview-sm"></div>
                <div class="img-preview preview-xs"></div>
            </div>

            <!-- <h3 class="page-header">Data:</h3> -->
            <div class="docs-data">
                <div class="input-group input-group-sm">
                    <label class="input-group-addon" for="dataX">X</label>
                    <input type="text" class="form-control" id="dataX" placeholder="x" readonly data-bind="textinput: model.crop.data.x">
                    <span class="input-group-addon">px</span>
                </div>
                <div class="input-group input-group-sm">
                    <label class="input-group-addon" for="dataY">Y</label>
                    <input type="text" class="form-control" id="dataY" placeholder="y" readonly data-bind="textinput: model.crop.data.y">
                    <span class="input-group-addon">px</span>
                </div>
                <div class="input-group input-group-sm">
                    <label class="input-group-addon" for="dataWidth">Width</label>
                    <input type="text" class="form-control" id="dataWidth" placeholder="width" readonly data-bind="textinput: model.crop.data.width">
                    <span class="input-group-addon">px</span>
                </div>
                <div class="input-group input-group-sm">
                    <label class="input-group-addon" for="dataHeight">Height</label>
                    <input type="text" class="form-control" id="dataHeight" placeholder="height" readonly data-bind="textinput: model.crop.data.height">
                    <span class="input-group-addon">px</span>
                </div>
                <div class="input-group input-group-sm">
                    <label class="input-group-addon" for="dataRotate">Rotate</label>
                    <input type="text" class="form-control" id="dataRotate" placeholder="rotate" readonly data-bind="textinput: model.crop.data.rotate">
                    <span class="input-group-addon">deg</span>
                </div>
                <div class="input-group input-group-sm">
                    <label class="input-group-addon" for="dataScaleX">ScaleX</label>
                    <input type="text" class="form-control" id="dataScaleX" placeholder="scaleX" readonly data-bind="textinput: model.crop.data.scaleX">
                </div>
                <div class="input-group input-group-sm">
                    <label class="input-group-addon" for="dataScaleY">ScaleY</label>
                    <input type="text" class="form-control" id="dataScaleY" placeholder="scaleY" readonly data-bind="textinput: model.crop.data.scaleY">
                </div>
                <div class="input-group input-group-sm">
                    <label class="input-group-addon" for="zoom">zoom</label>
                    <input type="text" class="form-control" id="zoom" placeholder="zoom" readonly data-bind="textinput: model.crop.data.zoom">
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-9 docs-buttons">
            <!-- <h3 class="page-header">Toolbar:</h3> -->
            <div class="btn-group">
                <button type="button" class="btn btn-primary" data-method="setDragMode" data-option="move">
                    <span class="docs-tooltip" data-toggle="tooltip" title="移动模式"><span class="fa fa-arrows"></span></span>
                </button>
                <button type="button" class="btn btn-primary" data-method="setDragMode" data-option="crop">
                    <span class="docs-tooltip" data-toggle="tooltip" title="裁剪模式"><span class="fa fa-crop"></span></span>
                </button>
            </div>

            <div class="btn-group">
                <button type="button" class="btn btn-primary" data-method="zoom" data-option="0.1">
                    <span class="docs-tooltip" data-toggle="tooltip" title="放大10%"><span class="fa fa-search-plus"></span></span>
                </button>
                <button type="button" class="btn btn-primary" data-method="zoom" data-option="-0.1">
                    <span class="docs-tooltip" data-toggle="tooltip" title="缩小10%"><span class="fa fa-search-minus"></span></span>
                </button>
            </div>

            <div class="btn-group">
                <button type="button" class="btn btn-primary" data-method="move" data-option="-10" data-secondoption="0">
                    <span class="docs-tooltip" data-toggle="tooltip" title="左移10px"><span class="fa fa-arrow-left"></span></span>
                </button>
                <button type="button" class="btn btn-primary" data-method="move" data-option="10" data-secondoption="0">
                    <span class="docs-tooltip" data-toggle="tooltip" title="右移10px"><span class="fa fa-arrow-right"></span></span>
                </button>
                <button type="button" class="btn btn-primary" data-method="move" data-option="0" data-secondoption="-10">
                    <span class="docs-tooltip" data-toggle="tooltip" title="上移10px"><span class="fa fa-arrow-up"></span></span>
                </button>
                <button type="button" class="btn btn-primary" data-method="move" data-option="0" data-secondoption="10">
                    <span class="docs-tooltip" data-toggle="tooltip" title="下移10px"><span class="fa fa-arrow-down"></span></span>
                </button>
            </div>

            <div class="btn-group">
                <button type="button" class="btn btn-primary" data-method="rotate" data-option="-45">
                    <span class="docs-tooltip" data-toggle="tooltip" title="逆时针旋转45deg"><span class="fa fa-rotate-left"></span></span>
                </button>
                <button type="button" class="btn btn-primary" data-method="rotate" data-option="45">
                    <span class="docs-tooltip" data-toggle="tooltip" title="顺时针旋转45deg"><span class="fa fa-rotate-right"></span></span>
                </button>
                <button type="button" class="btn btn-primary" data-method="rotate" data-option="180">
                    <span class="docs-tooltip" data-toggle="tooltip" title="旋转180deg">180°</span>
                </button>
            </div>

            <div class="btn-group">
                <button type="button" class="btn btn-primary" data-method="scaleX" data-option="-1">
                    <span class="docs-tooltip" data-toggle="tooltip" title="水平翻转"><span class="fa fa-arrows-h"></span></span>
                </button>
                <button type="button" class="btn btn-primary" data-method="scaleY" data-option="-1">
                    <span class="docs-tooltip" data-toggle="tooltip" title="垂直翻转"><span class="fa fa-arrows-v"></span></span>
                </button>
            </div>

            <div class="btn-group">
                <button type="button" class="btn btn-primary" data-method="crop">
                    <span class="docs-tooltip" data-toggle="tooltip" title="启用插件"><span class="fa fa-check"></span></span>
                </button>
                <button type="button" class="btn btn-primary" data-method="destroy">
                    <span class="docs-tooltip" data-toggle="tooltip" title="销毁插件"><span class="fa fa-power-off"></span></span>
                </button>
                <button type="button" class="btn btn-primary" data-method="clear">
                    <span class="docs-tooltip" data-toggle="tooltip" title="清空选区"><span class="fa fa-remove"></span></span>
                </button>
            </div>

            <div class="btn-group">
                <button type="button" class="btn btn-primary" data-method="disable">
                    <span class="docs-tooltip" data-toggle="tooltip" title="锁定插件"><span class="fa fa-lock"></span></span>
                </button>
                <button type="button" class="btn btn-primary" data-method="enable">
                    <span class="docs-tooltip" data-toggle="tooltip" title="解锁插件"><span class="fa fa-unlock"></span></span>
                </button>
            </div>

            <div class="btn-group">
                <button type="button" class="btn btn-primary" data-method="reset">
                    <span class="docs-tooltip" data-toggle="tooltip" title="重置"><span class="fa fa-refresh"></span></span>
                </button>
            </div>

            <div class="btn-group btn-group-crop">
                <button type="button" class="btn btn-primary" data-method="getCroppedCanvas">
                    <span class="docs-tooltip" data-toggle="tooltip" title="下载自定义选区">下载自定义选区</span>
                </button>
                <button type="button" class="btn btn-primary" data-method="getCroppedCanvas"
                        data-option="{ &quot;width&quot;: 160, &quot;height&quot;: 90 }">
                    <span class="docs-tooltip" data-toggle="tooltip" title="下载160x90选区">160&times;90</span>
                </button>
                <button type="button" class="btn btn-primary" data-method="getCroppedCanvas"
                        data-option="{ &quot;width&quot;: 320, &quot;height&quot;: 180 }">
                    <span class="docs-tooltip" data-toggle="tooltip" title="下载320x180选区">320&times;180</span>
                </button>
            </div>

            <!-- Show the cropped image in modal -->
            <div class="modal fade docs-cropped" id="getCroppedCanvasModal" aria-hidden="true"
                 aria-labelledby="getCroppedCanvasTitle" role="dialog" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="getCroppedCanvasTitle">效果</h4>
                        </div>
                        <div class="modal-body"></div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <a class="btn btn-primary" id="download" href="javascript:void(0);" download="cropped.jpg">下载</a>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.modal -->

            <button type="button" class="btn btn-primary" data-method="moveTo" data-option="0">
                <span class="docs-tooltip" data-toggle="tooltip" title="移到左上角">移到左上角</span>
            </button>
            <button type="button" class="btn btn-primary" data-method="zoomTo" data-option="1">
                <span class="docs-tooltip" data-toggle="tooltip" title="平铺100%">平铺100%</span>
            </button>

        </div>
        <!-- /.docs-buttons -->

        <div class="col-md-3 docs-toggles">
            <!-- <h3 class="page-header">Toggles:</h3> -->
            <div class="btn-group btn-group-justified" data-toggle="buttons">
                <label class="btn btn-primary active">
                    <input type="radio" class="sr-only" id="aspectRatio0" name="aspectRatio" value="1.7777777777777777">
                    <span class="docs-tooltip" data-toggle="tooltip" title="裁剪框比例: 16 / 9">16:9</span>
                </label>
                <label class="btn btn-primary">
                    <input type="radio" class="sr-only" id="aspectRatio1" name="aspectRatio" value="1.3333333333333333">
                    <span class="docs-tooltip" data-toggle="tooltip" title="裁剪框比例: 4 / 3">4:3</span>
                </label>
                <label class="btn btn-primary">
                    <input type="radio" class="sr-only" id="aspectRatio2" name="aspectRatio" value="1">
                    <span class="docs-tooltip" data-toggle="tooltip" title="裁剪框比例: 1 / 1">1:1</span>
                </label>
                <label class="btn btn-primary">
                    <input type="radio" class="sr-only" id="aspectRatio3" name="aspectRatio" value="0.6666666666666666">
                    <span class="docs-tooltip" data-toggle="tooltip" title="裁剪框比例: 2 / 3">2:3</span>
                </label>
                <label class="btn btn-primary">
                    <input type="radio" class="sr-only" id="aspectRatio4" name="aspectRatio" value="NaN">
                    <span class="docs-tooltip" data-toggle="tooltip" title="裁剪框比例: 自定义">Free</span>
                </label>
            </div>

            <div class="dropdown dropup docs-options">
                <button type="button" class="btn btn-primary btn-block dropdown-toggle" id="toggleOptions"
                        data-toggle="dropdown" aria-expanded="true">高级选项<span class="caret"></span>
                </button>
                <ul class="dropdown-menu" aria-labelledby="toggleOptions" role="menu">
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="responsive" checked>
                            responsive
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="restore" checked>
                            restore
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="checkCrossOrigin" checked>
                            checkCrossOrigin
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="checkOrientation" checked>
                            checkOrientation
                        </label>
                    </li>

                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="modal" checked>
                            modal
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="guides" checked>
                            guides
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="center" checked>
                            center
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="highlight" checked>
                            highlight
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="background" checked>
                            background
                        </label>
                    </li>

                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="autoCrop" checked>
                            autoCrop
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="movable" checked>
                            movable
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="rotatable" checked>
                            rotatable
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="scalable" checked>
                            scalable
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zoomable" checked>
                            zoomable
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zoomOnTouch" checked>
                            zoomOnTouch
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zoomOnWheel" checked>
                            zoomOnWheel
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="cropBoxMovable" checked>
                            cropBoxMovable
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="cropBoxResizable" checked>
                            cropBoxResizable
                        </label>
                    </li>
                    <li role="presentation">
                        <label class="checkbox-inline">
                            <input type="checkbox" name="toggleDragModeOnDblclick" checked>
                            toggleDragModeOnDblclick
                        </label>
                    </li>
                </ul>
            </div>
            <!-- /.dropdown -->
        </div>
        <!-- /.docs-toggles -->
    </div>
    <div class="row">
        <button class="btn btn-success btn-block btn-lg" data-bind="click: submitCrop">确定</button>
    </div>
</div>

<script>
    var imgUrl = "http://s24.tianyuimg.com//yddx/images/yddx_login_banner.jpg?version=2016082204"; // ${imgUrl!}";
    var callback = "${callback!}";
    var fullPath = "<@path path='' />";
</script>

<@script src="static/js/asserts/json2/v1.0.0/js/json2.js,
                  static/js/asserts/jquery/v2.1.4/js/jquery.js,
                  static/js/asserts/bootstrap/v3.3.5/js/bootstrap.js,

                  static/js/asserts/knockout/v3.4.0/js/knockout.js,
                  static/js/asserts/knockout-mapping/v2.4.1/js/knockout.mapping.js,


                  static/js/asserts/jquery-cover/v1.0.0/js/jquery.cover.js,
                  static/js/asserts/jquery-ajax/v1.0.0/js/jquery.ajax.js,
                  static/js/asserts/jquery-confirm/v2.5.1/js/jquery-confirm.js,


                  static/js/asserts/toastr/v2.1.2/js/toastr.js,
                  static/js/asserts/pace/v1.0.2/js/pace.js,
                  static/js/asserts/sweetalert/v1.0.1/js/sweetalert.js,
                  static/js/asserts/cropper/v2.3.3/js/cropper.js,

                  static/js/system/share/master.js,
                  static/js/components/image/cropper/index.js" />
</body>
</html>