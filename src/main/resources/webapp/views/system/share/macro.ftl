
<#--Modal-->
<#macro modal id="" title="系统提示" class="">
<div class="modal no-backdrop fade" id="${id}" tabindex="-1" role="dialog">
    <div class="modal-dialog ${class}">
        <div class="modal-content">
            <div class="color-line"></div>
            <div class="modal-header">
                <button aria-label="Close" data-dismiss="modal" class="close" type="button">
                    <span aria-hidden="true">×</span></button>
                <h4 class="modal-title">${title}</h4>
            </div>
            <#nested />
        </div>
    </div>
</div>
</#macro>

<#--Dialog-->
<#macro dialog id="" title="" width="100%">
<div class="dialog" id="${id}">
    <div class="panel" style="width: ${width};">
        <div class="panel-heading">
            <div class="panel-control">
                <button class="btn btn-s btn-bordered btn-default" type="button" data-toggle="collapse">
                    <i class="fa fa-minus"></i>
                </button>
                <button class="btn btn-s btn-bordered btn-default" type="button" data-toggle="close">
                    <i class="fa fa-remove"></i>
                </button>
            </div>
            <div class="panel-title">${title}</div>
        </div>
        <#nested />
    </div>
</div>
</#macro>

<#--form Dialog-->
<#macro formDialog dialogId="formDialog" iframeId="formIframe">
    <@dialog id="${dialogId}" width="1400px">
    <div class="panel-body" style="height: 450px;">
        <iframe id="${iframeId}" frameborder="0" class="iframe"></iframe>
    </div>
    <div class="panel-footer to-right">
        <button class="btn btn-default" data-bind="click: hideFormDialog">取消</button>
        <button class="btn btn-theme" data-bind="click: submitFormDialog">确定</button>
    </div>
    </@dialog>
</#macro>

<#--detail Dialog-->
<#macro detailDialog dialogId="detailDialog" iframeId="detailIframe">
    <@macro.dialog id="${dialogId}" width="1400px">
    <div class="panel-body" style="height: 450px;">
        <iframe id="${detailIframe}" frameborder="0" class="iframe"></iframe>
    </div>
    </@macro.dialog>
</#macro>

<!-- upload -->
<#macro upload title="图片管理">
<div class="dialog dialog-iframe" id="uploadDialog">
    <div class="panel" style="width: 80%;">
        <div class="panel-heading">
            <div class="panel-control">
                <button class="btn btn-s btn-bordered btn-default" type="button" data-toggle="collapse">
                    <i class="fa fa-minus"></i>
                </button>
                <button class="btn btn-s btn-bordered btn-default" type="button" data-toggle="close">
                    <i class="fa fa-remove"></i>
                </button>
            </div>
            <div class="panel-title">${title}</div>
        </div>
        <div class="panel-body" style="position: relative; height: 430px; max-height: 800px;"></div>
        <div class="panel-footer">
            <div class="row">
                <button class="btn btn-primary pull-right btn-select">确定</button>
                <button class="btn btn-default pull-right r-mar-25" data-toggle="close">关闭</button>
            </div>
        </div>
    </div>
</div>
</#macro>
