<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "系统概况">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/public/highcharts.js"></script>
<script src="${ctx}/static/js/billManage/getNewRanking.js"></script>
<style>
    .dashboard-stat {
        border-radius: 5px !important;
    }

    .fa {
        opacity: 0.2;
    }

    .details * {
        font-size: 18px !important;
    }
</style>
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>管理后台</span> > <span>系统概况</span>
    </div>

</div>
<div class="row row-bg">
    <#if bmsModel.user.hasRole("SUPER_ADMIN") ||bmsModel.user.hasRole("ADMIN")||bmsModel.user.hasRole("COMMISSIONER")>
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="dashboard-stat blue">
                <div class="visual">

                    <i class="fa fa-user"></i>
                </div>
                <div class="details">
                    <div class="number">客户数</div>
                    <div class="desc" id="UserCount"></div>
                </div>
            </div>
        </div>
    </#if>
    <#if bmsModel.user.hasRole("DISTRIBUTOR")||bmsModel.user.hasRole("AGENT")||bmsModel.user.hasRole("CUSTOMER")>
    <#--<div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="widget-content">
                <div class="visual cyan">
                    <i class="fa fa-user"></i>
                </div>
                <div class="title">
                    账户余额
                </div>
                <div class="value"  id="balance">

                </div>
            </div>
        </div>
    </div>-->
        <div class="col-sm-6 col-md-2 hidden-xs">
            <div class="dashboard-stat blue">
                <div class="visual">

                    <i class="fa fa-home"></i>
                </div>
                <div class="details">
                    <div class="number">账户余额</div>
                    <div class="desc" id="balance"></div>
                </div>
            </div>
        </div>
    </#if>

    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-dollar"></i>
            </div>
            <div class="details">
                <div class="number">客户本月消费</div>
                <div class="desc" id="MonthConsumption"></div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-dollar"></i>
            </div>
            <div class="details">
                <div class="number">客户今日消费</div>
                <div class="desc" id="DayConsumption"></div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-tasks"></i>
            </div>
            <div class="details">
                <div class="number">累计任务数</div>
                <div class="desc" id="AllbillCount"></div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-tasks"></i>
            </div>
            <div class="details">
                <div class="number">当前任务数</div>
                <div class="desc" id="billCount"></div>
            </div>
        </div>
    </div>

    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-tasks"></i>
            </div>
            <div class="details">
                <div class="number">今日达标任务</div>
                <div class="desc" id="standardSum"></div>
            </div>
        </div>
    </div>
</div>

<div class="row row-bg">
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-tasks"></i>
            </div>
            <div class="details">
                <div class="number">总完成率</div>
                <div class="desc" id="AllCompleteness"></div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-tasks"></i>
            </div>
            <div class="details">
                <div class="number">百度完成率</div>
                <div class="desc" id="baiduCompleteness"></div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-tasks"></i>
            </div>
            <div class="details">
                <div class="number">百度手机完成率</div>
                <div class="desc" id="baiduWapCompleteness"></div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-tasks"></i>
            </div>
            <div class="details">
                <div class="number">360完成率</div>
                <div class="desc" id="sanliulingCompleteness"></div>
            </div>
        </div>
    </div>
<#--<div class="col-sm-6 col-md-2 hidden-xs">
    <div class="dashboard-stat blue">
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
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-tasks"></i>
            </div>
            <div class="details">
                <div class="number">搜狗完成率</div>
                <div class="desc" id="sougouCompleteness"></div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
            <div class="visual">
                <i class="fa fa-tasks"></i>
            </div>
            <div class="details">
                <div class="number">神马完成率</div>
                <div class="desc" id="shenmaCompleteness"></div>
            </div>
        </div>
    </div>

</div>
</div>

<#--<div class="row row-bg">
  &lt;#&ndash;  <div class="col-sm-6 col-md-2 hidden-xs">
        <div class="dashboard-stat blue">
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
            <div id="container1" style="width: 100%; height: 400px; float:left;;"></div>
        </div>
    </div>
</div>
    <#if bmsModel.user.hasRole("SUPER_ADMIN")>
    <div class="row" STYLE="margin-top: 15px;">
        <div class="col-md-12">
            <div class="widget box">
                <div class="widget-header" style="text-align:left;">
                    <h4>
                        <i class="fa fa-line-chart"></i>任务达标走势
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
    </#if>
<div class="row" STYLE="margin-top: 15px;">
    <div class="col-md-12">
        <div class="widget box">
            <div class="widget-header" style="text-align:left;">
                <h4>
                    <i class="fa fa-plus"></i>新增排名
                </h4>
                <div class="toolbar no-padding">
                    <div class="btn-group">
                        <span class="btn btn-xs widget-collapse">
                            <i class="icon-angle-down"></i>
                        </span>
                    </div>
                </div>
            </div>
            <table id="myTable" class="table table-striped  table-condensed table-responsive" style="width:100%">

            </table>
        </div>
    </div>
</div>

<script type="text/javascript">


    //异步加载首页数据，解决加载慢的问题
    //1,客户数
    $.ajax({
        type: 'get',
        async: true,
        url: CTX + '/userCount',
        success: function (result) {
            $("#UserCount").html(result.UserCount);
        }
    });
    //2,余额
    $.ajax({
        type: 'get',
        async: true,
        url: CTX + '/balance',
        success: function (result) {
            $("#balance").html("¥" + result.balance);
        }
    });
    //3，月总消费
    $.ajax({
        type: 'get',
        async: true,
        url: CTX + '/MonthConsumption',
        success: function (result) {
            if (result.MonthConsumption == null) {
                $("#MonthConsumption").html("¥0.00");
            }
            else {
                $("#MonthConsumption").html("¥" + result.MonthConsumption);
            }
        }
    });
    //4，本日消费
    $.ajax({
        type: 'get',
        async: true,
        url: CTX + '/DayConsumption',
        success: function (result) {
            if (result.DayConsumption == null) {
                $("#DayConsumption").html("¥0.00");
            }
            else {
                $("#DayConsumption").html("¥" + result.DayConsumption);
            }
        }
    });
    //5，当前任务数
    $.ajax({
        type: 'get',
        async: true,
        url: CTX + '/billCount',
        success: function (result) {
            $("#billCount").html(result.billCount);
        }
    });
    //6，累计任务数
    $.ajax({
        type: 'get',
        async: true,
        url: CTX + '/AllbillCount',
        success: function (result) {
            $("#AllbillCount").html(result.AllbillCount);
        }
    });
    //7，达标数
    $.ajax({
        type: 'get',
        async: true,
        url: CTX + '/standardSum',
        success: function (result) {
            $("#standardSum").html(result.standardSum);

        }
    });

    $.ajax({
        type: 'get',
        async: true,
        url: CTX + '/homeDetails',
        success: function (result) {
            $("#AllCompleteness").html(result.search.allcompleteness + "%");
            $("#baiduCompleteness ").html(result.search.baiducompleteness + "%");
            $("#baiduWapCompleteness ").html(result.search.baiduwapcompleteness + "%");
            $("#sanliulingCompleteness ").html(result.search.sanliulingcompleteness + "%");
            $("#sougouCompleteness ").html(result.search.sougoucompleteness + "%");
            $("#shenmaCompleteness").html(result.search.shenmacompleteness + "%");
            container1(result);
        }
    })
    function container1(result) {
        var arr = result.yAxisSum.split(',');
        var arr1 = result.seriesLastMonthSum.split(',');
        var arr2 = result.seriesNowMonthSum.split(',');
        var aa = [];
        var aa1 = [];
        var aa2 = [];
        $.each(arr, function (i, item) {
            aa.push(item - 0);
        })
        $.each(arr1, function (i, item) {
            aa1.push(item - 0);
        })
        $.each(arr2, function (i, item) {
            aa2.push(item - 0);
        })
        var chart1 = new Highcharts.Chart('container1', {
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
                categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31']

            },
            yAxis: {
                floor: 0,
                gridLineColor: '#197F07',
                gridLineWidth: 1,
                tickPositions: aa,
                ceiling: 20,
                title: {
                    text: '单位 (元)'
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: [{
                name: '上月',
                data: aa1,
                color: '#ff0000',
                marker: {
                    symbol: 'square'//点形状
                },
            }, {
                name: '本月',
                data: aa2
            },]
        })

        var str1 = result.yAxis.split(',');
        var str2 = result.seriesLastMonth.split(',');
        var str3 = result.seriesNowMonth.split(',');
        var bb1 = [];
        var bb2 = [];
        var bb3 = [];
        $.each(str1, function (i, item) {
            bb1.push(item - 0);
        })
        $.each(str2, function (i, item) {
            bb2.push(item - 0);
        })
        $.each(str3, function (i, item) {
            bb3.push(item - 0);
        })
        var chart = new Highcharts.Chart('container', {
            chart: {
                borderColor: '#D9D9D9',
                borderWidth: 1,
                type: 'line'
            },
            credits: {
                enabled: false,//不显示highCharts版权信息

            },
            title: {
                text: '任务达标数量走势图 ',
                x: -20
            },

            xAxis: {
                categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31']

            },
            yAxis: {
                floor: 0,
                gridLineColor: '#197F07',
                gridLineWidth: 1,
                tickPositions: bb1,
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
                color: '#ff0000',
                data: bb2
            }, {
                name: '本月',

                data: bb3
            },]
        })

    }
</script>

</@base.html>