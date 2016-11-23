<#import "/system/frame/main.ftl" as frame>

<@frame.header>
</@frame.header>
<@frame.body modular="详情页">

<div class="panel panel-info">
    <div class="panel-heading">
        <div class="panel-control">
            <button class="btn btn-s btn-bordered btn-default" type="button" data-panel="refresh">
                <i class="fa fa-repeat"></i>
            </button>
            <button class="btn btn-s btn-bordered btn-default" type="button" data-panel="collapse">
                <i class="fa fa-minus"></i>
            </button>
        </div>
    </div>
    <div class="panel-body">

        <table class="table table-responsive table-hover table-detail">
            <thead>
            <tr>
                <th style="width: 20%;"></th>
                <th style="width: 30%;"></th>
                <th style="width: 20%;"></th>
                <th style="width: 30%;"></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>1</td>
                <td>Otto</td>
                <td><label class="label label-success">Success</label></td>
                <td>otto.doggy@loremipsumiun.com</td>
            </tr>
            <tr>
                <td>2</td>
                <td>Adda</td>
                <td><label class="label label-info">Info</label></td>
                <td>otto.lady@loremipsumiun.com</td>
            </tr>
            <tr>
                <td>3</td>
                <td>Thomas</td>
                <td><label class="label label-danger">Danger</label></td>
                <td>thomasy@loremipsumiun.com</td>
            </tr>
            <tr>
                <td>4</td>
                <td>Peter</td>
                <td><label class="label label-warning">Warning</label></td>
                <td>peter@loremipsumiun.com</td>
            </tr>
            </tbody>
        </table>

    </div>
</div>




</@frame.body>
<@frame.footer>

    <@script src="static/js/system/demo/detail.js" />
</@frame.footer>