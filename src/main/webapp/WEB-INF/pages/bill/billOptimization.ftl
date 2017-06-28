<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "关键词优化">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/public/pace.js"></script>
<script src="${ctx}/static/js/bill/billOptimization.js"></script>
<div class="Navs" >
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>优化管理</span> > <span>关键词优化</span>
    </div>

    <div class="nav_R right" id="divQx">
        <#if bmsModel.user.hasRole("SUPER_ADMIN") ||bmsModel.user.hasRole("COMMISSIONER")>
        <div id="OptimizationUpdate">
            <span>&nbsp;<i class="fa fa-exchange"></i>&nbsp;优化调整</span>
        </div>
        <div id="OptimizationStart">
            <span>&nbsp;<i class="fa fa-play"></i>&nbsp;优化上线</span>
        </div>
        <div id="OptimizationStop">
            <span>&nbsp;<i class="fa fa-stop"></i>&nbsp;优化离线</span>
        </div>
      </#if>

        <div id="billCreateGroupClick">
            <span>&nbsp;<i class="fa fa-plus"></i>&nbsp;创建分组</span>
        </div>
        <div id="billToGroupClick">
            <span>&nbsp;<i class="fa fa-folder"></i>&nbsp;批设分组</span>
        </div>
        <div class="search">
            <span>&nbsp;<i class="fa fa-search"></i>&nbsp;查询</span>
        </div>
    </div>

    <div class="cls">
    </div>

        </div>
<div class="Navs2" style="width: 100%; height: 100px; line-height: 50px;border-left: 1px solid #d9d9d9;background: #eee;z-index: 99;display: none;">
    <div class="nav_R2 right col-md-11" >
        <input type="hidden" name="type" value="${way}" id="way">
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

        <div style="width:70px;">
            达标天数:
        </div>
        <input id="standardDays1" name="standardDays1" class="form-control" value="" style="width: 50px;" type="text"
               onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
               onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
        <div style="width: 15px;">-</div>
        <input id="standardDays2" name="standardDays2" class="form-control" value="" style="width: 50px;" type="text"
               onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
               onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
    </div>
    <div class="nav_R2 right col-md-11" >
            <span style="font-size:13px;text-align:center;cursor:pointer;font-weight:bold;margin-left: 10px;">
        分组:
        </span>
        <select style="height: 35px; border: 1px solid #aaaaaa;" id="selectGroupId">
            <option value="0">--请选择--</option>
            <#list billGroupList as item>
                <option value="${item.id}">${item.groupName}</option>
            </#list>
        </select>
        <div>
            初排:
        </div>
        <input id="firstRanking1" name="firstRanking1" class="form-control" value="" style="width: 50px;padding: 0 10px;" type="text">
        <div style="width: 15px;">-</div>
        <input id="firstRanking2" name="firstRanking2" class="form-control" value="" style="width: 50px;padding: 0 10px;" type="text">
        <div>
            新排:
        </div>
        <input id="newRanking1" name="newRanking1" class="form-control" value="" style="width: 50px;padding: 0 10px;" type="text">
        <div style="width: 15px;">-</div>
        <input id="newRanking2" name="newRanking2" class="form-control" value="" style="width: 50px;padding: 0 10px;" type="text">
        <div>
            新变:
        </div>
        <input id="newchange1" name="newchange1" class="form-control" value="" style="width: 50px;padding: 0 10px;" type="text">
        <div style="width: 15px;">-</div>
        <input id="newchange2" name="newchange2" class="form-control" value="" style="width: 50px;padding: 0 10px;" type="text">
        <div style="width: 70px;">
            增加时间:
        </div>
        <input id="addTime1" name="addTime1" class="form-control" value="" style="width: 150px;padding: 0 10px;" type="text" placeholder="格式：2017-1-1">
        <div style="width: 15px;">-</div>
        <input id="addTime2" name="addTime2" class="form-control" value="" style="width: 150px;padding: 0 10px;" type="text" placeholder="格式：2017-1-1">

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
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;关键词优化(<span id="length"></span>)</div>
                    <div class="Nav_Right">
                        <div id="continue">优化中</div>
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
                            <input  type="text" value="" id="OptimizationUpdateNum">
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
<div class="pload" style="position:absolute;top:45%;left: 50%; z-index:2200;background:url('${ctx}/static/img/load3.gif') top center no-repeat;width:40px;height:40px;margin:auto auto;display: none;"></div>
<div class="modal-backdrop in" style="display: none">
</div>


<div id="billCreateGroupDiv" style="display: none;">
    <div class="modal-body">
        <div class="form-group">
            <table id="groupTable" class="table table-striped  table-condensed table-responsive"
                   style="width:100%;font-size: 13px;font-family: " 微软雅黑>
            </table>
        </div>
        <div style="margin:0 auto;text-align: center">
            <button id="createGroup" class="btn btn-success form-control " style="width: 30%;">
                <span class="glyphicon glyphicon-send">&nbsp;</span><span>添加分组</span></button>
        </div>

    </div>
</div>


<div id="billToGroupDiv" style="display: none;">
    <div class="modal-body">
        <div class="form-group">
            <table id="billToGroupTable" class="table table-striped  table-condensed table-responsive"
                   style="width:100%;font-size: 13px;font-family: " 微软雅黑>
            </table>
        </div>
        <div style="margin:0 auto;text-align: center">
            <button id="toGroupCmt" class="btn btn-success form-control " style="width: 30%;">
                <span class="glyphicon glyphicon-send">&nbsp;</span><span>确定</span></button>
        </div>

    </div>
</div>
</@base.html>