;(function () {


    $(function () {
        ready.init(['clockPicker', 'datePicker', 'datetimePicker', 'selectPicker', 'select2Picker', 'passwordPicker',
        'knobPicker', 'maskPicker', 'spinPicker', 'filePicker', 'validatePicker', 'foxiboxPicker', 'imgPicker']);


        $('.daterange-picker').daterangepicker({
            ranges: {
                "昨日学习一览": [moment().subtract(1, "days"), moment().subtract(1, "days")],
                "最近7天学习一览": [moment().subtract(7, "days"), moment().subtract(1, "days")],
                "最近30天学习一览": [moment().subtract(30, "days"), moment().subtract(1, "days")]
            },
            startDate: moment().subtract(1, "days"),
            endDate: moment().subtract(1, "days"),
            showDropdowns: true,
            locale: {
                format: "YYYY-MM-DD",
                separator: " 至 ",
                applyLabel: '确定',
                cancelLabel: '取消',
                fromLabel: '从',
                toLabel: '至',
                customRangeLabel: '自定义日期范围'
            }
        }, function (start, end, label) {

        });
    });

}());