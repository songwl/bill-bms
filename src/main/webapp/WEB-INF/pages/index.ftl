<#import "/base/bms.ftl" as bms>

<@bms.bmshtml bmsModel "主页">

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
</div>

</@bms.bmshtml>