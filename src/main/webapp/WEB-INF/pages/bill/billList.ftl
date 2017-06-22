<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "关键词优化">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/bill/billList.js"></script>
<script src="${ctx}/static/js/bill/billListchangePrice.js"></script>
<script src="${ctx}/static/js/bill/billListsamePrice.js"></script>
<script src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
<script src="//rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script>


<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>优化管理</span> > <span>关键词排名</span>
    </div>
    <div class="nav_R right" id="divQx">

        <#if  bmsModel.user.hasRole("SUPER_ADMIN")||bmsModel.user.hasRole("COMMISSIONER")> <#--如果是优化方-->
            <div id="optimizationStart">
                <span>&nbsp;<i class="fa fa-play"></i>&nbsp;优化启动</span>
            </div>
            <div id="optimizationStop">
                <span>&nbsp;<i class="fa fa-stop"></i>&nbsp;合作停止</span>
            </div>
            <div id="billDelete">
                <span>&nbsp;<i class="fa fa-trash"></i>&nbsp;删除</span>
            </div>
            <div id="updatePrice">
                <span>&nbsp;<i class="fa fa-cny"></i>&nbsp;调价</span>
            </div>

        </#if>
        <#if  bmsModel.user.hasRole("SUPER_ADMIN")>
            <div id="billChangeClick">
                <span>&nbsp;<i class="fa fa-users"></i>&nbsp;订单调换</span>
            </div>
        </#if>
        <#if  bmsModel.user.hasRole("DISTRIBUTOR")||bmsModel.user.hasRole("AGENT")>
            <div id="applyStopBill">
                <span>&nbsp;<i class="fa fa-trash"></i>&nbsp;申请停单</span>
            </div>
            <div id="applyToOptimization">
                <span>&nbsp;<i class="fa fa-magic"></i>&nbsp;申请优化</span>
            </div>
          <#--  <div id="billToChange">
                <span>&nbsp;<i class="fa fa-random"></i>&nbsp;切换订单</span>
            </div>-->
        </#if>
        <#if bmsModel.user.hasRole("CUSTOMER")>
            <div id="addBillByKehu">
                <span>&nbsp;<i class="fa fa-arrow-down"></i>&nbsp;导入</span>
            </div>
        </#if>

        <div class="search">
            <span>&nbsp;<i class="fa fa-search"></i>&nbsp;查询</span>
        </div>

    </div>
    <div class="cls">
    </div>
</div>
<div class="Navs2" style="height: 105px;">
    <div class="nav_R2 right col-md-11">
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
        <#if  bmsModel.user.hasRole("SUPER_ADMIN")||bmsModel.user.hasRole("ADMIN")||bmsModel.user.hasRole("COMMISSIONER")||bmsModel.user.hasRole("DISTRIBUTOR")||bmsModel.user.hasRole("AGENT")>
            <span style="font-size:13px;text-align:center;cursor:pointer;font-weight:bold;margin-left: 10px;">
        客户:
        </span>

            <select style="height: 35px;border: 1px solid #aaaaaa;" id="searchUserName">
                <option>--请选择--</option>
                <#list userList as user>
                    <option value="${user.id}">${user.userName}</option>
                </#list>
            </select>
        </#if>
        <span style="font-size:13px;text-align:center;cursor:pointer;font-weight:bold;margin-left: 10px;">
            今日达标:
        </span>
        <select style="height: 35px;border: 1px solid #aaaaaa;" id="searchStandard">
            <option>--请选择--</option>
            <option value="1">是</option>
            <option value="0">否</option>
        </select>
        <span style="font-size:13px;text-align:center;cursor:pointer;font-weight:bold;margin-left: 10px;" id="state">
            状态:
        </span>
        <select style="height: 35px;border: none;border: 1px solid #aaaaaa;" id="searchState">
            <option value="1">--请选择--</option>
            <option value="2">优化中</option>
            <option value="3">合作停</option>
        </select>
        <input type="hidden" value="2" id="searchStatechange">

    </div>
    <div class="nav_R2 right">
        <div style="width:70px;text-align: left;">
            达标天数
        </div>
        <input id="standardDays" name="standardDays" class="form-control" value="" style="width: 50px;" type="text"
               onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
               onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
        <div style="width:80px;">
            增加时间:
        </div>
        <input onFocus="WdatePicker({lang:'zh-tw',readOnly:true})" id="createTime"/>
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
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;关键词排名(<span id="length"></span>)
                    </div>
                    <div class="Nav_Right">
                        <div id="continue">优化中</div>
                        <div id="stop">合作停</div>
                        <div id="all">全部</div>


                    </div>
                    <div class="cls"></div>
                </div>
                <table id="myTable" class="table table-striped  table-condensed table-responsive"
                       style="width:100%;font-size: 13px;font-family: " 微软雅黑
                ">
                </table>
            </div>

        </div>
    </div>
</div>


<div class="bootbox modal in billExamineDiv" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false">
    <div class="modal-dialog" style="width: 990px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="bootbox-close-button close">×</button>
                <h4 class="modal-title">调价</h4>
            </div>
            <div class="modal-body" style="max-height: 574px;">
                <div class="bootbox-body">
                    <style type="text/css">
                        .modal-dialog {
                            width: 990px;
                        }
                    </style>
                    <div>
                        <div class="alert alert-info fade in">
                            录入价格的时候请按对应的顺序录入，请谨慎录入。顺序（排名1，价格1，排名2，价格2,......）
                        </div>
                        <div class="form-group" style="height:30px;">
                            <div class="form-group" style="margin-top:30px;">
                                <label class="col-md-1 control-label padding-right-0px padding-left-0px"
                                       style="width: 82px;">
                                    前
                                    <span class="red">N</span>名<span class="required">*</span>
                                </label>
                                <div class="" style="width: 35px;float: left;">
                                    <input name="updaterankend" style="width: 35px;">
                                </div>

                                <label class="col-md-1 control-label padding-right-0px padding-left-0px"
                                       style="width: 82px;">
                                    <span>元/天</span><span class="required">*</span>
                                </label>
                                <div class="" style="width: 35px;float: left;">
                                    <input name="updateprice" style="width: 35px;">
                                </div>
                                <label class="col-md-1 control-label padding-right-0px padding-left-0px"
                                       style="width: 78px;">
                                    前
                                    <span class="red">N</span>名
                                </label>
                                <div class="" style="width: 35px;float: left;">
                                    <input name="updaterankend1" style="width: 35px;">
                                </div>

                                <label class="col-md-1 control-label padding-right-0px padding-left-0px"
                                       style="width: 68px;">
                                    <span>元/天</span>
                                </label>
                                <div class="" style="width: 35px;float: left;">
                                    <input name="updateprice1" style="width: 35px;">
                                </div>
                                <label class="col-md-1 control-label padding-right-0px padding-left-0px"
                                       style="width: 78px;">
                                    前
                                    <span class="red">N</span>名
                                </label>
                                <div class="" style="width: 35px;float: left;">
                                    <input name="updaterankend2" style="width: 35px;">
                                </div>

                                <label class="col-md-1 control-label padding-right-0px padding-left-0px"
                                       style="width: 68px;">
                                    <span>元/天</span><span class="required"></span>
                                </label>
                                <div class="" style="width: 35px;float: left;">
                                    <input name="updateprice2" style="width: 35px;">
                                </div>
                                <label class="col-md-1 control-label padding-right-0px padding-left-0px"
                                       style="width: 78px;">
                                    前
                                    <span class="red">N</span>名
                                </label>
                                <div class="" style="width: 35px;float: left;">
                                    <input name="updaterankend3" style="width: 35px;">
                                </div>

                                <label class="col-md-1 control-label padding-right-0px padding-left-0px"
                                       style="width:68px;">
                                    <span>元/天</span>
                                </label>
                                <div class="" style="width: 35px;float: left;">
                                    <input name="updateprice3" style="width: 35px;">
                                </div>
                            </div>

                        </div>
                    </div>

                </div>

            </div>
            <div class="modal-footer" style="height:70px;">
                <button data-bb-handler="success" type="button" class="btn wzgj-blue updatePricecmt"
                        style="background: #09C;color: #fff;">确定
                </button>
                <button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
            </div>
        </div>
    </div>
</div>


<!--详情-->
<div class="bootbox modal in" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false"
     id="billCostDetail">
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
                                <thead>
                                <tr>
                                    <th style="width: 100px;">达标位置
                                    </th>
                                    <th>收费标准<span>(元/天)</span>
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="detailTable">


                                </tbody>
                            </table>

                            <label>任务消费记录</label>
                            <div class="widget box">
                                <div class="widget-header">

                                </div>
                                <div class="widget-content">
                                    <table id="pricetable"
                                           class="table table-striped  table-condensed table-responsive">
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


<!--订单切换操作员-->
<div class="bootbox modal in billChangeDiv" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="bootbox-close-button close">×</button>
                <h4 class="modal-title">订单调换</h4>
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
                            <div style="margin-left:15px;" class="Amount">操作员列表：</div>
                            <div style="margin-left:10px;margin-top: 10px;">
                                <select id="selectlist"></select>
                            </div>
                        </div>
                        <input type="hidden" value="" id="customerId">
                    </form>

                </div>
            </div>
            <div class="modal-footer">
                <button data-bb-handler="success" type="button" class="btn wzgj-blue billChangecmt">确定</button>
                <button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
            </div>
        </div>
    </div>
</div>


<!--订单切换客户-->
<div class="bootbox modal in billChangeToKeHuDiv" tabindex="-1" role="dialog" style="display: none;"
     aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="bootbox-close-button close">×</button>
                <h4 class="modal-title">订单调换</h4>
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
                            <div style="margin-left:15px;" class="Amount">客户列表：</div>
                            <div style="margin-left:10px;margin-top: 10px;">
                                <select id="selectKeHulist">
                                    <#list userList as user>
                                        <option value="${user.id}">${user.userName}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
            <div class="modal-footer">
                <button data-bb-handler="success" type="button" class="btn wzgj-blue billChangeToKeHucmt"
                        style="background: #09c;color: #fff;">确定
                </button>
                <button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
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
                <h4 class="modal-title">订单导入</h4>
            </div>
            <div class="modal-body" style="max-height: 574px; border-bottom: 1px solid #eee;">
                <div class="bootbox-body">
                    <style type="text/css">
                        .dv1 {
                            width: 960px;
                        }
                    </style>
                    <form class="form-horizontal row-border" id="edt-form" action="#" novalidate="novalidate">
                        <div class="form-group hidden">
                            <div class="progress progress-striped active">
                                <span id="success_span" data-count="0" class="progress-bar progress-bar-success"
                                      style="width: 0%; text-align: center; color: black;" title=""></span>
                                <span id="failed_span" data-count="0" class="progress-bar progress-bar-warning"
                                      style="width: 0%; text-align: center; color: black;" title=""></span>
                            </div>
                        </div>
                        <div class="form-group" style="border-bottom: 1px solid #eee;padding-bottom: 3px">
                            <label class="col-md-2 control-label padding-right-0px">
                                搜索引擎
                                <span class="required">*</span>
                            </label>
                            <div class="col-md-2 padding-right-0px padding-left-5px input-width-small">
                                <select id="searchengineid" name="searchengineid"
                                        class="form-control input-width-small">
                                    <@dict.showOptions dictKey="search" dictType="DICT" haveBlank="Y" />
                                </select>
                            </div>


                        </div>

                        <div class="form-group">
                            <label class="col-md-1 control-label padding-right-0px">
                                关键词*
                            </label>
                            <div class="col-md-4 padding-left-5px">
                                <textarea name="keyword" id="keyword" rows="15" class="form-control"
                                          style="width: 296px; height: 297px;resize: none;"></textarea>
                            </div>

                            <label class="col-md-1 control-label padding-right-0px">
                                网址
                                <span class="required">*</span>
                            </label>
                            <div class="col-md-6 padding-left-5px">
                                <textarea name="url" id="url" rows="15" class="form-control"
                                          style="height: 297px;resize: none;"></textarea>
                            </div>
                        </div>

                    </form>
                </div>
            </div>


            <div class="modal-footer">
                <button data-bb-handler="success" type="button" class="btn wzgj-blue addBillByKehuCmt"
                        style="background: #09C;color: #fff;">确定
                </button>
                <button data-bb-handler="cancel" type="button" class="btn wzgj-btn cancel">取消</button>
            </div>
        </div>
    </div>
</div>
<!--相同价导入end-->
<#--<a href="#" onclick="$('body').animate( {scrollTop: 0}, 500);" style="position: fixed;right: 18px;bottom: 20px;border: 1px solid;color: #ddd;"><i class="glyphicon glyphicon-arrow-up"></i></a>-->
<div class="fixed-btn go-top" onclick=" $('body,html').animate({scrollTop:0},500);"
     style="display:none;position: fixed;right: 0.8%;bottom: 5%;width: 40px;border: 1px solid #eee;background-color: white;font-size: 24px;z-index: 1040;background:#7a7a7a;">
    <a href="javascript:void(0)" title="返回顶部"
       style="display: inline-block;width: 40px;height: 40px;text-align: center;color: #64854c;">
        <i class="fa fa-angle-up" style="color: #fff;"></i></a>
</div>
<div class="pload"
     style="position:absolute;top:45%;left: 50%; z-index:2200;background:url('${ctx}/static/img/load3.gif') top center no-repeat;width:40px;height:40px;margin:auto auto;display: none;"></div>
<div id="offerSetUp" style="display: none;">

    <div class="modal-body">
        <div class="form-group">
            <div>
                <span>关键词：</span>
                <input type="text" class="form-control setkeyword" id="keywordUpdate" placeholder="关键词"   onkeyup="this.value=this.value.replace(/\s+/g,'')"/>
            </div>

            <br>
            <div>
                <span>网址：</span>
                <input type="text" class="form-control setkeyword" id="websiteUpdate" placeholder="网址"   onkeyup="this.value=this.value.replace(/\s+/g,'')"/>
            </div>
            <input type="hidden" value="" id="billIdInput"  />
        </div>
        <button id="confirmUpdateBill" class="btn btn-success form-control"><span class="glyphicon glyphicon-send">&nbsp;</span><span>确认</span></button>
    </div>
</div>
<#-- <#if  bmsModel.user.hasRole("SUPER_ADMIN")||bmsModel.user.hasRole("COMMISSIONER")>
<form id= "uploadForm" enctype="multipart/form-data">
   <p >上传文件： <input type="file" name="file"/></p>
   <input type="button" value="上传" onclick="doUpload()" />
</form>
<script>
   function doUpload() {
       var formData = new FormData($( "#uploadForm" )[0]);
       $.ajax({
           url:CTX+ '/order/uploadPrice' ,
           type: 'POST',
           data: formData,
           async: false,
           cache: false,
           contentType: false,
           processData: false,
           beforeSend: function () {
               $(".pload").show();

               $(".modal-backdrop").show();
           },
           success: function (result) {
               $(".pload").hide();
               $(".modal-backdrop").hide();
               alert(result.message);

           }
       });
   }
</script>
</#if>-->
<!--详情end-->
</@base.html>