<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "系统概况">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/public/highcharts.js"></script>
<script src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
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
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary" id="list-panel">
            <div class="panel-body">
                <div style="margin-bottom: 5px;">
                    <span>日期：</span>
                    <input onFocus="WdatePicker({lang:'zh-tw',readOnly:true})" id="createTime"  style="border:1px solid #09c;height:30px;"/>

                    <span class="search" style="cursor: pointer;">&nbsp;<i class="fa fa-search"></i>&nbsp;查询</span>

                </div>
                <div class="panel-nav">

                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;客户消费</div>
                    <div class="Nav_Right">

                    </div>
                    <div class="cls"></div>
                </div>

                <table id="myTable" class="table table-striped  table-condensed table-responsive" style="width:100%;font-size: 13px;font-family: "微软雅黑">
                </table>
            </div>

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
<script>
    var searchTime;
    $(".search").click(function () {
        searchTime=$("#createTime").val();
        $('#myTable').bootstrapTable('refresh');
    })
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
                url:CTX+ '/order/billDayCost',         //请求后台的URL（*）
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
                searchTime:searchTime

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