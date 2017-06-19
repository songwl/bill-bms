<#import "/base/base.ftl" as base>
<#import "/base/layout.ftl" as layout>

<#macro bmsheadcontent>
    <#assign bms_head_content>
        <#nested/>
    </#assign>
</#macro>
<#macro bmsheadjscontent>
    <#assign bms_head_js_content>
        <#nested/>
    </#assign>
</#macro>

<#macro bmsjscontent>
    <#assign bms_js_content>
        <#nested/>
    </#assign>
</#macro>

<#macro bmshtml bmsModel title>

    <@base.headcontent>
    <!--icheck-->
    <link href="${ctx}/static/css/public/pace.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/css/public/common.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/js/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/js/assets/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/js/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
    <link rel="shortcut icon" href="${ctx}/static/js/assets/img/favicon.ico"/>
    <link href="${ctx}/static/css/public/back.css" rel="stylesheet">
    <link href="${ctx}/static/js/layer/skin/default/layer.css" rel="stylesheet">
    <script src="${ctx}/static/js/layer/layer.js"></script>
    ${bms_head_content!""}
    </@base.headcontent>

    <@base.headjscontent>
    ${bms_head_js_content!""}
    </@base.headjscontent>

    <@base.jscontent>
    <script src="${ctx}/static/js/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
            type="text/javascript"></script>
    <script src="${ctx}/static/js/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/assets/scripts/app.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/js/app/js/index.js"></script>
    <script src="${ctx}/static/js/home/bms.js"></script>
    ${bms_js_content!""}
    </@base.jscontent>

    <@base.html title "page-header-fixed">

    <!--顶部导航栏     开始 -->
    <header class="header navbar navbar-fixed-top" role="banner">
        <div class="container">
            <div>
                <a class="navbar-brand">
                    <strong>管理后台</strong>

                </a>
                <ul style="border: none;float: left;display: inline-block;margin-left: 150px;margin-top: 13px;">
                    <li class="sidebar-toggler-wrapper">
                        <div class="sidebar-toggler hidden-phone fa fa-bars">
                        </div>
                    </li>
                </ul>
            </div>


            <ul class="nav navbar-right" style="height:50px;">
                <li class="dropdown user" style="height:48px;">
                    <a class="dropdown-toggle" data-toggle="dropdown" id="clickUser">
                        <i class="fa fa-user"></i>
                        <span class="username">${bmsModel.user.userName}</span>
                        <i class="icon-caret-down small"></i>
                    </a>
                    <ul class="dropdown-menu userDetails">

                    <#--  <li clas="updateUser">
                          <a class="dlg-user-edit-info">
                              <i class="icon-user"></i> 基本资料
                          </a>
                      </li>-->

                        <li class="updatePwd">
                            <a class="dlg-user-edit-pwd">
                                <i class="icon-pencil"></i> 修改密码
                            </a>
                        </li>
                        <!--
                        <li>
                            <a>
                                <i class="icon-tasks"></i> 任务列表
                            </a>
                        </li>-->
                        <li class="divider"></li>
                        <li>
                            <a href="${ctx}/logout">
                                <i class="icon-key"></i> 退出
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        <#--<ul class="nav navbar-right"  style="height:50px;">
            <li class="dropdown user" style="height:48px;" >
                <a class="dropdown-toggle" data-toggle="dropdown"  id="clickMessage">
                    <i class="fa fa-bell"></i>
                    <span class="username">消息中心</span>
                    <i class="icon-caret-down small"></i>
                </a>
            </li>
            <ul class="dropdown-menu messageShow" style="margin-top: 2px!important;position: absolute!important;	float: left!important;	background-color: #fff!important;
                border: 1px solid #ccc!important;
                border: 1px solid rgba(0, 0, 0, 0.15)!important;
                -webkit-box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175)!important;
                -moz-box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175)!important;
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175)!important;
                right: 100px;
                left: auto;
                display: none;">
                <li>
                    <a class="dlg-user-edit-info">
                        <i class="icon-user"></i> 新的订单
                    </a>
                </li>
               <!-- <li>
                    <a class="dlg-user-edit-pwd">
                        <i class="icon-pencil"></i> 订单反馈
                    </a>
                </li>
                &ndash;&gt;
            </ul>
        </ul>-->
        </div>
    </header>
    <div class="clearfix">
    </div>
    <div class="page-container">
        <div class="page-sidebar-wrapper" id="menuShow">
            <div class="page-sidebar navbar-collapse collapse" style="border-right:1px solid #eee;background: #fff; ">
                <ul class="page-sidebar-menu" id="page-sidebar-menu" style="background: #293038; ">
                    <li class="sidebar-toggler-wrapper">
                        <div class="sidebar-toggler hidden-phone">
                        </div>
                    </li>
                    <li class="start active" style="border-bottom: 1px solid #3d3d3d;" onclick="location.reload();">
                        <a href="#">
                            <i class="fa fa-home"></i><span class="title"> 首页 </span><span class="selected"></span>
                        </a>
                    </li>
                    <#if bmsModel.user.hasRole("SUPER_ADMIN") ||bmsModel.user.hasRole("ADMIN")||bmsModel.user.hasRole("COMMISSIONER")>
                        <li class="current" style="border-bottom: 1px solid #3d3d3d;">
                            <a href="javascript:;">
                                <i class="fa fa-star-o"></i><span class="title"> 优化管理 </span><span
                                    class="arrow "></span>
                            </a>
                            <ul class="sub-menu" style="background: #293038;">
                                <li>
                                    <a href="${ctx}/order/list?way=2">
                                        关键词排名
                                    </a>
                                </li>
                                <#if bmsModel.user.hasRole("SUPER_ADMIN") ||bmsModel.user.hasRole("COMMISSIONER")>
                                    <li>
                                        <a href="${ctx}/order/billOptimization">
                                            关键词优化
                                        </a>
                                    </li>
                                </#if>
                                <#if bmsModel.user.hasRole("SUPER_ADMIN") ||bmsModel.user.hasRole("ADMIN")>
                                    <li>
                                        <a href="${ctx}/order/pendingAudit">
                                            待审核订单
                                        </a>
                                    </li>
                                </#if>
                                <#if bmsModel.user.hasRole("SUPER_ADMIN") >
                                    <li>
                                        <a href="${ctx}/order/billApplyStopPass">
                                            停单待通过
                                        </a>
                                    </li>
                                </#if>
                            </ul>
                        </li>
                    </#if>
                    <#if bmsModel.user.hasRole("DISTRIBUTOR")>
                        <li class="" style="border-bottom: 1px solid #3d3d3d;">
                            <a href="javascript:;">
                                <i class="fa fa-star-o"></i><span class="title"> 优化方管理 </span><span
                                    class="arrow "></span>
                            </a>
                            <ul class="sub-menu" style="background: #293038;">
                                <li>
                                    <a href="${ctx}/order/list?way=1">
                                        关键词排名
                                    </a>
                                </li>
                                <li>
                                    <a href="${ctx}/order/billOptimizationSettlement">
                                        优化方结算
                                    </a>
                                </li>

                                <li>
                                    <a href="${ctx}/order/billApplyStop">
                                        停单待审核
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </#if>
                    <#if bmsModel.user.dailiRole=1>
                        <li class="" style="border-bottom: 1px solid #3d3d3d;">
                            <a href="javascript:;">
                                <i class="fa fa-star-o"></i><span class="title"> 代理商管理 </span><span
                                    class="arrow "></span>
                            </a>
                            <ul class="sub-menu" style="background: #293038;">
                                <li>
                                    <a href="${ctx}/order/list?way=2">
                                        关键词排名
                                    </a>
                                </li>
                                <li>
                                    <a href="${ctx}/order/pendingAudit">
                                        订单审核
                                    </a>
                                </li>
                                <li>
                                    <a href="${ctx}/order/billApplyStopPass">
                                        停单待通过
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </#if>
                    <#if bmsModel.user.hasRole("DISTRIBUTOR")>
                        <li class="" style="border-bottom: 1px solid #3d3d3d;">
                            <a href="javascript:;">
                                <i class="fa fa-star-o"></i><span class="title"> 客户方管理 </span><span
                                    class="arrow "></span>
                            </a>
                            <ul class="sub-menu" style="background: #293038;">
                                <li>
                                    <a href="${ctx}/order/billCustomer">
                                        关键词排名
                                    </a>
                                </li>
                                <li>
                                    <a href="${ctx}/order/billClientSideSettlement">
                                        客户方结算
                                    </a>
                                </li>
                                <li>
                                    <a href="${ctx}/order/pendingAuditView">
                                        待审核订单
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </#if>
                    <#if bmsModel.user.hasRole("AGENT")>
                        <li class="" style="border-bottom: 1px solid #3d3d3d;">
                            <a href="javascript:;">
                                <i class="fa fa-star-o"></i><span class="title"> 渠道商管理 </span><span
                                    class="arrow "></span>
                            </a>
                            <ul class="sub-menu" style="background: #293038;">
                                <li>
                                    <a href="${ctx}/order/list?way=1">
                                        关键词排名
                                    </a>
                                </li>
                                <li>
                                    <a href="${ctx}/order/billApplyStop">
                                        停单待审核
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </#if>
                    <#if bmsModel.user.hasRole("AGENT")>
                        <li class="" style="border-bottom: 1px solid #3d3d3d;">
                            <a href="javascript:;">
                                <i class="fa fa-star-o"></i><span class="title"> 客户管理 </span><span
                                    class="arrow "></span>
                            </a>
                            <ul class="sub-menu" style="background: #293038;">
                                <li>
                                    <a href="${ctx}/order/billCustomer">
                                        关键词排名
                                    </a>
                                </li>
                                <li>
                                    <a href="${ctx}/order/pendingAuditView">
                                        待审核订单
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </#if>
                    <#if bmsModel.user.hasRole("CUSTOMER")>
                        <li class="" style="border-bottom: 1px solid #3d3d3d;">
                            <a href="javascript:;">
                                <i class="fa fa-star-o"></i><span class="title"> 优化管理 </span><span
                                    class="arrow "></span>
                            </a>
                            <ul class="sub-menu" style="background: #293038;">
                                <li>
                                    <a href="${ctx}/order/list?way=1">
                                        关键词排名
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </#if>

                    <#if bmsModel.user.hasRole("SUPER_ADMIN") >
                        <li class="" style="border-bottom: 1px solid #3d3d3d;">
                            <a href="javascript:;">
                                <i class="fa fa-paperclip"></i><span class="title"> 操作员管理 </span><span
                                    class="arrow "></span>
                            </a>
                            <ul class="sub-menu" style="background: #293038;">
                                <li>
                                    <a href="${ctx}/operator/list">
                                        操作员列表
                                    </a>
                                </li>

                            </ul>
                        </li>
                    </#if>

                    <#if bmsModel.user.hasRole("SUPER_ADMIN")||bmsModel.user.hasRole("COMMISSIONER")||bmsModel.user.hasRole("DISTRIBUTOR")||bmsModel.user.hasRole("AGENT")>
                        <li class="" style="border-bottom: 1px solid #3d3d3d;">
                            <a href="javascript:;">
                                <i class="fa fa-magic"></i><span class="title"> 资金管理 </span><span class="arrow "></span>
                            </a>
                            <ul class="sub-menu" style="background: #293038;">
                                <li>
                                    <a href="${ctx}/customer/customerList">
                                        客户列表
                                    </a>
                                </li>
                                <li>
                                    <a href="${ctx}/customer/fundAccount">
                                        资金明细
                                    </a>
                                </li>
                                <#if bmsModel.user.hasRole("SUPER_ADMIN")>
                                    <li>
                                        <a href="${ctx}/customer/customerReviewList">
                                            客户审核
                                        </a>
                                    </li>
                                </#if>
                            </ul>
                        </li>
                    </#if>
                <#--	<#if bmsModel.user.hasRole("COMMISSIONER") >
                        <li class="" style="border-bottom: 1px solid #3d3d3d;">
                            <a href="javascript:;">
                                <i class="fa fa-line-chart"></i><span class="title"> 业绩管理 </span><span class="arrow "></span>
                            </a>
                            <ul class="sub-menu"  style="background: #293038;">
                                <li>
                                    <a href="${ctx}/achievement/achievementList">
                                        业绩详情
                                    </a>
                                </li>

                            </ul>
                        </li>
                     </#if>-->
                    <#if bmsModel.user.hasRole("SUPER_ADMIN")||  bmsModel.user.hasRole("COMMISSIONER")||bmsModel.user.hasRole("DISTRIBUTOR")>

                        <li class="" style="border-bottom: 1px solid #3d3d3d;">
                            <a href="javascript:;">
                                <i class="fa fa-bar-chart-o"></i><span class="title"> 数据统计 </span><span
                                    class="arrow "></span>
                            </a>
                            <ul class="sub-menu" style="background: #293038;">
                                <#if bmsModel.user.hasRole("SUPER_ADMIN")>
                                    <li>
                                        <a href="${ctx}/dataStatistics/getBillOptimization">
                                            成本统计
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/dataStatistics/distributorData">
                                            渠道商统计
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/dataStatistics/commissionerData">
                                            专员统计
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/billManage/manageListByAdmin">
                                            订单管理
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/billManage/getNewRanking">
                                            最新排名
                                        </a>
                                    </li>
                                </#if>
                                <#if  bmsModel.user.hasRole("COMMISSIONER")||bmsModel.user.hasRole("DISTRIBUTOR")>

                                    <li>
                                        <a href="${ctx}/billManage/manageListByOther">
                                            订单管理
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/billManage/getNewRanking">
                                            最新排名
                                        </a>
                                    </li>
                                </#if>
                            </ul>
                        </li>
                    </#if>
                    <#if bmsModel.user.hasRole("SUPER_ADMIN") || bmsModel.user.hasRole("COMMISSIONER") >
                        <li class="" style="border-bottom: 1px solid #3d3d3d;">
                            <a href="javascript:;">
                                <i class="fa fa-wrench"></i><span class="title"> 优化工具 </span><span
                                    class="arrow "></span>
                            </a>
                            <ul class="sub-menu" style="background: #293038;">
                                <li>
                                    <a href="${ctx}/optimizationTool/keywordpricesearch">
                                        关键词价格查询
                                    </a>
                                </li>
                                <li>
                                    <a href="javascript:;">
                                        关键词排名查询
                                    </a>
                                </li>
                                <li>
                                    <a href="javascript:;">
                                        关键词指数查询
                                    </a>
                                </li>

                                <li>
                                    <a href="javascript:;">
                                        网址收录查询
                                    </a>
                                </li>

                            </ul>
                        </li>
                    </#if>
                    <#if bmsModel.user.hasRole("SUPER_ADMIN")||bmsModel.user.hasRole("DISTRIBUTOR")>
                        <li class="" style="border-bottom: 1px solid #3d3d3d;">
                            <a href="javascript:;">
                                <i class="fa fa-magnet"></i><span class="title"> 自定义桌面 </span><span
                                    class="arrow "></span>
                            </a>
                            <ul class="sub-menu" style="background: #293038;">
                                <li class="bbbb">
                                    <a href="${ctx}/userCompany/userCompanyView">
                                        自定义页面
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </#if>

                    <li class="" style="border-bottom: 1px solid #3d3d3d;">
                        <a href="javascript:;">
                            <i class="fa fa-magnet"></i><span class="title"> 个人中心 </span><span class="arrow "></span>
                        </a>
                        <ul class="sub-menu" style="background: #293038;">
                            <li class="updateUser">
                                <a href="javascript:;">
                                    信息修改
                                </a>
                            </li>
                            <li class="updatePwd">
                                <a href="javascript:;">
                                    密码修改
                                </a>
                            </li>
                            <li class="apiUpdate">
                                <a href="${ctx}/optimizationTool/ParameterSetting">
                                    API参数设置
                                </a>
                            </li>

                        </ul>
                    </li>

                  <#--  <li class="" style="border-bottom: 1px solid #3d3d3d;">
                        <a href="javascript:;">
                            <i class="glyphicon glyphicon-envelope"></i><span class="title"> 信息系统 </span>
                            <#if bmsModel.SendUnReadNum gt 0||bmsModel.InUnReadNum gt 0>
                                <span class="label label-warning"
                                      id="">${bmsModel.SendUnReadNum+bmsModel.InUnReadNum}</span>
                            </#if>
                            <span class="arrow "></span>
                        </a>
                        <ul class="sub-menu" style="background: #293038;">
                            <li class="">
                                <a href="${ctx}/Message/WriteMail">
                                    写信息
                                </a>
                            </li>
                            <li class="">
                                <a href="${ctx}/Message/InBox">
                                    收件箱
                                    <#if bmsModel.InUnReadNum gt 0>
                                        <span class="label label-warning" id="">${bmsModel.InUnReadNum}</span>
                                    </#if>
                                </a>
                            </li>
                            <li class="">
                                <a href="${ctx}/Message/SendBox">
                                    发件箱
                                    <#if bmsModel.SendUnReadNum gt 0>
                                        <span class="label label-warning" id="">${bmsModel.SendUnReadNum}</span>
                                    </#if>
                                </a>
                            </li>
                            <li class="">
                                <a href="${ctx}/Message/DraftBox">
                                    草稿箱
                                </a>
                            </li>
                            <li class="">
                                <a href="${ctx}/Message/DustbinBox">
                                    垃圾箱
                                </a>
                            </li>

                        </ul>
                    </li>-->
                    <li class="" style="border-bottom: 1px solid #3d3d3d;">
                        <a href="javascript:;">
                            <i class="	glyphicon glyphicon-bullhorn"></i><span class="title"> 公告系统 </span><span
                                class="arrow "></span>
                        </a>
                        <ul class="sub-menu" style="background: #293038;">
                            <#if bmsModel.user.hasRole("SUPER_ADMIN")||bmsModel.user.hasRole("DISTRIBUTOR")||bmsModel.user.hasRole("AGENT")||bmsModel.user.hasRole("SECRETARY")>
                                <li class="">
                                    <a href="${ctx}/Message/SendNotice">
                                        发布公告
                                    </a>
                                </li>
                            </#if>
                            <li class="">
                                <a href="${ctx}/Message/NoticeSearch">
                                    公告查询
                                </a>
                            </li>

                        </ul>
                    </li>
                    <li class="" style="border-bottom: 1px solid #3d3d3d;">
                        <a href="javascript:;">
                            <i class="fa fa-comments-o"></i><span class="title"> 反馈与帮助 </span>
                            <#if bmsModel.UnReadNum gt 0>
                                <span class="label label-warning" id="">${bmsModel.UnReadNum}</span>
                            </#if>
                            <span class="arrow "></span>
                        </a>
                        <ul class="sub-menu" style="background: #293038;">
                            <#if !bmsModel.user.hasRole("SUPER_ADMIN")&&!bmsModel.user.hasRole("SECRETARY")>
                                <li class="">
                                    <a href="${ctx}/Message/SendFeedback">
                                        提交反馈
                                    </a>
                                </li>
                            </#if>
                            <li class="">
                                <a href="${ctx}/Message/FeedbackSearch">
                                    反馈查询
                                    <#if bmsModel.UnReadNum gt 0>
                                        <span class="label label-warning" id="">${bmsModel.UnReadNum}</span>
                                    </#if>
                                </a>
                            </li>

                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <div class="page-content-wrapper">
            <div class="page-content">
                <#nested />
            </div>
        </div>
    </div>


    <div class="modal-backdrop in" style="display: none"></div>
    <!--信息修改-->
    <div class="bootbox modal in updateUserDiv" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">信息修改</h4>
                </div>
                <div class="modal-body" style="max-height: 574px;">
                    <div class="bootbox-body">
                        <style type="text/css">
                            .modal-dialog {
                                width: 500px;
                            }
                        </style>
                        <form class="form-horizontal row-border" id="addOperatorform" action="#"
                              novalidate="novalidate">
                            <div class="form-group">
                                <label class="col-md-3 control-label">
                                    登录名<span class="required">* </span>
                                </label>
                                <div class="col-md-9">
                                    <input name="userNameBms" class="form-control input-width-large" type="text"
                                           value="${bmsModel.user.userName}" readonly="readonly">
                                </div>
                                <div class="pdlogid"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">
                                    真实姓名<span class="required">* </span>
                                </label>
                                <div class="col-md-9">
                                    <input name="realNameBms" class="form-control input-width-large" type="text"
                                           value="${bmsModel.user.realName}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">
                                    联系人<span class="required">* </span>
                                </label>
                                <div class="col-md-9">
                                    <input name="contactBms" class="form-control input-width-large" type="text"
                                           value="${bmsModel.user.contact}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">
                                    联系电话<span class="required">* </span>
                                </label>
                                <div class="col-md-9">
                                    <input name="phoneBms" class="form-control input-width-large" type="text"
                                           value="${bmsModel.user.phone}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">
                                    QQ<span class="required">* </span>
                                </label>
                                <div class="col-md-9">
                                    <input name="qqBms" class="form-control input-width-large" type="text"
                                           value="${bmsModel.user.qq}">
                                </div>
                            </div>
                        </form>

                    </div>
                </div>
                <div class="modal-footer">
                    <button data-bb-handler="success" type="button" class="btn wzgj-blue updateUserCmt">确定</button>
                    <button data-bb-handler="cancel" type="button" class="btn wzgj-btn bmscancel">取消</button>
                </div>
            </div>
        </div>
    </div>
    <!--信息修改end-->

    <!--修改密码-->
    <div class="bootbox modal in updatePwdDiv" tabindex="-1" role="dialog" style="display: none;" aria-hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">

                    <h4 class="modal-title">修改密码</h4></div>
                <div class="modal-body" style="max-height: 372px;">
                    <div class="bootbox-body">
                        <style type="text/css">.modal-dialog {
                            width: 560px;
                        }</style>
                        <form class="form-horizontal row-border" id="edt-form" action="#" novalidate="novalidate">
                            <div class="alert alert-info fade in">
                                系统对密码采用不可逆多重加密技术，如有遗忘，请联系客服随机生成！
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">原始密码:</label>
                                <div class="col-md-10">
                                    <input class="form-control input-width-large" name="passold" id="passold"
                                           placeholder="原始密码" type="password" style="width:210px;">
                                    <div class="passoldDiv" style="display: none;color: #ff0000;"></div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">新密码:</label>
                                <div class="col-md-10">
                                    <input class="form-control input-width-large" name="passnew" id="passnew"
                                           placeholder="新密码(12位以下，英文加数字)" type="password" style="width:210px;">
                                    <div class="passnewDiv"></div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label">确认密码:</label>
                                <div class="col-md-10">
                                    <input class="form-control input-width-large" name="passok" id="passok"
                                           placeholder="确认密码" type="password" style="width:210px;">
                                    <div class="passokDiv"></div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="modal-footer">
                    <button data-bb-handler="success" type="button" class="btn wzgj-blue updatePwdcmt">确定</button>
                    <button data-bb-handler="cancel" type="button" class="btn wzgj-btn bmscancel">取消</button>
                </div>
            </div>
        </div>
    </div>
    <!--修改密码end-->
    </@base.html>

</#macro>


