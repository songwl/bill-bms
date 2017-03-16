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
	<link href="${ctx}/static/js/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/js/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/js/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/js/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/js/assets/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/js/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/js/assets/css/plugins.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/js/assets/css/pages/tasks.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/js/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="${ctx}/static/js/assets/css/custom.css" rel="stylesheet" type="text/css"/>
	<link rel="shortcut icon" href="${ctx}/static/js/assets/img/favicon.ico"/>
  
	${bms_head_content!""}
</@base.headcontent>

<@base.headjscontent>
	${bms_head_js_content!""}
</@base.headjscontent>

<@base.jscontent>
	<script src="${ctx}/static/js/assets/plugins/respond.min.js"></script>
	<script src="${ctx}/static/js/assets/plugins/excanvas.min.js"></script>
	<script src="${ctx}/static/js/assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/js/assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/js/assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/js/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/js/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/js/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/js/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/js/assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/js/assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/js/assets/plugins/jquery-validation/dist/jquery.validate.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/js/assets/plugins/select2/select2.min.js"></script>
	<script src="${ctx}/static/js/assets/scripts/app.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/js/app/js/index.js"></script>
	
	${bms_js_content!""}
</@base.jscontent>

<@base.html title "page-header-fixed">
	<div class="header navbar navbar-inverse navbar-fixed-top">
		<div class="header-inner">
			<a class="navbar-brand" href="javascript:;">
				翊芃网络
			</a>
			<a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<img src="../static/js/assets/img/menu-toggler.png" alt=""/>
			</a>
			<ul class="nav navbar-nav pull-right">
				<li class="dropdown user">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
						<img alt="" src="../static/js/assets/img/avatar1_small.jpg"/>
						<span class="username">${bmsModel.user.userName}</span>
						<i class="fa fa-angle-down"></i>
					</a>
					<ul class="dropdown-menu">
						<li>
							<a href="javascript:;" id="trigger_fullscreen">
								<i class="fa fa-move"></i> 全屏
							</a>
						</li>
						<li>
							<a href="#">
								<i class="fa fa-lock"></i>修改头像
							</a>
						</li>
						<li>
							<a href="rest/user/logout">
								<i class="fa fa-key"></i> 退出
							</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
	<div class="clearfix">
	</div>
	<div class="page-container">
		<div class="page-sidebar-wrapper">
			<div class="page-sidebar navbar-collapse collapse">
				<ul class="page-sidebar-menu" id="page-sidebar-menu">
					<li class="sidebar-toggler-wrapper">
						<div class="sidebar-toggler hidden-phone">
						</div>
					</li>
					<li class="start active">
						<a href="rest/page/dashboard" id="btn-dashboard">
							<i class="fa fa-home"></i><span class="title"> 首页 </span><span class="selected"></span>
						</a>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-star-o"></i><span class="title"> 优化管理 </span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a href="${ctx}/bill/list">
									关键词排名
								</a>
							</li>
                            <li>
                                <a href="${ctx}/bill/pendingAudit">
                                    待审核订单
                                </a>
                            </li>

						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-paperclip"></i><span class="title"> 操作员管理 </span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a href="javascript:;">
									操作员列表
								</a>
							</li>

						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-magic"></i><span class="title"> 客户管理 </span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a href="javascript:;">
									客户管理
								</a>
							</li>
							<li>
								<a href="javascript:;">
									资金明细
								</a>
							</li>

						</ul>
					</li>
					<li class="">
						<a href="javascript:;">
							<i class="fa fa-magnet"></i><span class="title"> 个人中心 </span><span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<li>
								<a href="javascript:;">
									信息修改
								</a>
							</li>
							<li>
								<a href="javascript:;">
									密码修改
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
	<div class="footer">
		<div class="footer-inner">
			2017 &copy; 鱼在我这里。
		</div>
		<div class="footer-tools">
			<span class="go-top"><i class="fa fa-angle-up"></i></span>
		</div>
	</div>
</@base.html>

</#macro>


