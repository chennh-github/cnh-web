<#import "/system/frame/main.ftl" as layout>
<#import "/system/share/base/index.ftl" as index>
<#import "/system/share/macro.ftl" as macro>

<@layout.header>
	<@index.css />
    <style>
        #tree {
            float: left;
            margin: 0 15px 0 0;
            overflow: hidden;
        }
        .main {
            overflow: hidden;
        }
    </style>
    <script>
        var modularName = "menuInfo";
    </script>
</@layout.header>
<@layout.page modular="菜单管理">
<div>
    <div class="panel panel-info">
        <div class="panel-heading">
            <div class="tab tab-theme pull-left">
                <ul class="nav nav-tabs">
                    <li data-bind="css: {active: model.tree.menuType() === 1}, click: switchMenuType.bind($root, 1)">
                        <a href="javascript: void(0);">超级管理</a>
                    </li>
                    <li data-bind="css: {active: model.tree.menuType() === 2}, click: switchMenuType.bind($root, 2)">
                        <a href="javascript: void(0);">二级管理</a>
                    </li>
                    <li data-bind="css: {active: model.tree.menuType() === 3}, click: switchMenuType.bind($root, 3)">
                        <a href="javascript: void(0);">三级管理</a>
                    </li>
                </ul>
            </div>
            <div class="panel-control">
                <button class="btn btn-s btn-bordered btn-default" type="button" data-toggle="collapse">
                    <i class="fa fa-minus"></i>
                </button>
            </div>
        </div>
        <div class="panel-body">
            <div id="tree"></div>
            <div class="main" style="padding-bottom: 140px;">
                <div class="well">
                    <form class="form-inline">
                        <div class="form-body">
                            <div class="form-group">
                                <label>标题
                                    <input type="text" class="form-control" data-bind="textinput: model.search.title">
                                </label>
                            </div>
                            <div class="form-group">
                                <label>编码
                                    <input type="text" class="form-control" data-bind="textinput: model.search.code">
                                </label>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-theme" data-bind="click: search">查询</button>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="row mar-v-15">
                    <div class="pull-left">
                        <a href="javascript: void(0);" class="btn btn-primary"
                           data-bind="click: updateDependency">更新依赖</a>
                    </div>
                    <div class="pull-right">
                        <a href="javascript: void(0);" class="btn btn-primary"
                           data-bind="click: showFormDialog.bind($root, null)">新增</a>
                    </div>
                </div>

                <table class="table table-theme-hover table-striped table-responsive table-bordered" data-bind="is: model.bool()">
                    <thead>
                    <tr>
                        <th style="width: 70px;">ID</th>
                        <th style="width: 20%;">标题</th>
                        <th style="width: 30%;">URL</th>
                        <th style="width: 5%;">图标</th>
                        <th style="width: 10%;">编码</th>
                        <th style="width: 15%;">打开方式</th>
                        <th style="width: 10%;">状态</th>
                        <th style="width: 10%;">序号</th>
                        <th style="width: 100px;">操作</th>
                    </tr>
                    </thead>
                    <tbody style="display: none;" data-bind="visible: model.dataList().length, foreach: {data: model.dataList, as: 'item'}">
                    <tr>
                        <td data-bind="text: item.id"></td>
                        <td data-bind="text: item.title"></td>
                        <td>
                            <a href="javascript: void(0);" class="link" data-bind="attr: {href: item.url}, text: item.url" target="_blank"></a>
                        </td>
                        <td>
                            <i data-bind="css: item.menuIcon" style="font-size: 24px;"></i>
                        </td>
                        <td data-bind="text: item.code"></td>
                        <td data-bind="text: master.fmt.value(item.target, {'_self': '当前页', '_blank': '新页面'})"></td>
                        <td>
                            <!-- ko if: item.status === 0 -->
                            <label class="label label-warning">禁用</label>
                            <!-- /ko -->
                            <!-- ko if: item.status === 1 -->
                            <label class="label label-success">启用</label>
                            <!-- /ko -->
                        </td>
                        <td data-bind="text: item.orderNo"></td>
                        <td>
                            <div class="dropdown btn-group">
                                <button type="button" data-toggle="dropdown"
                                        class="btn btn-icon btn-theme dropdown-toggle"
                                        aria-expanded="false">
                                    <i class="fa fa-cog fa-spin"></i>
                                    <i class="dropdown-caret fa fa-caret-down"></i>
                                </button>
                                <ul class="dropdown-menu list-iconed">
                                    <li data-bind="click: $root.showFormDialog.bind($root, item.id)">
                                        <a href="javascript: void(0);"><i class="fa fa-edit"></i> 编辑</a>
                                    </li>
                                    <!-- ko if: item.status === 0 -->
                                    <li data-bind="click: $root.updateData.bind($root, {id: item.id, status: 1}, '启用')">
                                        <a href="javascript: void(0);"><i class="fa fa-circle"></i> 启用</a>
                                    </li>
                                    <!-- /ko -->
                                    <!-- ko if: item.status === 1 -->
                                    <li data-bind="click: $root.updateData.bind($root, {id: item.id, status: 0}, '禁用')">
                                        <a href="javascript: void(0);"><i class="fa fa-circle-o"></i> 禁用</a>
                                    </li>
                                    <!-- /ko -->
                                    <li data-bind="click: $root.moveUp.bind($root, {id: item.id, orderType: item.menuType})">
                                        <a href="javascript: void(0);"><i class="fa fa-arrow-up"></i> 上移</a>
                                    </li>
                                    <li data-bind="click: $root.moveDown.bind($root, {id: item.id, orderType: item.menuType})">
                                        <a href="javascript: void(0);"><i class="fa fa-arrow-down"></i> 下移</a>
                                    </li>
                                    <li data-bind="click: $root.zTop.bind($root, {id: item.id, orderType: item.menuType})">
                                        <a href="javascript: void(0);"><i class="fa fa-rocket"></i> 置顶</a>
                                    </li>
                                    <li><a href="javascript: void(0);" data-bind="click: $root.deleteItem.bind($root, item)"><i class="fa fa-trash"></i> 删除</a></li>
                                </ul>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                    <tbody style="display: none;" data-bind="visible: !model.dataList().length">
                    <tr>
                        <td colspan="12" class="nodata">暂无数据</td>
                    </tr>
                    </tbody>
                </table>
                <div id="pagination"></div>
            </div>
        </div>
    </div>
</div>

<#--form Dialog-->
    <@macro.formDialog />

</@layout.page>
<@layout.footer>
	<@index.js />
	<@script src="static/js/asserts/jquery-zTree/v3.5/js/jquery.zTreeProxy.js,
	              static/js/system/super/menuInfo/tree.js,
	              static/js/system/super/menuInfo/service.js,
				  static/js/system/super/menuInfo/index.js" />
</@layout.footer>