;
(function ($, viewModel) {

    'use strict';

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
        messages: {
            'update': {
                success: '更新成功'
            },
            'delete': {
                success: '删除成功',
                batch: {
                    title: '确定删除选中的记录？',
                    content: '该操作不可恢复，点击确定继续',
                    noData: '请选择要删除的记录'
                },
                item: {
                    title: '确定删除该记录？',
                    content: '该操作不可恢复，点击确定继续'
                }
            },
            'export': {
                success: '导出成功'
            },
            'moveUp': {
                success: '上移成功'
            },
            'moveDown': {
                success: '下移成功'
            },
            'zTop': {
                success: '置顶成功'
            },
            'list': {
                loadPrevPage: '当前页没有数据，自动加载上一页数据',
                noData: '暂无数据'
            },
            'form': {
                success: '操作成功'
            }
        },

        /**
         * 初始化viewModel
         */
        initViewModel: function () {
            this.model = ko.mapping.fromJS(this.model);
            this.initReady();
            this.initValidator();
            this.search();
            ko.applyBindings(this, $('#bootstrap')[0]);

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
         * 更新数据成功后的回调函数
         */
        afterUpdateData: function () {
            master.sweet.success(this.messages.update.success);
            this.list();
        },
        /**
         * 设置导入时需要上传的数据
         * @param data
         * @returns {{}}
         */
        setImportData: function (data) {
            return {};
        },
        /**
         * 删除单个记录后回调函数
         */
        afterDeleteItem: function () {
            master.toast.success(this.messages.delete.success);
            this.list();
        },
        /**
         * 批量删除回调函数
         */
        afterDeleteBatch: function () {
            master.sweet.success(this.messages.delete.success);
            this.list();
        },
        /**
         * 导入Excel成功后回调函数
         * @param result
         * @param data
         * @param fileInput
         */
        afterImportXls: function (result, data, fileInput) {
            if (result) {
                $('#importDialog').fadeOut(300);
                this.list();
                location.href = data;
            }
        },
        /**
         * 导出Excel成功后回调函数
         * @param xlsDownloadUrl
         */
        afterExportXls: function (xlsDownloadUrl) {
            master.sweet.success(this.messages.export.success);
            if (xlsDownloadUrl) {
                location.href = xlsDownloadUrl;
            }
        },
        /**
         * 设置弹窗form的传递参数
         * @returns {{}}
         */
        setFormDialogData: function (id, url) {
            return {};
        },
        /**
         * 上移成功后回调函数
         */
        afterMoveUp: function () {
            master.sweet.success(this.messages.moveUp.success);
            this.list();
        },
        /**
         * 下移成功后回调函数
         */
        afterMoveDown: function () {
            master.sweet.success(this.messages.moveDown.success);
            this.list();
        },
        /**
         * 置顶成功后回调函数
         */
        afterZTop: function () {
            master.sweet.success(this.messages.zTop.success);
            this.list();
        },
        /**
         * 设置分页
         * @param pageNo
         * @param pageSize
         */
        setPage: function (pageNo, pageSize) {
            if (pageNo) {
                this.model.search.pageNo(pageNo);
            }
            if (pageSize) {
                this.model.search.pageSize(pageSize);
            }
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
                        master.toast.info(that.messages.list.loadPrevPage);
                    } else {
                        master.toast.info(that.messages.list.noData);
                    }
                }
                that.page($('#pagination'), page);
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
                    var index = master.fmt.index(v, checkedArray, 'id');
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
        },
        /**
         * 当前页全选/全不选
         * @param data
         * @param e
         * @returns {boolean}
         */
        checkAll: function (data, e) {
            var isChecked = $(e.target).is(':checked'),
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
            var isChecked = $(e.target).is(':checked');
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
        getCheckedArrayIds: function () {
            var ids = [];
            $.each(this.model.checkedArray.peek(), function (index, item) {
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
                return master.sweet.warning('id或data不能为空!');
            }
            service.update(data).then(function () {
                that.afterUpdateData();
            });
        },
        /**
         * 删除记录
         * @param id
         */
        deleteItem: function (id, isSilent) {
            var that = this;
            if (typeof isSilent !== 'boolean') {
                isSilent = false;
            }
            function doDelete() {
                service.del(id).then(function () {
                    that.afterDeleteItem();
                });
            }

            if (isSilent) {
                doDelete();
            } else {
                $.confirm({
                    title: this.messages.delete.item.title,
                    content: this.messages.delete.item.content,
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
                return master.sweet.warning(this.messages.delete.batch.noData);
            }
            master.sweet.confirm({
                title: this.messages.delete.batch.title,
                content: this.messages.delete.batch.content
            }, function () {
                service.del(ids.join(',')).then(function () {
                    that.afterDeleteBatch();
                });
            });
        },
        /**
         * 导入Excel
         * @param fileInput
         * @param data 上传的附加数据，必须包含xlsName，指定处理服务
         * @returns {*}
         */
        importXls: function (fileInput, data) {
            var that = this;
            master.work.uploadXls(fileInput, {
                data: $.extend(true, {}, data, this.setImportData(data)),
                afterUpload: function (result, data, fileInput) {
                    that.afterImportXls(result, data, fileInput);
                }
            });
        },
        /**
         * 导出Excel
         * @param xlsName 指定处理服务的名称
         * @param size
         */
        exportXls: function (xlsName, size) {
            var data = ko.mapping.toJS(this.model.search),
                that = this;
            if (!size) {
                data.pageNo = 1;
                data.pageSize = Number.MAX_VALUE;
            } else {
                data.pageSize = size;
            }
            data.xlsName = xlsName;
            service.exportXls(data).then(function (xlsDownloadUrl) {
                that.afterExportXls(xlsDownloadUrl);
            });
        },
        /**
         * 显示form弹出框
         * @param id
         */
        showFormDialog: function (id, url) {
            url = (typeof url === 'string' ? url : 'form') + (id ? '/' + id : '');
            $('#formIframe').attr('src', master.utils.compatUrl(url, this.setFormDialogData(id, url)));
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
                master.sweet.success(this.messages.form.success);
            });
        },
        /**
         * 显示详情dialog
         * @param id
         */
        showDetailDialog: function (id) {
            $('#detailIframe').removeAttr('src').attr('src', 'detail/' + id);
            master.utils.showDialog($('#detailDialog'));
        },
        /**
         * 上移
         * @param data
         */
        moveUp: function (data) {
            var that = this;
            service.moveUp(data).then(function () {
               that.afterMoveUp();
            });
        },
        /**
         * 下移
         * @param data
         */
        moveDown: function (data) {
            var that = this;
            service.moveDown(data).then(function () {
                that.afterMoveDown();
            });
        },
        /**
         * 置顶
         * @param data
         */
        zTop: function (data) {
            var that = this;
            service.zTop(data).then(function () {
               that.afterZTop();
            });
        }
    });

    $(function () {
        ready.init(['selectPicker', 'select2Picker', 'datetimePicker', 'validatePicker']);
    });

    // 开启ko延迟更新
    ko.options.defer = true;

}(jQuery, (window.viewModel || (window.viewModel = {}))));