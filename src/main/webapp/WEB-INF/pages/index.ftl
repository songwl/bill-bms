<#import "/base/bms.ftl" as bms>

<@bms.bmshtml bmsModel "主页">
<link href="${ctx}/static/css/public/back.css" rel="stylesheet">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<div class="theme-panel hidden-xs hidden-sm">

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
<!--
        <ul class="page-breadcrumb breadcrumb">
            <li>
                <i class="fa fa-home"></i>
                <a href="javascript:;">
                    首页
                </a>
                <i class="fa fa-angle-right"></i>
            </li>

        </ul>-->
    </div>
</div>

<div id="main-content">
    <div class="Navs">
        <div class="nav_L left">
            <i class="fa fa-home">&nbsp;</i><span>管理后台</span> > <span>系统概况</span>
        </div>

    </div>
    <div class="row row-bg">
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual red">
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="title">
                        客户数
                    </div>
                    <div class="value">
                        0
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual red">
                        <i class="icon-dollar"></i>
                    </div>
                    <div class="title">
                        本月总消费
                    </div>
                    <div class="value">
                        ¥0.00
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual green">
                        <i class="icon-tasks"></i>
                    </div>
                    <div class="title">
                        累计任务数
                    </div>
                    <div class="value">
                        0
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual cyan">
                        <i class="icon-tasks"></i>
                    </div>
                    <div class="title">
                        当前任务数
                    </div>
                    <div class="value">
                        0
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual red">
                        <i class="icon-dollar"></i>
                    </div>
                    <div class="title">
                        今日消费
                    </div>
                    <div class="value">
                        ¥0.00
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual green">
                        <i class="icon-tasks"></i>
                    </div>
                    <div class="title">
                        今日达标任务
                    </div>
                    <div class="value">
                        0
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row row-bg">
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual red">
                        <i class="icon-dollar"></i>
                    </div>
                    <div class="title">
                        客户数
                    </div>
                    <div class="value">
                        0
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual red">
                        <i class="icon-dollar"></i>
                    </div>
                    <div class="title">
                        本月总消费
                    </div>
                    <div class="value">
                        ¥0.00
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual green">
                        <i class="icon-tasks"></i>
                    </div>
                    <div class="title">
                        累计任务数
                    </div>
                    <div class="value">
                        0
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual cyan">
                        <i class="icon-tasks"></i>
                    </div>
                    <div class="title">
                        当前任务数
                    </div>
                    <div class="value">
                        0
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual red">
                        <i class="icon-dollar"></i>
                    </div>
                    <div class="title">
                        今日消费
                    </div>
                    <div class="value">
                        ¥0.00
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual green">
                        <i class="icon-tasks"></i>
                    </div>
                    <div class="title">
                        今日达标任务
                    </div>
                    <div class="value">
                        0
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row row-bg">
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual red">
                        <i class="icon-dollar"></i>
                    </div>
                    <div class="title">
                        客户数
                    </div>
                    <div class="value">
                        0
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual red">
                        <i class="icon-dollar"></i>
                    </div>
                    <div class="title">
                        本月总消费
                    </div>
                    <div class="value">
                        ¥0.00
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual green">
                        <i class="icon-tasks"></i>
                    </div>
                    <div class="title">
                        累计任务数
                    </div>
                    <div class="value">
                        0
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual cyan">
                        <i class="icon-tasks"></i>
                    </div>
                    <div class="title">
                        当前任务数
                    </div>
                    <div class="value">
                        0
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual red">
                        <i class="icon-dollar"></i>
                    </div>
                    <div class="title">
                        今日消费
                    </div>
                    <div class="value">
                        ¥0.00
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual green">
                        <i class="icon-tasks"></i>
                    </div>
                    <div class="title">
                        今日达标任务
                    </div>
                    <div class="value">
                        0
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

</@bms.bmshtml>