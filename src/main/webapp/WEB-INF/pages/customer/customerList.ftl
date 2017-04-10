<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "客户列表">

<script src="//rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script>
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/customer/customerList.js"></script>

<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>客户管理</span> > <span>客户列表</span>
    </div>
    <div class="nav_R right" id="divQx">
    <#if  bmsModel.user.hasRole("SUPER_ADMIN")||bmsModel.user.hasRole("DISTRIBUTOR")||bmsModel.user.hasRole("AGENT")>
        <div class="Import addMember">
            <span id="Import">&nbsp;<i class="fa fa-plus"></i>&nbsp;添加客户</span>
        </div>
    </#if>
        <#if bmsModel.user.hasRole("DISTRIBUTOR")>
            <div class="Import addAGENT">
                <span id="Import">&nbsp;<i class="fa fa-plus"></i>&nbsp;添加代理商</span>
            </div>
        </#if>
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
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;客户列表</div>
                    <div class="Nav_Right">

                    </div>
                    <div class="cls"></div>
                </div>
                <div id="toolbar" class="btn-group">

                <#if  bmsModel.user.hasRole("SUPER_ADMIN")|| bmsModel.user.hasRole("DISTRIBUTOR")|| bmsModel.user.hasRole("AGENT")>
                    <button   type="button" class="btn btn-default" id="Recharge">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 充值
                    </button>
                </#if>

                </div>

                <table id="myTable" class="table table-striped  table-condensed table-responsive" style="width:100%">
                </table>
            </div>

        </div>
    </div>
</div>


<div class="modal-backdrop in" style="display: none">
</div>
<!--添加客户-->
<div class="bootbox modal in addMemberDiv" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="bootbox-close-button close">×</button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body" style="max-height: 574px;">
                <div class="bootbox-body">
                    <style type="text/css">
                        .modal-dialog {
                            width: 500px;
                        }
                    </style>
                    <form class="form-horizontal row-border" id="addOperatorform" action="#" novalidate="novalidate">
                        <div class="form-group" style="display:none;" id="nameDiv">
                            <label class="col-md-3 control-label">
                                登录名<span class="required">* </span>
                            </label>
                            <div class="col-md-9">
                                <input name="userName" class="form-control input-width-large" type="text" id="userName1">
                            </div>
                            <div class="pdlogid"></div>
                        </div>
                        <div class="form-group" style="display:none;" id="nameDiv1">
                            <label class="col-md-3 control-label">
                                登录名<span class="required">* </span>
                            </label>
                            <div class="col-md-9">
                                <input name="userName1" class="form-control input-width-large" type="text"  readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group" id="viewpwd">
                            <label class="col-md-3 control-label">
                                密码<span class="required">* </span>
                            </label>
                            <div class="col-md-9">
                                <input name="password" class="form-control input-width-large" type="password">
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
                        <div class="form-group" id="viewbalance">
                            <label class="col-md-3 control-label">
                                充值金额<span class="required">*</span>
                            </label>
                            <div class="col-md-9">
                                <input name="balance" id="balance" class="form-control input-width-large" type="text" value="0">
                            </div>
                        </div>
                        <div class="form-group" id="viewstate"  style="display: none;">
                            <label class="col-md-3 control-label">
                                状态<span class="required">* </span>
                            </label>
                            <div class="col-md-9">
                                <select id="viewstatus" name="status">

                                </select>
                            </div>
                        </div>
                    </form>
                   <input type="hidden" value="1" id="addMemberId">
                    <input type="hidden" value="" name="customerId">
                </div>
            </div>
            <div class="modal-footer">
                <button data-bb-handler="success" type="button" class="btn wzgj-blue addOperatorcmt" style="display: none">确定</button>
                <button data-bb-handler="success" type="button" class="btn wzgj-blue updateOperatorcmt" style="display: none">确定</button>
                <button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
            </div>
        </div>
    </div>
</div>
<!--添加客户end-->

<!--客户充值-->
<div class="bootbox modal in RechargeDiv" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="bootbox-close-button close">×</button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body" style="max-height: 374px;">
                <div class="bootbox-body">
                    <style type="text/css">
                        .modal-dialog {
                            width: 400px;
                        }
                    </style>
                    <form class="form-horizontal row-border" id="edt-form" action="#" novalidate="novalidate">
                        <div class="form-group">
                            <div style="margin-left:15px;" class="Amount">充值金额：</div>
                            <div style="margin-left:10px;margin-top: 10px;">
                             <input type="text" value="" id="RechargeSum">
                            </div>
                        </div>
                         <input type="hidden" value="" id="customerId">
                    </form>

                </div>
            </div>
            <div class="modal-footer">
                <button data-bb-handler="success" type="button" class="btn wzgj-blue Rechargecmt" style="display: none;">确定</button>
                <button data-bb-handler="success" type="button" class="btn wzgj-blue Refoundcmt" style="display: none;">确定</button>
                <button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
            </div>
        </div>
    </div>
</div>
<!--客户充值end-->
</@base.html>