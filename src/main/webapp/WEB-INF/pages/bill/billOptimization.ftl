<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "关键词优化">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/public/pace.js"></script>
<script src="${ctx}/static/js/bill/billOptimization.js"></script>
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>优化管理</span> > <span>关键词优化</span>
    </div>
    <div class="nav_R right" id="divQx">
        <div id="OptimizationUpdate">
            <span>&nbsp;<i class="fa fa-apple"></i>&nbsp;优化调整</span>
        </div>
        <div id="OptimizationUpdate">
            <span>&nbsp;<i class="fa fa-stop"></i>&nbsp;优化停止</span>
        </div>
        <div class="search">
            <span>&nbsp;<i class="fa fa-search"></i>&nbsp;查询</span>
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
        <span style="font-size:13px;text-align:center;cursor:pointer;font-weight:bold;margin-left: 10px;">
            今日达标:
        </span>
        <select style="height: 35px;border: 1px solid #aaaaaa;" id="searchStandard">
            <option>--请选择--</option>
            <option value="1">是</option>
            <option value="0">否</option>
        </select>
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
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;关键词优化</div>
                    <div class="Nav_Right">
                        <div id="continue">优化中</div>
                        <!--
                        <div id="stop">合作停</div>
                        <div id="all">全部</div>
                        -->
                    </div>
                    <div class="cls"></div>
                </div>

                <table id="myTable" class="table table-striped  table-condensed table-responsive" style="width:100%">
                </table>
            </div>
        </div>
    </div>
</div>


<!--优化调整-->
<div class="bootbox modal in OptimizationUpdateDiv" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="bootbox-close-button close">×</button>
                <h4 class="modal-title">优化调整</h4>
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
                            <div style="margin-left:15px;">优化指数：</div>
                            <div style="margin-left:10px;margin-top: 10px;">
                            <select style="width: 80px;" id="OptimizationUpdateSelect">
                                <option>--请选择--</option>
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                                <option>4</option>
                                <option>5</option>
                                <option>6</option>
                                <option>7</option>
                                <option>8</option>
                                <option>9</option>
                                <option>10</option>
                            </select>
                            </div>
                        </div>

                    </form>

                </div>
            </div>
            <div class="modal-footer">
                <button data-bb-handler="success" type="button" class="btn wzgj-blue OptimizationUpdatecmt">确定</button>
                <button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
            </div>
        </div>
    </div>
</div>
<!--优化调整end-->
</@base.html>