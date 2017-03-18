<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "关键词优化">

<script src="//rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script>
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<link href="${ctx}/static/css/public/pace.css" rel="stylesheet">
<script src="${ctx}/static/js/public/pace.js"></script>
<script src="${ctx}/static/js/bill/billList.js"></script>
<script src="${ctx}/static/js/bill/billListchangePrice.js"></script>
<script src="${ctx}/static/js/bill/billListsamePrice.js"></script>
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>优化管理</span> > <span>关键词排名</span>
    </div>
    <div class="nav_R right" id="divQx">

       <#if  bmsModel.user.hasRole("SUPER_ADMIN")||bmsModel.user.hasRole("ADMIN")||bmsModel.user.hasRole("COMMISSIONER")> <#--如果是优化方-->
        <div>
            <span>&nbsp;<i class="fa fa-play"></i>&nbsp;优化启动</span>
        </div>

        <div>
            <span>&nbsp;<i class="fa fa-certificate"></i>&nbsp;优化调整</span>
        </div>

        <div>
            <span>&nbsp;<i class="fa fa-stop"></i>&nbsp;合作停止</span>
        </div>
        <div>
            <span>&nbsp;<i class="fa fa-trash"></i>&nbsp;删除</span>
        </div>
       </#if>
    <#if  bmsModel.user.hasRole("DISTRIBUTOR")||bmsModel.user.hasRole("AGENT")> <#--如果是客户-->
        <div class="Import">
            <span id="Import">&nbsp;<i class="fa fa-arrow-down"></i>&nbsp;导入</span>
            <ul class="ImportPrice">
                <li id="Sameprice">相同价导入</li>
                <li id="Differentprice">不同价导入</li>
            </ul>
        </div>
    </#if>
        <div class="search">
            <span>&nbsp;<i class="fa fa-search"></i>&nbsp;查询</span>
        </div>

    </div>
    <div class="cls">
    </div>
</div>
<div class="Navs2">

    <div class="nav_R2 right" >
        <input type="hidden" name="type" value="${way}">
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
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;关键词排名</div>
                    <div class="Nav_Right">
                        <div>优化中</div>
                        <div>合作停</div>
                        <div>全部</div>


                    </div>
                    <div class="cls"></div>
                </div>
                <div id="toolbar" class="btn-group">


                    <button id="btn_update" type="button" class="btn btn-default">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 调价
                    </button>

                    <button id="testpm" type="button" class="btn btn-default">
                        <span class="glyphicon glyphicon-leaf" aria-hidden="true" id=""></span> 查排名
                    </button>

                </div>
                <table id="myTable" class="table table-striped  table-condensed table-responsive" style="width:100%">
                </table>
            </div>

        </div>
    </div>
</div>
<!--相同价导入-->
<div class="bootbox modal in samepriceDiv" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false">
<div class="dv1">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="bootbox-close-button close">×</button>
            <h4 class="modal-title">相同价导入</h4>
        </div>
        <div class="modal-body" style="max-height: 574px; border-bottom: 1px solid #eee;">
            <div class="bootbox-body">
                <style type="text/css">
                    .dv1{
                        width: 960px;
                    }
                </style>
                <form class="form-horizontal row-border" id="edt-form" action="#" novalidate="novalidate">
                    <div class="form-group hidden">
                        <div class="progress progress-striped active">
                            <span id="success_span" data-count="0" class="progress-bar progress-bar-success" style="width: 0%; text-align: center; color: black;" title=""></span>
                            <span id="failed_span" data-count="0" class="progress-bar progress-bar-warning" style="width: 0%; text-align: center; color: black;" title=""></span>
                        </div>
                    </div>
                    <div class="form-group" style="border-bottom: 1px solid #eee;padding-bottom: 3px">
                        <label class="col-md-2 control-label padding-right-0px">
                            搜索引擎
                            <span class="required">*</span>
                        </label>
                        <div class="col-md-2 padding-right-0px padding-left-5px input-width-small">
                            <select id="searchengineid" name="searchengineid" class="form-control input-width-small">
                                <@dict.showOptions dictKey="search" dictType="DICT" haveBlank="Y" />
                            </select>
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="col-md-1 control-label padding-right-0px">
                            关键词*
                        </label>
                        <div class="col-md-4 padding-left-5px">
                            <textarea name="keyword" id="keyword" rows="15" class="form-control"  style="width: 296px; height: 297px;resize: none;"></textarea>
                        </div>
                        <label class="col-md-1 control-label padding-right-0px">
                            网址
                            <span class="required">*</span>
                        </label>
                        <div class="col-md-6 padding-left-5px">
                            <textarea name="url" id="url" rows="15" class="form-control"   style=" height: 297px;resize: none;"></textarea>
                        </div>
                    </div>
                    <div class="form-group" style="margin-top:30px;">
                        <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                            前
                            <span class="red">N</span>名<span class="required">*</span>
                        </label>
                        <div class="col-md-1 padding-left-0px padding-right-0px" style="width: 35px;">
                            <input name="samePricerankend"   style="width: 35px;">
                        </div>

                        <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                            <span>元/天</span><span class="required">*</span>
                        </label>
                        <div class="col-md-1 padding-left-5px padding-right-0px" style="width: 35px;">
                            <input name="samePriceprice"   style="width: 35px;">
                        </div>
                        <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                            前
                            <span class="red">N</span>名
                        </label>
                        <div class="col-md-1 padding-left-0px padding-right-0px" style="width: 35px;">
                            <input name="samePricerankend1"   style="width: 35px;">
                        </div>

                        <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                            <span>元/天</span>
                        </label>
                        <div class="col-md-1 padding-left-5px padding-right-0px" style="width: 35px;">
                            <input name="samePriceprice1"   style="width: 35px;">
                        </div>
                        <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                            前
                            <span class="red">N</span>名
                        </label>
                        <div class="col-md-1 padding-left-0px padding-right-0px" style="width: 35px;">
                            <input name="samePricerankend2"  style="width: 35px;">
                        </div>

                        <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                            <span>元/天</span><span class="required">*</span>
                        </label>
                        <div class="col-md-1 padding-left-5px padding-right-0px" style="width: 35px;">
                            <input name="samePriceprice2"   style="width: 35px;">
                        </div>
                        <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                            前
                            <span class="red">N</span>名
                        </label>
                        <div class="col-md-1 padding-left-0px padding-right-0px" style="width: 35px;">
                            <input name="samePricerankend3"   style="width: 35px;">
                        </div>

                        <label class="col-md-1 control-label padding-right-0px padding-left-0px" style="width: 78px;">
                            <span>元/天</span>
                        </label>
                        <div class="col-md-1 padding-left-5px padding-right-0px" style="width: 35px;">
                            <input name="samePriceprice3"   style="width: 35px;">
                        </div>

                    </div>
                </form>
            </div>
        </div>


        <div class="modal-footer">
            <button data-bb-handler="success" type="button" class="btn wzgj-blue samepricecmt">确定</button><button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
        </div>
    </div>
</div>
</div>
<!--相同价导入end-->

<!--不同价导入-->
<div class="bootbox modal in differentpriceDiv" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false">
<div class="dv1">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="bootbox-close-button close">×</button>
            <h4 class="modal-title">不同价导入</h4>
        </div>
        <div class="modal-body" style="max-height: 508px;">
            <div class="bootbox-body">
                <style type="text/css">
                    .dv1 {
                        width: 960px;
                    }
                </style>
                <form class="form-horizontal row-border" id="edt-form" action="#" novalidate="novalidate">
                    <div class="form-group hidden">
                        <div class="progress progress-striped active">
                            <span id="success_span" data-count="0" class="progress-bar progress-bar-success" style="width: 0%; text-align: center; color: black;" title=""></span>
                            <span id="failed_span" data-count="0" class="progress-bar progress-bar-warning" style="width: 0%; text-align: center; color: black;" title=""></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-2 control-label padding-right-0px">
                            搜索引擎
                            <span class="required">*</span>
                        </label>
                        <div class="col-md-2 padding-right-0px padding-left-5px input-width-small">
                            <select id="dfsearch" name="searchengineid" class="form-control input-width-small" style="width:120px;">
                                    <@dict.showOptions dictKey="search" dictType="DICT" haveBlank="Y" />
                            </select>
                        </div>

                        <label class="col-md-2 control-label padding-right-0px">
                            前
                            <span class="red">N</span>名<span class="required">*</span>
                        </label>
                        <div class="col-md-2 padding-right-0px padding-left-5px" style="width: 190px">
                            <input name="rankend" class="form-control input-width-middle" type="text" id="dfrankend">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-1 control-label padding-right-0px">
                            关键词*
                            <span class="required"></span>
                        </label>
                        <div class="col-md-3 padding-left-5px">
                            <textarea name="keyword" rows="15" class="form-control" style="resize: none;" id="dfkeyword"></textarea>
                        </div>
                        <label class="col-md-1 control-label padding-right-0px">
                            网址
                            <span class="required">*</span>
                        </label>
                        <div class="col-md-5 padding-left-5px">
                            <textarea name="url" rows="15" class="form-control" style="resize: none;" id="dfurl"></textarea>
                        </div>
                        <label class="col-md-1 control-label padding-right-0px">
                            元/天
                            <span class="required">*</span>
                        </label>
                        <div class="col-md-1 padding-left-5px">
                            <textarea name="price" rows="15" class="form-control" style="resize: none;" id="dfprice"></textarea>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="modal-footer">
            <button data-bb-handler="success" type="button" class="btn wzgj-blue dfpricecmt">确定</button>
            <button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
        </div>
    </div>
</div>
</div>
<div id="pload" style="position:absolute;top:45%;left: 87%; z-index:2200;background:url('${ctx}/static/img/load3.gif') top center no-repeat;width:40px;height:40px;margin:auto auto;display: none;"></div>
<!--不同价导入end-->

<!--调价-->
<div class="bootbox modal in changepriceDiv" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="bootbox-close-button close">×</button>
                <h4 class="modal-title">调价</h4>
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
                            <table class="table table-striped table-bordered">
                                <tbody>
                                <tr>
                                    <th>
                                        前<span class="red">N</span>名
                                    </th>
                                    <th>
                                        收费<span class="red">(元/天)</span>
                                    </th>
                                </tr>
                                <tr>
                                    <td>
                                        <input name="changerankend" class="form-control input-width-small" type="text">
                                    </td>
                                    <td>
                                        <input name="changeprice" class="form-control input-width-small" type="text">
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <input name="changerankend1" class="form-control input-width-small" type="text">
                                    </td>
                                    <td>
                                        <input name="changeprice1" class="form-control input-width-small" type="text">
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <input name="changerankend2" class="form-control input-width-small" type="text">
                                    </td>
                                    <td>
                                        <input name="changeprice2" class="form-control input-width-small" type="text">
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <input name="changerankend3" class="form-control input-width-small" type="text">
                                    </td>
                                    <td>
                                        <input name="changeprice3" class="form-control input-width-small" type="text">
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <input name="tids" value="" type="hidden">
                        <input name="op" value="cp" type="hidden">
                    </form>

                </div>
            </div>
            <div class="modal-footer">
                <button data-bb-handler="success" type="button" class="btn wzgj-blue changeprice">确定</button><button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
            </div>
        </div>
    </div>
</div>
<!--调价end-->
<!--详情-->

    <div class="modal-backdrop in" style="display: none">
    </div>
    <div class="bootbox modal in" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false" id="billCostDetail">
        <div class="modal-dialog listdetails">
            <div class="modal-content">
                <div class="modal-body" style="max-height: 374px;">
                    <button type="button" class="bootbox-close-button close" style="margin-top: -10px;">×</button>
                    <div class="bootbox-body">
                        <style type="text/css">
                            .listdetails {
                                width: 800px;
                            }
                        </style>
                        <div class="row" style="margin-top: -5px;">
                            <div class="col-md-12">
                                <label>价格配置</label>
                                <table class="table table-hover table-striped table-bordered">
                                    <table class="table table-hover table-striped table-bordered">
                                        <thead>
                                        <tr>
                                            <th style="width: 100px;">达标位置
                                            </th>
                                            <th>收费标准<span>(元/天)</span>
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>前<span class="red">10</span>名
                                            </td>
                                            <td>￥1.25
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>前<span class="red">20</span>名
                                            </td>
                                            <td>￥0.47
                                            </td>
                                        </tr>

                                        </tbody>
                                    </table>
                                </table>
                                <label>任务消费记录</label>
                                <div class="widget box">
                                    <div class="widget-header">
                                        <h4><i class="icon-reorder"></i>(17条记录)</h4>
                                    </div>
                                    <div class="widget-content">
                                        <table id="teacher_table" data-toggle="table" data-url="./data.php" data-method="post"
                                               data-query-params="queryParams"
                                               data-toolbar="#toolbar"
                                               data-pagination="true"
                                               data-page-size="5">
                                            <thead>
                                            <tr>
                                                <th data-field="id">序号</th>
                                                <th data-field="costDate">消费日期</th>
                                                <th data-field="ranking">排名</th>
                                                <th data-field="price">金额</th>
                                            </tr>
                                            </thead>
                                        </table>
                                        <div class="row">
                                            <div class="table-footer">
                                                <div class="col-md-6">
                                                    <div class="dataTables_paginate paging_bootstrap" style="float:left;">
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="dataTables_paginate paging_bootstrap">

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <input name="Bid" value="" type="hidden">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button data-bb-handler="cancel" type="button" class="btn wzgj-blue cancel">关闭</button>
                </div>
            </div>
        </div>
    </div>

<!--详情end-->
</@base.html>