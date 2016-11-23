<#import "/system/frame/main.ftl" as frame>
<#import "/system/share/base/index.ftl" as list>
<#import "/system/share/macro.ftl" as macro>

<@frame.header>
    <@list.css />
    <@link href="static/js/asserts/bootstrap-datepicker/v1.7.0/css/bootstrap-datepicker.css,
                 static/js/asserts/bootstrap-clockpicker/v0.0.7/css/bootstrap-clockpicker.css,
                 static/js/asserts/bootstrap-daterangepicker/v1.3.4/css/daterangepicker.css,
                 static/js/asserts/bootstrap-select/v1.7.3/css/bootstrap-select.css"/>
<script>
    var modularName = "demo";
</script>
</@frame.header>
<@frame.body modular="列表页">
<#--http://view.jqueryfuns.com/%E9%A2%84%E8%A7%88-/2015/10/12/d331b11f497aec6aaef8bff4da314f94/dashboard.html-->
<div class="panel">
    <div class="panel-heading">
        <div class="panel-control">
            <button class="btn btn-s btn-bordered btn-default" type="button" data-toggle="collapse">
                <i class="fa fa-minus"></i>
            </button>
        </div>
    </div>
    <div class="panel-body">
        <div class="well">
            <form class="form-inline">
                <div class="form-body">
                    <div class="form-group">
                        <label>Account
                            <input class="form-control" data-bind="textinput: model.search.account">
                        </label>
                    </div>
                    <div class="form-group">
                        <label>Name
                            <input class="form-control" data-bind="textinput: model.search.name">
                        </label>
                    </div>
                    <div class="form-group">
                        <label>Sex
                            <select class="form-control">
                                <option>全部</option>
                                <option>男</option>
                                <option>女</option>
                            </select>
                        </label>
                    </div>
                    <div class="form-group">
                        <label>Sport
                            <select class="form-control select-picker">
                                <option>全部</option>
                                <option>篮球</option>
                                <option>足球</option>
                            </select>
                        </label>
                    </div>
                    <div class="form-group">
                        <label>Date
                                <span class="iconed-input inline-block">
                                    <i class="fa fa-calendar"></i>
                                    <input type="text" class="form-control date-picker">
                                </span>
                        </label>
                    </div>
                    <div class="form-group">
                        <label>Time
                                <span class="iconed-input inline-block clock-picker">
                                    <i class="fa fa-clock-o"></i>
                                    <input type="text" class="form-control clock-picker">
                                </span>
                        </label>
                    </div>
                    <div class="form-group">
                        <label>Date Range
                            <input type="text" class="form-control w200 daterange-picker">
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <button class="btn btn-theme">Search</button>
                </div>
            </form>
        </div>

        <div class="row mar-v-15">
            <div class="pull-left">
                <div class="dropdown btn-group">
                    <button data-toggle="dropdown" class="btn btn-iconed btn-primary btn-rounded dropdown-toggle"
                            aria-expanded="true">
                        <i class="glyphicon glyphicon-export"></i> Export <i
                            class="dropdown-caret fa fa-caret-down"></i>
                    </button>
                    <ul class="dropdown-menu dropdown-right">
                        <li class="dropdown-header">选择导出条数</li>
                        <li><a href="javascript: void(0);">10 Records</a></li>
                        <li><a href="javascript: void(0);">20 Records</a></li>
                        <li><a href="javascript: void(0);">50 Records</a></li>
                        <li><a href="javascript: void(0);">All Records</a></li>
                    </ul>
                </div>
                <button class="btn btn-iconed btn-info">
                    <i class="glyphicon glyphicon-import"></i> Import
                </button>
            </div>
            <div class="pull-right">
                <a href="javascript: void(0);" class="btn btn-danger" style="display: none;"
                   data-bind="visible: model.checkedArray().length, click: deleteBatch">
                    Batch Delete
                    <span data-bind="text: '(' + model.checkedArray().length + ')'"></span>
                </a>
                <a href="javascript: void(0);" class="btn btn-iconed btn-theme" data-toggle="dialog"
                   data-target="#demoDialog"><i class="fa fa-plus"></i> Dialog
                </a>
                <a href="javascript: void(0);" class="btn btn-primary" data-toggle="modal"
                   data-target="#demoModal">Modal
                </a>
                <a href="form" class="btn btn-primary">Form Page</a>
                <a href="javascript: void(0);" class="btn btn-primary"
                   data-bind="click: showFormDialog.bind($root, null)">Form Dialog</a>
            </div>
        </div>

        <table class="table table-theme-hover table-striped table-responsive table-bordered">
            <thead>
            <tr>
                <th style="width: 45px;">
                    <label class="option">
                        <input type="checkbox" name="checkbox" data-bind="click: checkAll">
                        <span class="checkbox checkbox-thin"></span>
                    </label>
                </th>
                <th style="width: 30%;">Name</th>
                <th style="width: 20%;">Surname</th>
                <th style="width: 40%;">Email</th>
                <th style="width: 10%;">Operate</th>
            </tr>
            </thead>
            <tbody style="display: none;"
                   data-bind="visible: model.dataList().length, foreach: {data: model.dataList, as: 'item'}">
            <tr>
                <td>
                    <label class="option">
                        <input type="checkbox" name="checkbox"
                               data-bind="checked: $root.model.checkedArray, checkedValue: item">
                        <span class="checkbox checkbox-thin"></span>
                    </label>
                </td>
                <td>Otto</td>
                <td><label class="label label-success">Success</label></td>
                <td>otto.doggy@loremipsumiun.com</td>
                <td>
                    <div class="dropdown btn-group">
                        <button type="button" data-toggle="dropdown"
                                class="btn btn-icon btn-theme dropdown-toggle"
                                aria-expanded="false">
                            <i class="fa fa-cog fa-spin"></i>
                            <i class="dropdown-caret fa fa-caret-down"></i>
                        </button>
                        <ul class="dropdown-menu list-iconed">
                            <li><a href="javascript: void(0);" data-bind="attr: {href: 'form/' + item.id}">
                                <i class="fa fa-edit"></i> Edit Page</a></li>
                            <li><a href="javascript: void(0);"
                                   data-bind="click: $root.showFormDialog.bind($root, item.id)">
                                <i class="fa fa-edit"></i> Edit Dialog</a></li>
                            <li><a href="javascript: void(0);"
                                   data-bind="click: $root.deleteItem.bind($root, item.id)">
                                <i class="fa fa-trash"></i> Delete</a></li>
                            <li><a href="javascript: void(0);"><i class="fa fa-info"></i> Info</a></li>
                            <li class="divider"></li>
                            <li><a href="javascript: void(0);"><i class="fa fa-arrow-up"></i> Up</a></li>
                            <li><a href="javascript: void(0);"><i class="fa fa-arrow-down"></i> Down</a></li>
                        </ul>
                    </div>
                </td>
            </tr>
            </tbody>
            <tbody style="display: none;" data-bind="visible: !model.dataList().length">
            <tr>
                <td colspan="5">
                    <p class="nodata" style="display: none;" data-bind="visible: !model.dataList().length">暂无数据</p>
                </td>
            </tr>
            </tbody>
        </table>
        <div id="pagination"></div>
    </div>
</div>

<#-- demo Modal-->
    <@macro.modal id="demoModal" title="Modal Title">
    <div class="modal-body">
        What are you want to do?
    </div>
    <div class="modal-footer to-right">
        <button class="btn btn-theme">确定</button>
    </div>
    </@macro.modal>

<#--demo Dialog-->
    <@macro.dialog id="demoDialog" width="500px">
    <div class="panel-body panel-loading">
        <div class="panel-refreshing">
            <div class="spinner">
                <div class="se rotating-plane"></div>
            </div>
        </div>
            <span><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec et tempor risus, sed tempor velit.
                Phasellus ut maximus nulla. Curabitur sit amet maximus nulla. Class.</p></span>
    </div>
    <div class="panel-footer">
        CSS Classes: <span class="code">.spinner .rotating-plane</span>
    </div>
    </@macro.dialog>

<#--form Dialog-->
    <@macro.formDialog />

<#--detail Dialog-->
    <@macro.detailDialog />


</@frame.body>
<@frame.footer>
    <@list.js />
    <@script src="static/js/asserts/bootstrap-datepicker/v1.7.0/js/bootstrap-datepicker.js,
                  static/js/asserts/bootstrap-datepicker/v1.7.0/locales/bootstrap-datepicker.zh-CN.min.js,
                  static/js/asserts/bootstrap-clockpicker/v0.0.7/js/bootstrap-clockpicker.js,
                  static/js/asserts/bootstrap-daterangepicker/v1.3.4/js/daterangepicker.js,
                  static/js/asserts/bootstrap-select/v1.7.3/js/bootstrap-select.js"/>

    <@script src="static/js/system/demo/list.js" />
</@frame.footer>