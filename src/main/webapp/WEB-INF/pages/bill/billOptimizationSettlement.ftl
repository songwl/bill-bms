<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "系统概况">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/public/highcharts.js"></script>
<style>
    .dashboard-stat {
        border-radius: 5px !important;
    }

    .visual > .fa {
        opacity: 0.2;
    }

    .details * {
        font-size: 18px !important;
    }
</style>
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>优化管理</span> > <span>优化结算</span>
    </div>

</div>
<div class="row row-bg">
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-home"></i>
            </div>
            <div class="details">
                <div class="number">账户余额</div>
                <div class="desc" id="userBalance">￥0.00</div>
            </div>
        </div>
    </div>

    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-yahoo"></i>
            </div>
            <div class="details">
                <div class="number">年度结算</div>
                <div class="desc" id="yearConsumption">￥0.00</div>
            </div>
        </div>
    </div>


    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-bitcoin"></i>
            </div>
            <div class="details">
                <div class="number">本月结算</div>
                <div class="desc" id="">￥${bmsModel.MonthConsumption}</div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-bitcoin"></i>
            </div>
            <div class="details">
                <div class="number">上月结算</div>
                <div class="desc" id="lastMonthConsumption">￥0.00</div>
            </div>
        </div>
    </div>




    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-dollar"></i>
            </div>
            <div class="details">
                <div class="number">今日结算</div>
                <div class="desc" id="">￥${bmsModel.DayConsumption}</div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-dollar"></i>
            </div>
            <div class="details">
                <div class="number">昨日结算</div>
                <div class="desc" id="">￥${bmsModel.yesterDayConsumption}</div>
            </div>
        </div>
    </div>

</div>


<div class="row">
    <div class="col-md-12">
        <div class="widget box">
            <div class="widget-header" style="text-align:left;">
                <h4>
                    <i class="fa fa-line-chart"></i>任务消费走势
                </h4>
                <div class="toolbar no-padding">
                    <div class="btn-group">
                        <span class="btn btn-xs widget-collapse">
                            <i class="icon-angle-down"></i>
                        </span>
                    </div>
                </div>
            </div>
            <div id="container" style="width: 100%; height: 400px; float:left;;"></div>
        </div>
    </div>
</div>



<script>
    $.ajax({
        type: 'get',
        async: true,
        url: CTX + '/order/userBalance',
        success: function (result) {

            if(result.userBalance==null)
            {
                $("#userBalance").html("¥0.00");
            }
            else
            {
                $("#userBalance").html("¥"+result.userBalance);
            }
        }
    });
    $.ajax({
        type: 'get',
        async: true,
        url: CTX + '/order/yearConsumption',
        success: function (result) {
            if(result.yearSum==null)
            {
                $("#yearConsumption").html("¥0.00");
            }
            else
            {
                $("#yearConsumption").html("¥"+result.yearSum);
            }
        }
    });
    $.ajax({
        type: 'get',
        async: true,
        url: CTX + '/order/lastMonthConsumption',
        success: function (result) {
            if(result.lastMonthSum==null)
            {
                $("#lastMonthConsumption").html("¥0.00");
            }
            else
            {
                $("#lastMonthConsumption").html("¥"+result.lastMonthSum);
            }
        }
    });

    $(function () {
        var chart1 = new Highcharts.Chart('container', {
            chart: {
                borderColor: '#D9D9D9',
                borderWidth: 1,
                type: 'line'
            },
            credits: {
                enabled: false,//不显示highCharts版权信息

            },
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