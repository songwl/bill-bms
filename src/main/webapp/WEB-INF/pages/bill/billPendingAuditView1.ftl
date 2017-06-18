<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "待审核订单">

<script src="//rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script>
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/bill/pendingAuditView1.js"></script>

<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>客户方管理</span> > <span>待审核订单</span>
    </div>
    <div class="nav_R right" id="divQx">
        <div class="pass" style="width:120px;">
            <span id="pass1">&nbsp;<i class="fa fa-legal"></i>&nbsp;上级待审核</span>
        </div>
        <div class="pass1" style="width:120px;">
            <span id="pass2">&nbsp;<i class="fa fa-bullhorn"></i>&nbsp;客户待审核</span>
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
    <div class="nav_R2 right col-md-11" >
        <input type="hidden" name="type" value="${way}" id="way">
        <div>
            网址:
        </div>
        <input id="website" name="userName" class="form-control" value="" style="width: 150px;" type="text">
        <div>
            关键词:
        </div>
        <input id="keywords" name="keywords" class="form-control" value="" style="width: 150px;" type="text">
        <span style="font-size:13px;text-align:center;cursor:pointer;font-weight:bold;margin-left: 10px;">
        搜索引擎:
        </span>
        <select style="height: 35px;border: 1px solid #aaaaaa;" id="searchName">
            <@dict.showOptions dictKey="search" dictType="DICT" haveBlank="Y" />
        </select>
        <span style="font-size:13px;text-align:center;cursor:pointer;font-weight:bold;margin-left: 10px;">
        客户:
        </span>

        <select style="height: 35px;border: 1px solid #aaaaaa;" id="searchUserName">
            <option>--请选择--</option>
            <#list userList as user>
                <option value="${user.id}">${user.userName}</option>
            </#list>
        </select>


        <select style="height: 35px;border: 1px solid #aaaaaa;">
            <option>--请选择--</option>
            <option>是</option>
            <option>否</option>
        </select>


        <input type="hidden" value="0" id="searchStatechange">
        <span id="searchButton">查询</span>



    </div>
    <div class="cls">
    </div>
</div>
<div class="row" id="row">
    <div class="col-md-12">
        <div class="panel panel-primary" id="list-panel">
            <div class="panel-body">
                <div class="panel-nav">
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;客户待审核</div>
                    <div class="Nav_Right">
                    </div>
                    <div class="cls"></div>
                </div>
                <div id="toolbar" class="btn-group">
                    <button id="billExamine" type="button" class="btn btn-default">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>审核通过</button>
                    <button id="billNotExamine" type="button" class="btn btn-default">
                        <span class="glyphicon glyphicon-leaf" aria-hidden="true" id=""></span>审核不通过</button>
                </div>

                <table id="myTable" class="table table-striped  table-condensed table-responsive" style="width:100%;font-size: 13px;font-family: "微软雅黑">
                </table>
            </div>

        </div>
    </div>
</div>

<div class="bootbox modal in billExamineDiv " tabindex="-1 " role="dialog " style="display: none; " aria-hidden="false ">
    <div class="modal-dialog " style="width: 990px; ">
        <div class="modal-content ">
            <div class="modal-header ">
                <button type="button " class="bootbox-close-button close">×</button>
                <h4 class="modal-title ">审核</h4></div>
            <div class="modal-body" style="max-height: 574px; ">
                <div class="bootbox-body ">
                    <style type="text/css ">
                        .modal-dialog {
                            width: 990px;
                        }
                    </style>
                    <div>
                        <div class="alert alert-info fade in">
                            录入价格的时候请按对应的顺序录入，请谨慎录入。顺序（排名1，价格1，排名2，价格2,......）
                        </div>
                        <div class="form-group" style="margin-bottom:55px;">
                            <label class="col-md-1 control-label padding-right-0px padding-left-0px " style="width: 80px; ">
                                前
                                <span class="red">N</span>名<span class="required">*</span></label>
                            <div class=" " style="width: 35px;float: left; ">
                                <input name="rankend"   style="width: 35px; "></div>
                            <label class="col-md-1 control-label padding-right-0px padding-left-0px " style="width: 78px; ">
                                <span>元/天</span><span class="required ">*</span></label>
                            <div class=" " style="width: 35px;float: left; ">
                                <input name="price"   style="width: 35px; "></div>
                            <label class="col-md-1 control-label padding-right-0px padding-left-0px " style="width: 78px; ">
                                前
                                <span class="red">N</span>名
                            </label>
                            <div class="" style="width: 35px;float: left; ">
                                <input name="rankend1"   style="width: 35px; "></div>
                            <label class="col-md-1 control-label padding-right-0px padding-left-0px " style="width: 78px; ">
                                <span>元/天</span></label>
                            <div class=" " style="width: 35px;float: left; ">
                                <input name="price1"   style="width: 35px; "></div>
                            <label class="col-md-1 control-label padding-right-0px padding-left-0px " style="width: 78px; ">
                                前
                                <span class="red">N</span>名
                            </label>
                            <div class=" " style="width: 35px;float: left; ">
                                <input name="rankend2"  style="width: 35px;"></div>
                            <label class="col-md-1 control-label padding-right-0px padding-left-0px " style="width: 78px; ">
                                <span>元/天</span></label>
                            <div class=" " style="width: 35px;float: left; ">
                                <input name="price2"   style="width: 35px;"></div>
                            <label class="col-md-1 control-label padding-right-0px padding-left-0px " style="width: 78px; ">
                                前
                                <span class="red">N</span>名
                            </label>
                            <div class="" style="width: 35px;float: left; ">
                                <input name="rankend3"   style="width: 35px; "></div>
                            <label class="col-md-1 control-label padding-right-0px padding-left-0px " style="width:78px; ">
                                <span>元/天</span></label>
                            <div class=" " style="width: 35px;float: left; ">
                                <input name="price3"   style="width: 35px;"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer" style="height:70px;">
                <button data-bb-handler="success " type="button " class="btn wzgj-blue qudaoShenhe" style="background: #09C;color: #fff; ">确定</button>
                <button data-bb-handler="cancel " type="button " class="btn wzgj-btn cancel ">取消</button></div>
        </div>
    </div>
</div>

<div class="modal-backdrop in" style="display: none">
</div>
</@base.html>