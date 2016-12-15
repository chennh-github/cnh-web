;
(function ($, viewModel) {
    "use strict";

    $.extend(true, viewModel, {
        model: {
            search: {
                parentId: 0,
                menuType: 1
            },
            tree: {
                menuType: 1
            }

        },
        initViewModel: function () {
            this.model = ko.mapping.fromJS(this.model);
            this.initReady();
            this.initValidator();
            ko.applyBindings(this, $("#bootstrap")[0]);
            this.model.hasInited(true);
        },
        initReady: function () {
            var that = this;

            this.model.tree.menuType.subscribe(function (value) {
                that.model.search.menuType(value);
                that.refreshTree();
            });

            Tree.init($('#tree'), {
                zTree: {
                    callback: {
                        beforeDrop: function (treeId, treeNodes, targetNode, moveType) {
                            if (targetNode == null || targetNode.level === 0) {
                                master.toast.warning('不允许设置为根节点');
                                return false;
                            } else if (targetNode.level > 2 || (targetNode.level === 2 && moveType === 'inner')) {
                                master.toast.warning('菜单不允许超过两级');
                                return false;
                            }
                            return true;
                        },
                        onDrop: function (event, treeId, treeNodes, targetNode, moveType) {
                            service.drop({
                                id: treeNodes[0].id,
                                targetId: targetNode.id,
                                moveType: moveType
                            }).then(function () {
                                that.refreshTree();
                            });
                        }
                    }
                },
                callback: {
                    click: function (event, treeId, treeNode) {
                        if (treeNode.isParent) {
                            that.model.search.parentId(treeNode.id);
                            that.search();
                        }
                    },
                    afterTreeBuild: function () {
                        that.search();
                    }
                }
            });
            this.refreshTree();
        },
        refreshTree: function () {
            var search = ko.mapping.toJS(this.model.tree);
            service.list(search).then(function (data) {
                var ret = [];
                $.each(data, function (i, v) {
                    ret.push({
                        id: v.id,
                        parentId: v.parentId || 0,
                        title: v.title
                    });
                });
                ret.push({
                    id: 0,
                    title: '全部'
                });
                Tree.build($('#tree'), ret);
            });
        },
        switchMenuType: function (menuType) {
            this.model.tree.menuType(menuType);
        },
        search: function () {
            var that = this;
            this.setPage(1);
            setTimeout(function () {
                that.list();
            }, 0);
        },
        /**
         * 删除单个记录后回调函数
         */
        afterDeleteItem: function () {
            master.toast.success("删除成功");
            this.refreshTree();
        },
        /**
         * 批量删除回调函数
         */
        afterDeleteBatch: function () {
            master.sweet.success("删除成功");
            this.refreshTree();
        },
        /**
         * 上移成功后回调函数
         */
        afterMoveUp: function () {
            master.sweet.success("上移成功");
            this.refreshTree();
        },
        /**
         * 下移成功后回调函数
         */
        afterMoveDown: function () {
            master.sweet.success("下移成功");
            this.refreshTree();
        },
        /**
         * 置顶成功后回调函数
         */
        afterZTop: function () {
            master.sweet.success("置顶成功");
            this.refreshTree();
        },
        /**
         * 设置弹窗form的传递参数
         * @returns {{}}
         */
        setFormDialogData: function (id, url) {
            return {
                parentId: this.model.search.parentId(),
                menuType: this.model.search.menuType()
            };
        },
        /**
         * 更新菜单依赖
         */
        updateDependency: function () {
            service.updateDependency().then(function () {
                master.sweet.success('更新依赖成功!');
            });
        },
        /**
         * 删除记录
         * @param item
         */
        deleteItem: function (item) {
            var that = this;
            $.confirm({
                title: '确定删除该记录？',
                content: item.parentId ? '' : '将级联删除该节点下的子节点',
                confirm: function () {
                    service.del(item.id).then(function () {
                        that.afterDeleteItem();
                    });
                }
            });
        }
    });


    $(function () {
        viewModel.initViewModel();
    });

}(jQuery, (window.viewModel || (window.viewModel = {}))));