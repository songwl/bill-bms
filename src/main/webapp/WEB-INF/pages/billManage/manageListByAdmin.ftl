<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "数据统计">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/billManage/manageListByAdmin.js"></script>
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>数据统计</span> > <span>订单管理</span>
    </div>
    <div class="nav_R right" id="divQx">
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
            网址:
        </div>
        <input id="website" name="userName" class="form-control" value="" style="width: 150px;" type="text">
        <div>
            客户:
        </div>
        <select style="height: 35px;border: 1px solid #aaaaaa;" id="searchUserName">
             <option>--请选择--</option>
             <#list userList as user>
                 <option value="${user.id}">${user.userName}</option>
             </#list>
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
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;订单管理</div>
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

</@base.html>