<#import "/system/frame/main.ftl" as frame>

<@frame.header>
<style>
    #wrapper {
        position: fixed;
        height: 100%;
    }
    .container-wrapper {
        min-height: inherit !important;
        height: 100%;
    }
    .container-content {
        position: absolute;
        top: 57px;
        right: 0;
        bottom: 0;
        left: 0;
        padding: 0;
        min-height: inherit !important;
    }
    .container-content #bootstrap {
        height: 100%;
    }
    .container-content .mainframe {
        display: block;
        border: none;
        width: 100%;
        height: 100%;
        overflow: auto;
    }
    .frame-loading {
        position: fixed;
        display: none;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background: RGBA(0, 0, 0, .1);
        z-index: 100;
    }
</style>
</@frame.header>

<@frame.body wrapperClass="fixed-sidebar container-footer-fixed">
<iframe id="mainframe" name="mainframe" class="mainframe"></iframe>
<div class="panel-refreshing frame-loading">
    <div class="spinner">
        <div class="se rotating-plane"></div>
    </div>
</div>
</@frame.body>

<@frame.footer>
    <@script src="static/js/system/frame/main-fixed.js" />
</@frame.footer>