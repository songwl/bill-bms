<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "待审核订单">

<script src="//rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script>
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/bill/pendingAuditView.js"></script>

<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>优化管理</span> > <span>待审核订单</span>
    </div>
    <div class="nav_R right" id="divQx">

      <#--  <div class="Import">
            <span id="Import">&nbsp;<i class="fa fa-arrow-down"></i>&nbsp;导出</span>

        </div>-->
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

                <table id="myTable" class="table table-striped  table-condensed table-responsive" style="width:100%">
                </table>
            </div>

        </div>
    </div>
</div>
<div class="modal-backdrop in" style="display: none">
</div>


</@base.html>