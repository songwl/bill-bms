<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "关键词优化">

<script src="//rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script>
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/bill/billList.js"></script>

<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>优化管理</span> > <span>关键词排名</span>
    </div>
    <div class="nav_R right" id="divQx">
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
        <div>
            <span>&nbsp;<i class="fa fa-dollar"></i>&nbsp;调价</span>

        </div>
        <div class="Import">
            <span id="Import">&nbsp;<i class="fa fa-arrow-down"></i>&nbsp;导入</span>
            <ul class="ImportPrice">
                <li id="Sameprice">相同价导入</li>
                <li id="Differentprice">不同价导入</li>
            </ul>
        </div>

        <div>
            <span>&nbsp;<i class="fa fa-arrow-up"></i>&nbsp;导出</span>
        </div>
        <div class="search">
            <span>&nbsp;<i class="fa fa-search"></i>&nbsp;查询</span>
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
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;关键词排名</div>
                    <div class="Nav_Right">
                        <div>优化中</div>
                        <div>合作停</div>
                        <div>全部</div>


                    </div>
                    <div class="cls"></div>
                </div>
                <div id="toolbar" class="btn-group">
                    <select id="sel_exportoption" class="form-control">
                        <option value="">导出当前</option>
                        <option value="all">导出全部</option>
                        <option value="selected">导出选中</option>
                    </select>
                </div>
                <table id="myTable" class="table table-striped " style="width:100%">


                </table>
            </div>

        </div>
    </div>
</div>
<!--相同价导入-->
<div class="modal-dialog samepriceDiv">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="bootbox-close-button close">×</button>
            <h4 class="modal-title">相同价导入</h4>
        </div>
        <div class="modal-body" style="max-height: 574px; border-bottom: 1px solid #eee;">
            <div class="bootbox-body">
                <style type="text/css">
                    .modal-dialog {
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
                </form>
            </div>
        </div>


        <div class="modal-footer">
            <button data-bb-handler="success" type="button" class="btn wzgj-blue samepricecmt">确定</button><button data-bb-handler="cancel" type="button" class="btn wzgj-btn">取消</button>
        </div>
    </div>
</div>
<!--相同价导入end-->

<!--不同价导入-->
<div class="modal-dialog differentpriceDiv">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="bootbox-close-button close">×</button>
            <h4 class="modal-title">不同价导入</h4>
        </div>
        <div class="modal-body" style="max-height: 508px;">
            <div class="bootbox-body">
                <style type="text/css">
                    .modal-dialog {
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
                            <select id="searchengineid" name="searchengineid" class="form-control input-width-small" style="width:120px;">
                                    <@dict.showOptions dictKey="search" dictType="DICT" haveBlank="Y" />
                            </select>
                        </div>

                        <label class="col-md-2 control-label padding-right-0px">
                            前
                            <span class="red">N</span>名<span class="required">*</span>
                        </label>
                        <div class="col-md-2 padding-right-0px padding-left-5px" style="width: 190px">
                            <input name="rankend" class="form-control input-width-middle" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-1 control-label padding-right-0px">
                            关键词*
                            <span class="required"></span>
                        </label>
                        <div class="col-md-3 padding-left-5px">
                            <textarea name="keyword" rows="15" class="form-control"></textarea>
                        </div>
                        <label class="col-md-1 control-label padding-right-0px">
                            网址
                            <span class="required">*</span>
                        </label>
                        <div class="col-md-5 padding-left-5px">
                            <textarea name="url" rows="15" class="form-control"></textarea>
                        </div>
                        <label class="col-md-1 control-label padding-right-0px">
                            元/天
                            <span class="required">*</span>
                        </label>
                        <div class="col-md-1 padding-left-5px">
                            <textarea name="price" rows="15" class="form-control"></textarea>
                        </div>
                    </div>
                </form>


            </div>
        </div>

        <div class="modal-footer">
            <button data-bb-handler="success" type="button" class="btn wzgj-blue">确定</button><button data-bb-handler="cancel" type="button" class="btn wzgj-btn">取消</button>
        </div>
    </div>
</div>
<div id="pload" style="position:fixed;top:45%;left: 47%; z-index:1200;background:url('${ctx}/static/img/load3.gif') top center no-repeat;width:40px;height:40px;margin:auto auto;display: none;"></div>
<!--不同价导入end-->

</@base.html>