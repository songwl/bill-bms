<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "关键词优化">

<script src="//rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script>
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/public/pace.js"></script>
<script src="${ctx}/static/js/bill/billFeedback.js"></script>
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>优化管理</span> > <span>订单详情与反馈</span>
    </div>
    <div class="nav_R right" id="divQx">
        <div id="feedback">
            <span>&nbsp;<i class="fa fa-play"></i>&nbsp;我要反馈</span>
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
            网址:
        </div>
        <input id="website" name="userName" class="form-control" value="" style="width: 150px;" type="text">
        <div>
            关键词:
        </div>
        <input id="keywords" name="keywords" class="form-control" value="" style="width: 150px;" type="text">

        <input type="hidden" value="2" id="searchStatechange">
        <span id="searchButton">查询</span>
    </div>

    <div class="cls">
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary" id="list-panel">
            <div class="panel-body">
                <div class="panel-nav">
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;基本信息</div>
                    <div class="Nav_Right">
                    </div>
                    <div class="cls"></div>
                </div>
                <div class="mytask_box">

                    <div class="info_mk">
                        <ul class="u1">

                            <li>
                                <div class="d1"><p>网站</p></div>
                                <div class="d2"><p>www.hunsha.com</p>
                                    <a class="yhz" href="javascript:void(0);">[优化中]</a></div>
                                <div class="d3"><p>搜索引擎</p></div>
                                <div class="d4">
                                    百度，搜狗，360
                                </div>
                            </li>
                            <li>
                                <div class="d1"><p>接单时间</p></div>
                                <div class="d2"><p>2017年04月06日</p></div>
                                <div class="d3"><p>达标天数</p></div>
                                <div class="d4"><p><em>0</em>天 </p></div>
                            </li>
                            <li class="gjcdv">
                                <div class="gj1"><p>关键词</p></div>
                                <div class="gj2"> 上海婚纱，上海婚纱公司....</div>
                            </li>

                        </ul>
                    </div>

                </div>

            </div>

        </div>
    </div>

    <div class="col-md-12">
        <div class="panel panel-primary" id="list-panel">
            <div class="panel-body">
                <div class="panel-nav">
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;问题反馈</div>

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
<!--问题反馈-->
<div class="bootbox modal in feedbackDiv" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="bootbox-close-button close">×</button>
                <h4 class="modal-title">问题反馈</h4>
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
                            <label class="col-md-2 control-label">
                                标题<span class="required">* </span>
                            </label>
                            <div class="col-md-10">
                                <input name="userName" class="form-control input-width-large" type="text">
                            </div>
                            <div class="pdlogid"></div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                内容<span class="required">*</span>
                            </label>
                            <div class="col-md-10">
                                <textarea style="height: 378px;width: 380px;resize: none;border: 1px solid #ccc;padding: 5px;"></textarea>
                            </div>
                        </div>
                    </form>
                    <input type="hidden" value="1" id="addMemberId">
                </div>
            </div>
            <div class="modal-footer">
                <button data-bb-handler="success" type="button" class="btn wzgj-blue addOperatorcmt">提交</button>
                <button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
            </div>
        </div>
    </div>
</div>
<!--问题反馈end-->
</@base.html>