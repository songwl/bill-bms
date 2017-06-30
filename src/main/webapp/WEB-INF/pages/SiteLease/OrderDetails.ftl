<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "写信息">
<link href="${ctx}/static/css/Message/font-awesome.min.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/custom.css" rel="stylesheet">
<link href="${ctx}/static/js/layer/skin/default/layer.css" rel="stylesheet">
<script src="${ctx}/static/js/layer/layer.js"></script>
<#--<link href="${ctx}/static/css/Message/Index.css" rel="stylesheet">-->

<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<div class="Navs">
    <div class="nav_R right" id="divQx">
        <div id="order">
            <span>&nbsp;<i class="fa fa-arrow-down"></i>&nbsp;订购</span>
        </div>

    </div>
    <div class="cls">
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary" id="list-panel" style="border-color: #eeeeee;">
            <div class="panel-body">

                <table id="myTable" class="table table-striped  table-condensed table-responsive"
                       style="width:100%;font-size: 13px;font-family: " 微软雅黑
                "></table>

            </div>

        </div>
    </div>
</div>
<script type="text/javascript">
    var index1;
    $(function () {
        MissionHall.init();
    });
    var MissionHall = {
        init: function () {
            //1.初始化Table
            var oTable = new MissionHall.TableInit();
            oTable.Init();
            //划分按钮
            $("#order").on("click", MissionHall.orderClick);
        },
        TableInit: function () {
            var oTableInit = new Object();
            //初始化Table
            oTableInit.Init = function () {
                $('#myTable').bootstrapTable({
                    url: CTX + '/SiteLease/GetOrderDetails',         //请求后台的URL（*）
                    method: 'get',                      //请求方式（*）
                    toolbar: '#toolbar',                //工具按钮用哪个容器
                    striped: true,                      //是否显示行间隔色
                    cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,                   //是否显示分页（*）
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    queryParams: oTableInit.queryParams,//传递参数（*）
                    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                    //queryParamsType: "",
                    pageNumber: 1,                       //初始化加载第一页，默认第一页
                    pageSize: 10,                       //每页的记录行数（*）
                    pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                    showColumns: false,                  //是否显示所有的列
                    showRefresh: false,                  //是否显示刷新按钮
                    minimumCountColumns: 2,             //最少允许的列数
                    clickToSelect: true,                //是否启用点击选中行
                    //height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                    uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                    showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
                    cardView: false,                    //是否显示详细视图
                    detailView: false,                   //是否显示父子表

                    columns: [
                        {
                            field: 'checked',
                            checkbox: true,
                            valign: 'middle',
                            align: 'center',
                            formatter: stateFormatter
                        },
                        {
                            field: 'id',
                            title: '编号',
                            align: 'center',
                            valign: 'middle',
                            visible: false
                        },
                        {
                            field: 'website',
                            align: 'center',
                            valign: 'middle',
                            title: '网址',
                            formatter: function (value, row, index) {
                                return value;
                            }
                        },
                        {
                            field: 'keywords',
                            align: 'left',
                            valign: 'middle',
                            title: '关键词',
                            //events: ReadMailEvents

                        },
                        {
                            field: 'keywordState',
                            align: 'center',
                            valign: 'middle',
                            title: '关键词状态',
                            formatter: function (value, row, index) {
                                if (value == 0) {
                                    return "必选";
                                } else {
                                    return "推荐";
                                }
                            }
                        },
                        {
                            field: 'firstRanking',
                            title: '初排',
                            align: 'center',
                            valign: 'middle'
                        },
                        {
                            field: 'newRanking',
                            title: '新排',
                            align: 'center',
                            valign: 'middle'
                        },
                        {
                            field: 'search_support',
                            title: '搜索引擎',
                            align: 'center',
                            valign: 'middle'

                        },
                        {
                            field: 'price',
                            align: 'center',
                            valign: 'middle',
                            title: '价格',
                            formatter: function (value, row, index) {
                                switch (row.search_support) {
                                    case "百度":
                                        return row.PriceBaiduPc;
                                        break;
                                    case "手机百度":
                                        return row.PriceBaiduWap;
                                        break;
                                    case "搜狗":
                                        return row.PriceSogouPc;
                                        break;
                                    case "360":
                                        return row.PriceSoPc;
                                        break;
                                    case "神马":
                                        return row.PriceSm;
                                        break;
                                }
                            }
                        },
                        {
                            field: 'time',
                            align: 'center',
                            valign: 'middle',
                            title: '时间',
                            formatter: function (value, row, index) {
                                var times = new Date(value);
                                return times.toLocaleString();
                            }
                        },
                        {
                            field: 'state',
                            align: 'center',
                            valign: 'middle',
                            title: '状态',
                            formatter: function (value, row, index) {
                                var a = "";
                                if (row.orderState == 6) {
                                    if (row.customerId ==${userId}) {
                                        a += "<a style='color: #aaaaaa;' id='reserveCancel'>已订购</a>";
                                    } else {
                                        a += "<a style='color: #ff0000;' id='reserve'>未订购</a>";
                                    }
                                } else {
                                    a += "<a style='color: #ff0000;' id='reserve'>未订购</a>";
                                }
                                return a;
                            }
                        }
                    ]
                });
            };

            function stateFormatter(value, row, index) {
                console
                if (row.keywordState == 0)//关键词状态为0必选
                    return {
                        disabled: true,//设置是否可用
                        checked: true//设置选中
                    };
                return value;
            }

            //得到查询的参数
            //oTableInit.queryParams = MissionHall.queryParams;
            oTableInit.queryParams = function (params) {
                var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    limit: params.limit,   //页面大小
                    offset: params.offset,  //页码
                    website: "${website}"
                };
                return temp;
            };
            return oTableInit;
        },
        orderClick: function () {
            var selectContent = $('#myTable').bootstrapTable('getSelections');
            //console.info(selectContent);
            if (selectContent.length == 0) {
                layer.msg("请先选择行");
                return;
            }
            if (!window.confirm("你确定要订购这些订单吗？已选择行数：" + selectContent.length + "行")) {
                return;
            }
            $.post(CTX + '/SiteLease/order', {
                data: selectContent,
                len: selectContent.length
            }, function (data) {
                if (data == -1) {
                    layer.msg("你没有权限");
                } else if (data == 0) {
                    layer.msg("成功");
                    $("#myTable").bootstrapTable("refresh");
                }
                else {
                    layer.msg("失败");
                }
            });
            return;
        }
    };
</script>







</@base.html>