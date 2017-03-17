<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "待审核订单">

<script src="//rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script>
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/bill/pendingAudit.js"></script>
<script src="${ctx}/static/js/bill/pendingAuditStyle.js"></script>

<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>优化管理</span> > <span>待审核订单</span>
    </div>
    <div class="nav_R right" id="divQx">

        <div class="Import">
            <span id="Import">&nbsp;<i class="fa fa-arrow-down"></i>&nbsp;导出</span>

        </div>
        <div class="search">
            <span>&nbsp;<i class="fa fa-search"></i>&nbsp;查询</span>
        </div>

    </div>
    </div>
    <div class="cls">
    </div>
</div>
<div class="Navs2">

    <div class="nav_R2 right" >
        <div>
            客户ID:
        </div>
        <input name="acid" class="form-control" value="" style="width: 60px;" type="text">
    </div>
    <div class="cls">
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary" id="list-panel">
            <div class="panel-body">
                <div class="panel-nav">
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;待审核订单</div>
                    <div class="Nav_Right">
                    </div>
                    <div class="cls"></div>
                </div>
                <div id="toolbar" class="btn-group">
                    <button id="billExamine" type="button" class="btn btn-default">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 审核通过
                    </button>
                    <button id="testpm" type="button" class="btn btn-default">
                        <span class="glyphicon glyphicon-leaf" aria-hidden="true" id=""></span> 审核不通过
                    </button>

                </div>
                <table id="myTable" class="table table-striped  table-condensed table-responsive" style="width:100%">
                </table>
            </div>

        </div>
    </div>
</div>
<div class="modal-backdrop in" style="display: none">
</div>
<!--审核-->

<div class="bootbox modal in billExamineDiv" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="bootbox-close-button close">×</button>
                <h4 class="modal-title">审核</h4>
            </div>
            <div class="modal-body" style="max-height: 374px;">
                <div class="bootbox-body">
                    <style type="text/css">
                        .modal-dialog {
                            width: 400px;
                        }
                    </style>
                    <div>
                        <div><h5>专员分配</h5></div>
                        <select class="selectpicker">
                            <option>--请选择--</option>
                            <option>张三</option>
                            <option>李四</option>
                            <option>王五</option>
                            <option>赵六</option>
                        </select>
                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <button data-bb-handler="success" type="button" class="btn wzgj-blue changeprice">确定</button>
                <button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
            </div>
        </div>
    </div>
</div>
<!--调价end-->

</@base.html>