;(function ($) {
	
	$.extend($.validator.messages, {
        required    : "  为必填字段",
        remote      : "  请修正该字段",
        email       : "  必须为合法的电子邮件",
        url         : "  必须为合法的网址",
        date        : "  必须为合法的日期格式",
        dateIOS     : "  必须为合法的日期格式(ISO)",
        number      : "  必须为合法的数字",
        digits      : "  必须为整数",
        creditcard  : "  必须为合法的信用卡号",
        equalTo     : "  的值与第一次输入的不同",
        accept      : "  只允许合法后缀名的字符串",
        maxlength   : $.validator.format("  必须是一个 长度最多是 {0} 的字符串"),
        minlength   : $.validator.format("  必须是一个 长度最少是 {0} 的字符串"),
        rangelength : $.validator.format("  必须是一个长度介于 {0} 和 {1} 之间的字符串"),
        range       : $.validator.format("  必须是一个介于 {0} 和 {1} 之间的值"),
        max         : $.validator.format("  必须是一个最大为{0} 的值"),
        min         : $.validator.format("  必须是一个最小为{0} 的值")
    } );
	
}(jQuery));