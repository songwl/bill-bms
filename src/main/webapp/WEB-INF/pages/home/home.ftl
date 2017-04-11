<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "操作员列表">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="https://img.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
<script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>管理后台</span> > <span>系统概况</span>
    </div>

</div>
<div class="row row-bg">
    <#if bmsModel.user.hasRole("SUPER_ADMIN") ||bmsModel.user.hasRole("ADMIN")||bmsModel.user.hasRole("COMMISSIONER")>
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
</#if>
 <#if bmsModel.user.hasRole("DISTRIBUTOR")||bmsModel.user.hasRole("AGENT")||bmsModel.user.hasRole("CUSTOMER")>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="statbox widget box box-shadow">
                <div class="widget-content">
                    <div class="visual cyan">
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="title">
                        账户余额
                    </div>
                    <div class="value">
                        ¥${bmsModel.balance}
                    </div>
                </div>
            </div>
        </div>
    </#if>

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
                ${bmsModel.AllbillCount}
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
                    ${bmsModel.billCount}
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
                     ¥${bmsModel.DayConsumption}
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

<div id="container" style="width: 40%; height: 400px; float:left;margin-left:70px;"></div>
<div id="container1" style="width: 40%; height: 400px; float:left;margin-left:70px;"></div>
<div id="container2" style="min-width:400px;height:400px"></div>
<script>
    $(function () {
        $('#container').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: '搜索引擎达标率'
            },
            tooltip: {
                headerFormat: '{series.name}<br>',
                pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    }
                }
            },
            series: [{
                type: 'pie',
                name: '浏览器访问量占比',
                data: [
                    ['Firefox',   45.0],
                    ['IE',       26.8],
                    {
                        name: 'Chrome',
                        y: 12.8,
                        sliced: true,
                        selected: true
                    },
                    ['Safari',    8.5],
                    ['Opera',     6.2],
                    ['其他',   0.7]
                ]
            }]
        });
    });
    $(function () {
        $('#container1').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: '搜索引擎达标率'
            },
            tooltip: {
                headerFormat: '{series.name}<br>',
                pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    }
                }
            },
            series: [{
                type: 'pie',
                name: '浏览器访问量占比',
                data: [
                    ['Firefox',   45.0],
                    ['IE',       26.8],
                    {
                        name: 'Chrome',
                        y: 12.8,
                        sliced: true,
                        selected: true
                    },
                    ['Safari',    8.5],
                    ['Opera',     6.2],
                    ['其他',   0.7]
                ]
            }]
        });
    });
    /**
     * Highcharts 在 4.2.0 开始已经不依赖 jQuery 了，直接用其构造函数既可创建图表
     **/
    $(function () {
    var chart = new Highcharts.Chart('container2', {
        title: {
            text: '任务达标数量走势图 ',
            x: -20
        },

        xAxis: {
            categories: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
        },
        yAxis: {
            title: {
                text: '温度 (°C)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '°C'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: '上月',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: '本月',
            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
        },  ]
    })
    });



</script>

</@base.html>