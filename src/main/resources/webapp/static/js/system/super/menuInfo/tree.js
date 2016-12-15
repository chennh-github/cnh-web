(function ($, Tree) {

    "use strict";

    $.extend(true, Tree, {

        init: function ($tree, options) {
            $tree.zTreeProxy($.extend(true, {
                zTree: {
                    data: {
                        simpleData: {
                            idKey: "id",
                            pIdKey: "parentId",
                            name: "title"
                        },
                        key: {
                            name: "title",
                            title: "title"
                        },
                        keep: {
                            leaf: false,
                            parent: false
                        }
                    },
                    edit: {
                        drag: {
                            isCopy: false,
                            isMove: true
                        },
                        enable: true,
                        showRemoveBtn: false,
                        showRenameBtn: false
                    }
                },
                reload: {
                    handleResponseLevel: function (event, treeId, treeNode, responseLevel, clickFn) {
                        clickFn.call(null, event, treeId, treeNode);
                    }
                }
            }, options));
            $tree.find('.tree').css({
                'max-height': $(window).height()
            });
        },
        build: function ($tree, data) {
            var zTreeProxy = $.fn.zTreeProxy.getZTreeProxy($tree),
                zTree = null;
            zTreeProxy.buildTree(data);
            zTree = $.fn.zTreeProxy.getZTreeObj($tree);
            zTree.expandAll(true);
        }
    });

}(jQuery, (window.Tree || (window.Tree = {}))));