;(function ($, viewModel) {

    "use strict";

    $.extend(true, viewModel, {

        model: {
            search: {
                pageNo: 1,
                pageSize: 10
            },
            form: {},
            detail: {},
            dataList: [],
            checkedArray: [],       // 列表选中记录
            hasInited: false
        },

        /**
         * 初始化viewModel
         */
        initViewModel: function () {
            this.model = ko.mapping.fromJS(this.model);
            this.initReady();
            this.initValidator();
            this.search();
            ko.applyBindings(this, $("#bootstrap")[0]);

            this.model.hasInited(true);
        },
        /**
         * 初始化时执行的函数
         */
        initReady: $.noop,
        /**
         * 初始化jquery.validator
         */
        initValidator: $.noop,
        /**
         * 执行list ajax返回数据后执行
         */
        afterList: $.noop,
        /**
         * 设置分页
         * @param pageNo
         * @param pageSize
         */
        setPage: function (pageNo, pageSize) {
            if (pageNo) { this.model.search.pageNo(pageNo); }
            if (pageSize) { this.model.search.pageSize(pageSize); }
        },
        list: function (pageNo, pageSize) {
            var that = this,
                data = null;
            this.setPage(pageNo, pageSize);
            data = ko.mapping.toJS(this.model.search);
            service.list(data).then(function (page) {
                // 调用处理函数
                that.afterList(page);
                that.matchCheckedArray(page.list);
                that.model.dataList(page.list);

                if (!page.list.length) {
                    // 删除等操作导致的最后一页唯一一条记录被删除后要跳转到前一页
                    if (data.pageNo > 1) {
                        that.list(data.pageNo - 1);
                        master.toast.info("当前页没有数据，自动加载上一页数据");
                    } else {
                        master.toast.info("暂无数据");
                    }
                }
                that.page($("#pagination"), page);
            });
        },
        /**
         * 匹配列表数据的选中状态
         * @param data
         */
        matchCheckedArray: function (data) {
            var checkedArray = this.model.checkedArray.peek();
            if (checkedArray.length) {
                $.each(data, function (i, v) {
                    var index = master.fmt.index(v, checkedArray, "id");
                    if (index > -1) {
                        data[i] = checkedArray[index];
                    }
                });
            }
        },
        /**
         * 查询首页数据列表
         */
        search: function () {
            this.setPage(1);
            this.list();
        },
        /**
         * 生成分页脚本
         * @param $pagination
         * @param page
         */
        page: function ($pagination, page) {
            var that = this;
            if (page) {
                if (!page.pageNo) {
                    page.pageNo = page.pageNum;
                }
                if (!page.pageCount) {
                    page.pageCount = page.pages;
                }
                if (!page.totalCount) {
                    page.totalCount = page.total;
                }
            }
            page = $.extend(true, (page || {
                pageNo: 1,
                pageSize: this.model.search.pageSize.peek(),
                pageCount: 1,
                totalCount: 0
            }), {
                showMiniDesc: true,
                showPages: 7,
                callback: {
                    click: function (pageObj, pageNo, pageSize) {
                        pageObj.refreshPage();
                        that.list(pageNo, pageSize);
                    }
                }
            });
            this.setPage(page.pageNo, page.pageSize);
            // 此处调用分页插件
            $pagination.page(page);
            // 如果只有一页或没有直接隐藏分页
            //if (page.pageCount <= 1) {
            //    $pagination.hide();
            //} else {
            //    $pagination.show();
            //}
        },
        /**
         * 当前页全选/全不选
         * @param data
         * @param e
         * @returns {boolean}
         */
        checkAll: function (data, e) {
            var isChecked = $(e.target).is(":checked"),
                checkedArray = this.model.checkedArray;

            $.each(this.model.dataList.peek(), function (i, v) {
                var isExists = checkedArray.indexOf(v) > -1;
                if (isChecked && !isExists) {
                    checkedArray.push(v);
                } else if (!isChecked && isExists) {
                    checkedArray.remove(v);
                }
            });
            return true;
        },
        /**
         * 单选
         * @param item
         * @param e
         * @returns {boolean}
         */
        checkOne: function (item, e) {
            var isChecked = $(e.target).is(":checked");
            if (isChecked) {
                this.model.checkedArray.push(item);
            } else {
                this.model.checkedArray.remove(item);
            }
            return true;
        },
        /**
         * 获取多选框选中的ID数组
         * @returns {Array}
         */
        getCheckedArrayIds:  function () {
            var ids = [];
            $.each(this.model.checkedArray.peek(), function (index, item){
                ids.push(item.id);
            });
            return ids;
        },
        /**
         * 更新某些字段的值
         * @param data  必须包含ID
         * @param label
         */
        updateData: function (data, label) {
            var that = this;
            if (data == null || data.id == null) {
                return master.sweet.warning("id或data不能为空!");
            }
            service.update(data).then(function (){
                master.sweet.success("更新成功");
            });
        },
        /**
         * 删除记录
         * @param id
         */
        deleteItem: function (id, isSilent) {
            var that = this;
            if (typeof isSilent !== "boolean") {
                isSilent = false;
            }
            function doDelete() {
                service.del(id).then(function () {
                    master.toast.success("删除成功");
                    that.list();
                });
            }
            if (isSilent) {
                doDelete();
            } else {
                $.confirm({
                    title: "确定删除该记录？",
                    content: "该操作不可恢复，点击确定继续",
                    confirm: function () {
                        doDelete();
                    }
                });
            }
        },
        /**
         * 批量删除记录
         */
        deleteBatch: function () {
            var ids = this.getCheckedArrayIds(),
                that = this;
            if (!ids.length) {
                return master.sweet.warning("请选择要删除的记录");
            }
            master.sweet.confirm({
                title: "确定删除选中的记录？"
            }, function () {
                service.del(ids.join(",")).then(function () {
                    master.sweet.success("删除成功");
                    that.list();
                });
            });
        },
        /**
         * 设置导入时需要上传的数据
         * @param moduleName
         * @returns {{}}
         */
        setImportData: function (moduleName) {
            return {};
        },
        /**
         * 导入Excel
         * @param fileInput
         * @param data 上传的附加数据，必须包含xlsName，指定处理服务
         * @returns {*}
         */
        importXls: function (fileInput, data) {
            var that = this;
            if (!master.isExcel(fileInput) || !master.isFileSizeValid(fileInput, 2*1024*1024)) {
                return master.toast.warning("请选择.xls后缀名的Excel文件");
            }
            $("body").cover();
            $.ajaxFileUpload({
                url : fullPath + "system/xls/importXls",
                type: "POST",
                secureuri:false,
                fileElementId : fileInput.id,
                dataType : 'json',
                data: $.extend(true, data, this.setImportData(window["modularName"])),
                success: function (xlsDownloadUrl, status){
                    master.sweet.success("导入成功", "请从下载的Excel中查证记录是否导入");
                    if (xlsDownloadUrl) {
                        location.href = xlsDownloadUrl;
                    }
                    that.list();
                    $("body").uncover();
                },
                error: function (data, status, e){
                    master.toast.error("导入失败");
                    $("body").uncover();
                }
            });
        },
        /**
         * 导出Excel
         * @param xlsName 指定处理服务的名称
         * @param size
         */
        exportXls: function (xlsName, size) {
            var data = ko.mapping.toJS(this.model.search);
            if (!size) {
                data.pageNo = 1;
                data.pageSize = Number.MAX_VALUE;
            } else {
                data.pageSize = size;
            }
            data.xlsName = xlsName;
            service.exportXls(data).then(function (xlsDownloadUrl) {
                master.sweet.success("导出成功");
                if (xlsDownloadUrl) {
                    location.href = xlsDownloadUrl;
                }
            });
        },
        /**
         * 显示form弹出框
         * @param id
         */
        showFormDialog: function (id) {
            var url = 'form2' + (id ? '/' + id : '');
            $('#formIframe').attr('src', url);
            master.utils.showDialog($('#formDialog'));
        },
        /**
         * 隐藏form弹出框
         */
        hideFormDialog: function () {
            master.utils.hideDialog($('#formDialog'), function () {
                $('#formIframe').removeAttr('src');
            });
        },
        /**
         * 提交form
         */
        submitFormDialog: function () {
            var formIframe = document.getElementById('formIframe'),
                that = this;
            formIframe.contentWindow.submitForm().then(function () {
                that.list();
                that.hideFormDialog();
            });
        },
        /**
         * 显示详情dialog
         * @param id
         */
        showDetailDialog: function (id) {
            $('#detailIframe').removeAttr('src').attr('src', 'detail/' + id);
            master.utils.showDialog($('#detailDialog'));
        }
    });

    $(function () {
        ready.init(['selectPicker', 'select2Picker', 'datetimePicker', 'validatePicker']);
        viewModel.initViewModel();
    });

    // 开启ko延迟更新
    ko.options.defer = true;

}(jQuery, (window.viewModel || (window.viewModel = {}))));