<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "系统概况">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/public/highcharts.js"></script>
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
                        ￥${bmsModel.balance}
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
                    ￥${bmsModel.MonthConsumption}
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
                    ￥${bmsModel.DayConsumption}
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
                ${bmsModel.standardSum}
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
                ${bmsModel.AllCompleteness}%
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
                ${bmsModel.baiduCompleteness}%
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
                ${bmsModel.baiduWapCompleteness}%
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
                ${bmsModel.sanliulingCompleteness}%
                </div>
            </div>
        </div>
    </div>
    <#--<div class="col-sm-6 col-md-2 hidden-xs">
        <div class="statbox widget box box-shadow">
            <div class="widget-content">
                <div class="visual cyan">
                    <i class="fa fa-tasks"></i>
                </div>
                <div class="title">
                    360手机完成率
                </div>
                <div class="value">
                ${bmsModel.sanliulingWapCompleteness}%
                </div>
            </div>
        </div>
    </div>-->
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
                ${bmsModel.sougouCompleteness}%
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
                    神马完成率
                </div>
                <div class="value">
                ${bmsModel.shenmaCompleteness}%
                </div>
            </div>
        </div>
    </div>

</div>

<#--<div class="row row-bg">
  &lt;#&ndash;  <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="statbox widget box box-shadow">
            <div class="widget-content">
                <div class="visual green">
                    <i class="fa fa-tasks"></i>
                </div>
                <div class="title">
                    搜狗手机完成率
                </div>
                <div class="value">
                ${bmsModel.sougouWapWapCompleteness}%
                </div>
            </div>
        </div>
    </div>&ndash;&gt;

</div>-->


<div id="container1" style="width: 100%; height: 400px; float:left;;"></div>
    <#if bmsModel.user.hasRole("SUPER_ADMIN")>
    <div id="container" style="width: 100%; height: 400px; float:left;;"></div>
    <script>
        $(function () {
            var chart = new Highcharts.Chart('container', {
                title: {
                    text: '任务达标数量走势图 ',
                    x: -20
                },

                xAxis: {
                    categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10','11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31']

                },
                yAxis: {
                    floor: 0,
                    gridLineColor: '#197F07',
                    gridLineWidth: 1,
                    tickPositions:[${bmsModel.yAxis}],
                    ceiling: 20,
                    title: {
                        text: '单位 (个)'
                    }
                },
                tooltip: {
                    valueSuffix: ''
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                },
                series: [{
                    name: '上月',
                    data: [${bmsModel.seriesLastMonth}]
                }, {
                    name: '本月',
                    data: [${bmsModel.seriesNowMonth}]
                },  ]
            })
        });
    </script>
    </#if>
<script>
    $(function () {
        var chart1 = new Highcharts.Chart('container1', {
            title: {
                text: '任务消费走势图 ',
                x: -20
            },

            xAxis: {
                categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10','11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31']

            },
            yAxis: {
                floor: 0,
                gridLineColor: '#197F07',
                gridLineWidth: 1,
                tickPositions:[${bmsModel.yAxisSum}],
                ceiling: 20,
                title: {
                    text: '单位 (元)'
                }
            },
            tooltip: {
                valueSuffix: ''
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: [{
                name: '上月',
                data: [${bmsModel.seriesLastMonthSum}]
            }, {
                name: '本月',
                data: [${bmsModel.seriesNowMonthSum}]
            },  ]
        })
    });

</script>

</@base.html>