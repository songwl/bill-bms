<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "代理商管理">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/public/highcharts.js"></script>
<script src="${ctx}/static/js/bill/billClientSideSettlement.js"></script>
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
        <i class="fa fa-home">&nbsp;</i><span>代理商管理</span> > <span>代理商结算</span>
    </div>

</div>
<div class="row row-bg">

    <#if bmsModel.user.hasRole("DISTRIBUTOR")||bmsModel.user.hasRole("AGENT")||bmsModel.user.hasRole("CUSTOMER")>

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
        <div class="dashboard-stat green">
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
        <div class="dashboard-stat purple">
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
        <div class="dashboard-stat yellow">
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

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary" id="list-panel">
            <div class="panel-body">
                <div class="panel-nav">
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;今日消费(<span id="length"></span>)</div>

                    <div class="cls"></div>
                </div>
                <table id="myTable" class="table table-striped  table-condensed table-responsive" style="width:100%;font-size: 13px;font-family: "微软雅黑">
                </table>
            </div>

        </div>
    </div>
</div>


<script type="text/javascript">


    //异步加载首页数据，解决加载慢的问题
    //1,客户数
/*    $.ajax({
        type: 'get',
        async: true,
        url: CTX + '/userCount',
        success: function (result) {
            $("#UserCount").html(result.UserCount);
        }
    });*/
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
                categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30']

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
                categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30',]

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

<script>
    $(function () {

        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();
        //2.初始化Button的点击事件
        var oButtonInit = new ButtonInit();
        oButtonInit.Init();
    });
    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#myTable').bootstrapTable({
                url:CTX+ '/order/billClientDayCost',         //请求后台的URL（*）
                method: 'get',                      //请求方式（*）
                toolbar: '#toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，
                //pagination: true,                   //是否显示分页（*）
                // pageNumber: 1,                       //初始化加载第一页，默认第一页
                // pageSize:20,                       //每页的记录行数（*）
                // pageList: [50, 500,1000],        //可供选择的每页的行数（*）
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                queryParams: oTableInit.queryParams,//传递参数（*）
                queryParamsType: "",
                minimumCountColumns: 2,             //最少允许的列数
                clickToSelect: true,                //是否启用点击选中行
                //height: 700,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id",                     //每一行的唯一标识，一般为主键列
                rowStyle: function (row, index) {
                    //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                    var strclass = "";
                    if ((row.id)%2==0){
                        strclass = '';
                    }
                    else {
                        strclass = 'active';
                    }
                    return { classes: strclass }
                },
                columns: [
                    {
                        checkbox: true
                    },{
                        field: 'id',

                        align: 'center',
                        valign: 'middle',
                        title: '序号',

                    },
                    {
                        field: 'fundItemId',

                        align: 'center',
                        valign: 'middle',
                        title: 'sql序号',
                        visible:false

                    },
                    {
                        field: 'userName',

                        align: 'center',
                        valign: 'middle',
                        title: '客户名',

                    },
                    {
                        field: 'changeAmount',
                        align: 'center',
                        valign: 'middle',
                        title: '本月扣费',

                    },
                    {
                        field: 'dayAccountSum',
                        align: 'center',
                        valign: 'middle',
                        title: '本日扣费',

                    },
                    {
                        field: 'balance',
                        align: 'center',
                        valign: 'middle',
                        title: '客户余额',

                    },
                    {
                        field: 'changeTime',
                        align: 'center',
                        valign: 'middle',
                        title: '消费时间',

                    }

                ],

            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {
                limit: params.pageSize,   //页面大小
                offset: params.pageNumber,  //页码
                sortOrder: params.sortOrder,
                sortName: params.sortName,

            };
            return temp;
        }



        return oTableInit;
    };

    $(function () {
        $("#queren").click(function () {

            $('#myTable').bootstrapTable('refresh');
        });

    });


</script>

</@base.html>