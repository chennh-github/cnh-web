;
(function ($, viewModel) {

    "use strict";

    $.extend(true, viewModel, {
        model: {
            entity: {
                id: id,
                title: "",
                url: "",
                menuType: menuType,
                describtion: "",
                parentId: parentId,
                menuIcon: "",
                code: "",
                status: 1,
                orderNo: "",
                target: "_self"
            },
            iconList: ICON_LIST
        },
        initReady: function () {
            var that = this;

            // 代理图标选择事件
            $('.icon-list').delegate('li a', 'click', function (e) {
                var icon = this.getAttribute('data-icon');
                that.model.entity.menuIcon(icon);
                master.utils.hideDialog($('#iconDialog'));
            });

            master.utils.delay(0, function () {
                $('#target').trigger('change');
            });
        },
        initValidator: function () {
            var $form = $('#form');
            $form.find('[name=orderNo]').rules('add', {
                digits: true
            });
        },
        showIconDialog: function () {
            master.utils.showDialog($('#iconDialog'));
        },
        afterEntity: function () {
            $('#target').trigger('change');
        },
        afterSaveOrUpdate: $.noop
    });


    $(function () {
        viewModel.initViewModel();
    });

}(jQuery, (window.viewModel || (window.viewModel = {}))));