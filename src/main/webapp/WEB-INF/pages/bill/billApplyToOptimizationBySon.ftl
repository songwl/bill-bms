<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "停单待通过">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/public/pace.js"></script>
<script src="${ctx}/static/js/bill/billApplyToOptimizationBySon.js"></script>
<div class="Navs" >
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>优化管理</span> > <span>停单待通过</span>
    </div>
    <div class="nav_R right" id="divQx">
        <div id="pass">
            <span>&nbsp;<i class="fa fa-stop"></i>&nbsp;停单待通过</span>
        </div>
        <div id="pass1">
            <span>&nbsp;<i class="fa fa-play"></i>&nbsp;优化待通过</span>
        </div>
    </div>
    <div class="cls">
    </div>
</div>
<div class="Navs2" style="width: 100%; height: 100px; line-height: 50px;border-left: 1px solid #d9d9d9;background: #eee;z-index: 99;display: none;">
    <div class="nav_R2 right col-md-11" >
        <div>
            网址:
        </div>
        <input id="website" name="userName" class="form-control" value="" style="width: 150px;padding: 0 10px;" type="text">
        <div>
            关键词:
        </div>
        <input id="keywords" name="keywords" class="form-control" value="" style="width: 150px;padding: 0 10px;" type="text">
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

    </div>

    <div class="cls">
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary" id="list-panel">
            <div class="panel-body">
                <div class="panel-nav">
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;优化待通过</div>
                    <div class="Nav_Right">
                        <div id="continue">审核列表</div>
                        <!--
                        <div id="stop">合作停</div>
                        <div id="all">全部</div>
                        -->
                    </div>
                    <div class="cls"></div>
                </div>

                <table id="myTable" class="table table-striped  table-condensed table-responsive" style="width:100%;font-size: 13px;font-family: "微软雅黑">
                </table>
            </div>
        </div>
    </div>
</div>
<!--优化调整-->

<!--优化调整end-->
</@base.html>