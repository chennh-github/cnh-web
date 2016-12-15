<#import "/system/frame/main.ftl" as frame>
<#import "/system/share/base/form.ftl" as form>
<#import "/system/share/macro.ftl" as macro>

<@frame.header>
    <@form.css />
    <@link href="static/js/asserts/bootstrap-datepicker/v1.7.0/css/bootstrap-datepicker.css,
                 static/js/asserts/bootstrap-clockpicker/v0.0.7/css/bootstrap-clockpicker.css,
                 static/js/asserts/bootstrap-daterangepicker/v1.3.4/css/daterangepicker.css,
                 static/js/asserts/bootstrap-select/v1.7.3/css/bootstrap-select.css,
                 static/js/asserts/select2/v4.0.3/css/select2.css,
                 static/js/asserts/dropzone/v4.3.0/css/dropzone.css"/>
<script>
    var id = "${id!''}";
</script>
</@frame.header>
<@frame.block modular="表单页">

<form id="form" class="form-horizontal validate-picker">
    <div class="form-body">
        <div class="form-group">
            <label class="col-sm-3 to-right">Text</label>

            <div class="col-sm-6">
                <input type="text" name="text" class="form-control" required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">Password</label>

            <div class="col-sm-6">
                <input type="password" name="Password" class="form-control" required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">Dee Password</label>

            <div class="col-sm-6">
                <input type="password" name="deePassword" class="form-control password-picker" required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">性别</label>

            <div class="col-sm-6">
                <select name="sex" class="form-control" required>
                    <option value="">请选择</option>
                    <option value="1">男</option>
                    <option value="2">女</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">喜好</label>

            <div class="col-sm-6">
                <select name="interest" class="form-control select-picker" required>
                    <option value="">请选择</option>
                    <option value="1">篮球</option>
                    <option value="2">足球</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">Select2</label>

            <div class="col-sm-6">
                <select name="select2" class="form-control select2-picker" required>
                    <option value="">请选择</option>
                    <option value="1">篮球</option>
                    <option value="2">足球</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">Date</label>

            <div class="col-sm-6">
                <input type="text" name="date" class="form-control date-picker" required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">Clock</label>

            <div class="col-sm-6">
                <input type="text" name="clock" class="form-control clock-picker" required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">DateRange</label>

            <div class="col-sm-6">
                <input type="text" name="dateRange" class="form-control daterange-picker" required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">Knob</label>

            <div class="col-sm-6">
                <input type="text" name="knob" class="form-control knob-picker" value="36" data-knob="{min: 10, max: 100, step: 1}" required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">File</label>

            <div class="col-sm-6">
                <div class="dropzone dropzone-theme file-picker"
                     data-dropzone="{businessType: 'file', maxFiles: 1, koRefer: ''}"></div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">Image</label>

            <div class="col-sm-6">
                <div>
                    <div class="row" style="display: none;" data-bind="visible: model.entity.photo().length, foreach: {data: model.entity.photo, as: 'url'}">
                        <div class="photo" style="margin-right: 15px;">
                            <a class="thumbnail" href="javascript:void(0);">
                                <img data-bind="attr: {src: master.fmt.imgUrl(url)}">
                                <i class="remove fa fa-remove" data-bind="click: $root.removeUpload.bind($root, 'model.entity.photo', $index())"></i>
                            </a>
                        </div>
                    </div>
                </div>
                <a href="javascript: void(0);" class="btn btn-primary" data-bind="click: showUploadDialog.bind($root, 'model.entity.photo', {businessType: 'demo', maxCount: 5})">图片管理</a>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">Date: 9999-99-99</label>

            <div class="col-sm-6">
                <input type="text" name="dateMask" class="form-control mask-picker" data-mask="9999-99-99" required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">Spin</label>

            <div class="col-sm-6">
                <input type="text" name="spin" class="form-control spin-picker" value="1"
                       data-spin="{min: 0, max: 100, step: 1, postfix: '%'}" required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">Radio</label>

            <div class="col-sm-6">
                <div class="option-group">
                    <label class="option inline-block">
                        <input type="radio" name="radio" value="Default" checked>
                        <span class="radio radio-thin"></span> Default
                    </label>
                    <label class="option inline-block">
                        <input type="radio" name="radio" value="success">
                        <span class="radio radio-thin radio-success"></span> Success
                    </label>
                    <label class="option inline-block">
                        <input type="radio" name="radio" value="Info">
                        <span class="radio radio-thin radio-info"></span> Info
                    </label>
                    <label class="option inline-block">
                        <input type="radio" name="radio" value="Warning">
                        <span class="radio radio-thin radio-warning"></span> Warning
                    </label>
                    <label class="option inline-block">
                        <input type="radio" name="radio" value="Danger">
                        <span class="radio radio-thin radio-danger"></span> Danger
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 to-right">Checkbox</label>

            <div class="col-sm-6">
                <div class="option-group">
                    <label class="option inline-block">
                        <input type="checkbox" name="checkbox" value="Default" checked>
                        <span class="checkbox checkbox-thin"></span> Default
                    </label>
                    <label class="option inline-block">
                        <input type="checkbox" name="checkbox" value="Success">
                        <span class="checkbox checkbox-thin checkbox-success"></span> Success
                    </label>
                    <label class="option inline-block">
                        <input type="checkbox" name="checkbox" value="Info">
                        <span class="checkbox checkbox-thin checkbox-info"></span> Info
                    </label>
                    <label class="option inline-block">
                        <input type="checkbox" name="checkbox" value="Warning">
                        <span class="checkbox checkbox-thin checkbox-warning"></span> Warning
                    </label>
                    <label class="option inline-block">
                        <input type="checkbox" name="checkbox" value="Danger">
                        <span class="checkbox checkbox-thin checkbox-danger"></span> Danger
                    </label>
                </div>
            </div>
        </div>


        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-6">
                <button class="btn btn-theme" type="submit" data-bind="click: submitForm">Submit</button>
            </div>
        </div>
    </div>
</form>
<@macro.upload />

</@frame.block>
<@frame.footer>
    <@form.js />
    <@script src="static/js/asserts/bootstrap-datepicker/v1.7.0/js/bootstrap-datepicker.js,
                  static/js/asserts/bootstrap-datepicker/v1.7.0/locales/bootstrap-datepicker.zh-CN.min.js,
                  static/js/asserts/bootstrap-clockpicker/v0.0.7/js/bootstrap-clockpicker.js,
                  static/js/asserts/bootstrap-daterangepicker/v1.3.4/js/daterangepicker.js,
                  static/js/asserts/bootstrap-select/v1.7.3/js/bootstrap-select.js,
                  static/js/asserts/select2/v4.0.3/js/select2.full.js,
                  static/js/asserts/deepassword/v1.0.0/js/deepassword.js,
                  static/js/asserts/dropzone/v4.3.0/js/dropzone.js,
                  static/js/asserts/jquery-knob/v1.2.12/js/jquery-knob.js,
                  static/js/asserts/jquery-maskedinput/v1.4.1/js/jquery-maskedinput.js,
                  static/js/asserts/bootstrap-touchspin/v3.0.1/js/bootstrap-touchspin.js"/>

    <@script src="static/js/system/demo/form.js" />
</@frame.footer>