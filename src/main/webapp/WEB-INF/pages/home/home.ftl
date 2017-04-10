<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "操作员列表">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>管理后台</span> > <span>系统概况</span>
    </div>

</div>
<div class="row row-bg">
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="statbox widget box box-shadow">
            <div class="widget-content">
                <div class="visual cyan">
                    <i class="fa fa-user"></i>
                </div>
                <div class="title">
                    客户数
                </div>
                <div class="value">
                ${bmsModel.UserCount}
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="statbox widget box box-shadow">
            <div class="widget-content">
                <div class="visual red">
                    <i class="fa fa-dollar"></i>
                </div>
                <div class="title">
                    本月总消费
                </div>
                <div class="value">
                    ¥${bmsModel.MonthConsumption}
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="statbox widget box box-shadow">
            <div class="widget-content">
                <div class="visual green">
                    <i class="fa fa-tasks"></i>
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
                    <i class="fa fa-tasks"></i>
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
                    <i class="fa fa-dollar"></i>
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
                    <i class="fa fa-tasks"></i>
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
                <div class="visual cyan">
                    <i class="fa fa-tasks"></i>
                </div>
                <div class="title">
                    总完成率
                </div>
                <div class="value">
                    0.0%
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="statbox widget box box-shadow">
            <div class="widget-content">
                <div class="visual red">
                    <i class="fa fa-tasks"></i>
                </div>
                <div class="title">
                    百度完成率
                </div>
                <div class="value">
                   0.0%
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="statbox widget box box-shadow">
            <div class="widget-content">
                <div class="visual green">
                    <i class="fa fa-tasks"></i>
                </div>
                <div class="title">
                    百度手机完成率
                </div>
                <div class="value">
                    0.0%
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="statbox widget box box-shadow">
            <div class="widget-content">
                <div class="visual cyan">
                    <i class="fa fa-tasks"></i>
                </div>
                <div class="title">
                    360完成率
                </div>
                <div class="value">
                    0.0%
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="statbox widget box box-shadow">
            <div class="widget-content">
                <div class="visual red">
                    <i class="fa fa-tasks"></i>
                </div>
                <div class="title">
                    搜狗完成率
                </div>
                <div class="value">
                    0.0%
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="statbox widget box box-shadow">
            <div class="widget-content">
                <div class="visual green">
                    <i class="fa fa-tasks"></i>
                </div>
                <div class="title">
                    神马完成率
                </div>
                <div class="value">
                    0.0%
                </div>
            </div>
        </div>
    </div>
</div>



</@base.html>