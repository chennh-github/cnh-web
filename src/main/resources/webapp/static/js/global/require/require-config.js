/**
 * Author: Administrator
 * Date: 2016/9/6
 * Description:
 *      1. created
 */
;(function () {

    window.requireConfig = {
        baseUrl: fullPath + 'static/',
        urlArgs: 'v=' + window['staticVersion'] || new Date().getTime(),
        paths: {
            text: 'js/asserts/require-text/v2.0.14/js/text',
            css: 'js/asserts/require-css/v0.1.8/js/css',

            jquery: 'js/asserts/jquery/v2.1.4/js/jquery.min',
            bootstrap: 'js/asserts/bootstrap',
            knockout: 'js/asserts/knockout/v3.4.0/js/knockout',
            koMapping: 'js/asserts/knockout-mapping/v2.4.1/js/knockout.mapping',

            components: 'js/components/',
            template: fullPath + 'component/template?templatePath=',
            koComponentRegister: 'js/global/ko/ko.component.register',

            fileinput: 'js/asserts/bootstrap-fileinput/'
        },
        map: {
            '*': {
                'css': 'css'
            }
        },
        shim: {
            fileinput: {
                exports: '$.fn.fileinput'
            }
        }
    };

}());