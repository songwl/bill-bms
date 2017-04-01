<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "操作员列表">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/operator/operatorList.js"></script>
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>操作员管理</span> > <span>操作员列表</span>
    </div>
    <div class="nav_R right" id="divQx">

        <div class="Import">
            <span id="addOperator">&nbsp;<i class="fa fa-plus"></i>&nbsp;添加操作员</span>

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
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;操作员列表</div>
                    <div class="Nav_Right">
                    </div>
                    <div class="cls"></div>
                </div>

                <table id="myTable" class="table table-striped  table-condensed table-responsive" style="width:100%">
                </table>
            </div>

        </div>
    </div>
</div>

<div class="modal-backdrop in" style="display: none">
</div>


<!--添加操作员-->

<div class="bootbox modal in addOperatorDiv" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="bootbox-close-button close">×</button>
                <h4 class="modal-title">添加操作员</h4>
            </div>
            <div class="modal-body" style="max-height: 574px;">
                <div class="bootbox-body">
                    <style type="text/css">
                        .modal-dialog {
                            width: 500px;
                        }
                    </style>
                    <form class="form-horizontal row-border" id="addOperatorform" action="#" novalidate="novalidate">
                        <div class="form-group">
                            <label class="col-md-3 control-label">
                                登录名<span class="required">* </span>
                            </label>
                            <div class="col-md-9">
                                <input name="userName" class="form-control input-width-large" type="text" id="userName1">
                            </div>
                            <div class="pdlogid"></div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">
                                密码<span class="required">* </span>
                            </label>
                            <div class="col-md-9">
                                <input name="password" class="form-control input-width-large" type="password" id="password">
                            </div>
                            <div class="pdpwd"></div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 control-label">
                                真实姓名<span class="required">* </span>
                            </label>
                            <div class="col-md-9">
                                <input name="realName" class="form-control input-width-large" type="text">
                            </div>

                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">
                                联系人<span class="required">* </span>
                            </label>
                            <div class="col-md-9">
                                <input name="contact" class="form-control input-width-large" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">
                                联系电话<span class="required">* </span>
                            </label>
                            <div class="col-md-9">
                                <input name="phone" class="form-control input-width-large" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">
                                QQ<span class="required">* </span>
                            </label>
                            <div class="col-md-9">
                                <input name="qq" class="form-control input-width-large" type="text">
                            </div>
                        </div>
                    </form>

                </div>
            </div>
            <div class="modal-footer">
                <button data-bb-handler="success" type="button" class="btn wzgj-blue addOperatorcmt">确定</button>
                <button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
            </div>
        </div>
    </div>
</div>



<!--添加操作员end-->

</@base.html>