<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "系统概况">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/public/highcharts.js"></script>
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>优化管理</span> > <span>优化结算</span>
    </div>

</div>
<div class="row row-bg">
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="statbox widget box box-shadow">
            <div class="widget-content">
                <div class="visual red">
                    <i class="fa fa-dollar"></i>
                </div>
                <div class="title">
                    本月结算
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
                <div class="visual red">
                    <i class="fa fa-dollar"></i>
                </div>
                <div class="title">
                    今日结算
                </div>
                <div class="value">
                    ￥${bmsModel.DayConsumption}
                </div>
            </div>
        </div>
    </div>

</div>





<div id="container" style="width: 100%; height: 400px; float:left;;"></div>
<script>
    $(function () {
        var chart1 = new Highcharts.Chart('container', {
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