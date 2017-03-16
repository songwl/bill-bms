<!DOCTYPE html>
<!--[if IE 8]>
<html lang="en" class="ie8 no-js">
<![endif]-->
<!--[if IE 9]>
<html lang="en" class="ie9 no-js">
<![endif]-->
<html lang="en" class="no-js">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8"/>
    <title>主页</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <meta name="MobileOptimized" content="320">
    <link href="../static/js/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="../static/js/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="../static/js/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
    <link href="../static/js/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
    <link href="../static/js/assets/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="../static/js/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
    <link href="../static/js/assets/css/plugins.css" rel="stylesheet" type="text/css"/>
    <link href="../static/js/assets/css/pages/tasks.css" rel="stylesheet" type="text/css"/>
    <link href="../static/js/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
    <link href="../static/js/assets/css/custom.css" rel="stylesheet" type="text/css"/>
    <link rel="shortcut icon" href="../static/js/assets/img/favicon.ico"/>
</head>
<body class="page-header-fixed">
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
                    <span class="username">张三</span>
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
                            <a href="javascript:;">
                                关键词排名
                            </a>
                        </li>
                        <li>
                            <a href="javascript:;">
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
            <div class="theme-panel hidden-xs hidden-sm">
                <div class="toggler">
                </div>
                <div class="toggler-close">
                </div>
                <div class="theme-options">
                    <!--页面切换颜色
                <div class="theme-option theme-colors clearfix">
                    <span> 主题颜色 </span>
                    <ul>
                        <li class="color-black current color-default" data-style="default"></li>
                        <li class="color-blue" data-style="blue"></li>
                        <li class="color-brown" data-style="brown"></li>
                        <li class="color-purple" data-style="purple"></li>
                        <li class="color-grey" data-style="grey"></li>
                        <li class="color-white color-light" data-style="light"></li>
                    </ul>
                </div>
                -->
                    <div class="theme-option">
                        <span> 布局 </span>
                        <select class="layout-option form-control input-small">
                            <option value="fluid">顺序</option>
                            <option value="boxed">盒状</option>
                        </select>
                    </div>
                    <div class="theme-option">
                        <span> 标题 </span>
                        <select class="header-option form-control input-small">
                            <option value="fixed">固定</option>
                            <option value="default">默认</option>
                        </select>
                    </div>
                    <div class="theme-option">
                        <span> 工具栏 </span>
                        <select class="sidebar-option form-control input-small">
                            <option value="fixed">固定</option>
                            <option value="default">默认</option>
                        </select>
                    </div>
                    <div class="theme-option">
                        <span> 工具栏位置 </span>
                        <select class="sidebar-pos-option form-control input-small">
                            <option value="left">左边</option>
                            <option value="right">右边</option>
                        </select>
                    </div>
                    <div class="theme-option">
                        <span> 页脚 </span>
                        <select class="footer-option form-control input-small">
                            <option value="fixed">固定</option>
                            <option value="default">默认</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">

                    <ul class="page-breadcrumb breadcrumb">
                        <li>
                            <i class="fa fa-home"></i>
                            <a href="javascript:;">
                                首页
                            </a>
                            <i class="fa fa-angle-right"></i>
                        </li>

                    </ul>
                </div>
            </div>
            <div id="main-content">
            </div>
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
<script src="../static/js/assets/plugins/respond.min.js"></script>
<script src="../static/js/assets/plugins/excanvas.min.js"></script>
<script src="../static/js/assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="../static/js/assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<script src="../static/js/assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="../static/js/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../static/js/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="../static/js/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="../static/js/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="../static/js/assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="../static/js/assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="../static/js/assets/plugins/jquery-validation/dist/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../static/js/assets/plugins/select2/select2.min.js"></script>
<script src="../static/js/assets/scripts/app.js" type="text/javascript"></script>
<script type="text/javascript" src="../static/js/app/js/index.js"></script>
</body>
</html>