<#import "/system/frame/main.ftl" as layout>
<#import "/system/share/base/form.ftl" as form>
<#import "/system/share/macro.ftl" as macro>

<@layout.header>
	<@form.css />
    <@link href="static/js/asserts/bootstrap-select/v1.7.3/css/bootstrap-select.css,
                 static/js/asserts/select2/v4.0.3/css/select2.css"/>
    <script>
        var modularName = "menuInfo";
        var id = "${id!}";
        var parentId = "${parentId!'0'}";
        var menuType = "${menuType!'1'}";
    </script>
</@layout.header>
<@layout.block modular="${id???string('编辑', '新增')}">

<div class="panel">
    <div class="panel-body">
        <form id="form" class="form-horizontal validate-picker">
            <div class="form-body">
                <div class="form-group">
                    <label for="title" class="col-sm-3 to-right">标题<span class="required">*</span></label>
                    <div class="col-sm-6">
                        <input type="text" id="title" name="title" class="form-control"
                               required maxlength="32"
                               data-bind="textinput: model.entity.title">
                    </div>
                </div>
                <div class="form-group">
                    <label for="url" class="col-sm-3 to-right">地址<span class="required">*</span></label>
                    <div class="col-sm-6">
                        <input type="text" id="url" name="url" class="form-control"
                               required maxlength="256"
                               data-bind="textinput: model.entity.url">
                    </div>
                </div>
                <div class="form-group">
                    <label for="describtion" class="col-sm-3 to-right">描述<span class="required">*</span></label>
                    <div class="col-sm-6">
                        <textarea id="describtion" name="describtion" class="form-control"
                                  required maxlength="128"
                                  data-bind="textinput: model.entity.describtion"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="menuIcon" class="col-sm-3 to-right">图标<span class="required">*</span></label>
                    <div class="col-sm-6">
                        <div class="input-group">
                            <input type="text" id="menuIcon" name="menuIcon" class="form-control"
                                   required maxlength="32"
                                   data-bind="textinput: model.entity.menuIcon">
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button" data-bind="click: showIconDialog">选择图标</button>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="code" class="col-sm-3 to-right">编码<span class="required">*</span></label>
                    <div class="col-sm-6">
                        <input type="text" id="code" name="code" class="form-control"
                               required maxlength="32"
                               data-bind="textinput: model.entity.code">
                    </div>
                </div>
                <div class="form-group">
                    <label for="orderNo" class="col-sm-3 to-right">序号</label>
                    <div class="col-sm-6">
                        <input type="text" id="orderNo" name="orderNo" class="form-control"
                               required maxlength="8"
                               data-bind="textinput: model.entity.orderNo">
                        <p class="help-block">若不填写，系统将自动生成一个最大的序号</p>
                    </div>
                </div>
                <div class="form-group">
                    <label for="target" class="col-sm-3 to-right">打开方式<span class="required">*</span></label>
                    <div class="col-sm-6">
                        <select id="target" name="target" class="form-control select-picker"
                                required
                                data-bind="value: model.entity.target">
                            <option value="">请选择</option>
                            <option value="_self">当前页</option>
                            <option value="_blank">新页面</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 to-right">状态<span class="required">*</span></label>

                    <div class="col-sm-6">
                        <div class="option-group">
                            <label class="option inline-block">
                                <input type="radio" name="status" data-bind="checked: model.entity.status, checkedValue: 1">
                                <span class="radio radio-thin radio-success"></span> 启用
                            </label>
                            <label class="option inline-block">
                                <input type="radio" name="status" data-bind="checked: model.entity.status, checkedValue: 0">
                                <span class="radio radio-thin radio-warning"></span> 禁用
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<#--icon Dialog-->
    <@macro.dialog id="iconDialog" width="1048px">
    <div class="panel-body" style="height: 400px; overflow: auto;">
        <div class="icon-list">
            <ul data-bind="foreach: {data: model.iconList, as: 'item'}">
                <li>
                    <a href="javascript: void(0);" data-bind="attr: {'data-icon': item}">
                        <i data-bind="css: item + ' fa-lg'"></i>
                        <span data-bind="text: item"></span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    </@macro.dialog>

</@layout.block>
<@layout.footer>
	<@form.js />
	<@script src="static/js/asserts/bootstrap-select/v1.7.3/js/bootstrap-select.js,
	              static/js/asserts/select2/v4.0.3/js/select2.full.js,
	              static/js/system/frame/icon.js,
	              static/js/system/super/menuInfo/service.js,
				  static/js/system/super/menuInfo/form.js" />
</@layout.footer>