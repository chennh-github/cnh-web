define(["knockout", "koMapping", "fileinput/v4.3.2/js/fileinput", "css!fileinput/v4.3.2/css/fileinput.css",
"bootstrap/v3.3.5/js/bootstrap", "css!bootstrap/v3.3.5/css/bootstrap"], function (ko, koMapping) {

    require(["fileinput/v4.3.2/js/fileinput_locale_zh"], function (){});

    var global = (0, eval)("this");

    var viewModel = {
        model: {
            fileList: (function (urls) {
                return urls ? urls.split(",") : [];
            }(window.urls))
        },
        initViewModel: function () {
            this.model = koMapping.fromJS(this.model);

            ko.applyBindings(this);
        }
    };

    global.getFileList = function () {
        return ko.unwrap(viewModel.model.fileList);
    };

    return viewModel;
});