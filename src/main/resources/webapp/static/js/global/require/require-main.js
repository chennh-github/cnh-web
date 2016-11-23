;(function (require) {

	var dataAppSrc = $("script[data-app]").attr("data-app");
	
    require.config(window.requireConfig || {});
    require.config({
        paths: {
            app: dataAppSrc
        }
    });

    require(["knockout",
             "koComponentRegister", 
             "app"], function (ko, koComponentRegister, app){
        koComponentRegister.registerAllComponents();
        
        app.initViewModel();
    });
} (require));