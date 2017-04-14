<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "审核订单">

<script src="//rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script>
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/bill/pendingAudit.js"></script>
<script src="${ctx}/static/js/bill/pendingAuditStyle.js"></script>

<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>优化管理</span> > <span>审核订单</span>
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
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;审核订单</div>
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
<!--管理员审核-->
<#if  bmsModel.user.hasRole("SUPER_ADMIN")>
<div class="bootbox modal in billExamineDiv" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false">
    <div class="modal-dialog" style="width: 990px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="bootbox-close-button close">×</button>
                <h4 class="modal-title">审核</h4>
            </div>
            <div class="modal-body" style="max-height: 374px;">
                <div class="bootbox-body">
                    <style type="text/css">
                        .modal-dialog {
                            width: 990px;
                        }
                    </style>

                        <div><h5>专员分配</h5></div>
                        <select class="selectpicker" id="caozuoyuan">
                            <option>--请选择--</option>
                            <#list  userList as user>
                                <option value="${user.id}">${user.userName}</option>
                            </#list>
                        </select>
                        <div class="form-group" style="height:50px;margin-top:10px;">
                            <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                前
                                <span class="red">N</span>名<span class="required">*</span>
                            </label>
                            <div class="col-md-1 padding-left-0px padding-right-0px" style="width: 35px;">
                                <input name="adminrankend"   style="width: 35px;">
                            </div>

                            <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                <span>元/天</span><span class="required">*</span>
                            </label>
                            <div class="col-md-1 padding-left-5px padding-right-0px" style="width: 35px;">
                                <input name="adminprice"   style="width: 35px;">
                            </div>
                            <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                前
                                <span class="red">N</span>名
                            </label>
                            <div class="col-md-1 padding-left-0px padding-right-0px" style="width: 35px;">
                                <input name="adminrankend1"   style="width: 35px;">
                            </div>

                            <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                <span>元/天</span>
                            </label>
                            <div class="col-md-1 padding-left-5px padding-right-0px" style="width: 35px;">
                                <input name="adminprice1"   style="width: 35px;">
                            </div>
                            <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                前
                                <span class="red">N</span>名
                            </label>
                            <div class="col-md-1 padding-left-0px padding-right-0px" style="width: 35px;">
                                <input name="adminrankend2"  style="width: 35px;">
                            </div>

                            <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                <span>元/天</span><span class="required">*</span>
                            </label>
                            <div class="col-md-1 padding-left-5px padding-right-0px" style="width: 35px;">
                                <input name="adminprice2"   style="width: 35px;">
                            </div>
                            <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                前
                                <span class="red">N</span>名
                            </label>
                            <div class="col-md-1 padding-left-0px padding-right-0px" style="width: 35px;">
                                <input name="adminrankend3"   style="width: 35px;">
                            </div>

                            <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                <span>元/天</span>
                            </label>
                            <div class="col-md-1 padding-left-5px padding-right-0px" style="width: 35px;">
                                <input name="adminprice3"   style="width: 35px;">
                            </div>
                            <div class="pload" style="position:absolute;top:45%;left: 50%; z-index:2200;background:url('${ctx}/static/img/load3.gif') top center no-repeat;width:40px;height:40px;margin:auto auto;display: none;"></div>

                        </div>
            </div>
            <div class="modal-footer" style="height:50px;">
                <button data-bb-handler="success" type="button" class="btn wzgj-blue adminshenhe">确定</button>
                <button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
            </div>
        </div>
    </div>
</div>
</#if>
<!--审核end-->
<!--渠道商审核-->
    <#if  bmsModel.user.hasRole("DISTRIBUTOR")>
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
                                width: 990px;
                            }
                        </style>
                        <div>

                            <div class="form-group" style="margin-top:30px;">
                                <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                    前
                                    <span class="red">N</span>名<span class="required">*</span>
                                </label>
                                <div class="col-md-1 padding-left-0px padding-right-0px" style="width: 35px;">
                                    <input name="rankend"   style="width: 35px;">
                                </div>

                                <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                    <span>元/天</span><span class="required">*</span>
                                </label>
                                <div class="col-md-1 padding-left-5px padding-right-0px" style="width: 35px;">
                                    <input name="price"   style="width: 35px;">
                                </div>
                                <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                    前
                                    <span class="red">N</span>名
                                </label>
                                <div class="col-md-1 padding-left-0px padding-right-0px" style="width: 35px;">
                                    <input name="rankend1"   style="width: 35px;">
                                </div>

                                <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                    <span>元/天</span>
                                </label>
                                <div class="col-md-1 padding-left-5px padding-right-0px" style="width: 35px;">
                                    <input name="price1"   style="width: 35px;">
                                </div>
                                <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                    前
                                    <span class="red">N</span>名
                                </label>
                                <div class="col-md-1 padding-left-0px padding-right-0px" style="width: 35px;">
                                    <input name="rankend2"  style="width: 35px;">
                                </div>

                                <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                    <span>元/天</span><span class="required">*</span>
                                </label>
                                <div class="col-md-1 padding-left-5px padding-right-0px" style="width: 35px;">
                                    <input name="price2"   style="width: 35px;">
                                </div>
                                <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                    前
                                    <span class="red">N</span>名
                                </label>
                                <div class="col-md-1 padding-left-0px padding-right-0px" style="width: 35px;">
                                    <input name="rankend3"   style="width: 35px;">
                                </div>

                                <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                                    <span>元/天</span>
                                </label>
                                <div class="col-md-1 padding-left-5px padding-right-0px" style="width: 35px;">
                                    <input name="price3"   style="width: 35px;">
                                </div>
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button data-bb-handler="success" type="button" class="btn wzgj-blue qudaoShenhe">确定</button>
                    <button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
                </div>
            </div>
        </div>
    </div>
    </#if>
<!--审核end-->
</@base.html>