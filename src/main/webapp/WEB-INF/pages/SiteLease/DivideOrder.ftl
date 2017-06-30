<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "写信息">
<link href="${ctx}/static/css/Message/font-awesome.min.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/custom.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/Index.css" rel="stylesheet">

<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>网站出租平台</span> > <span>任务大厅</span>
    </div>
    <div class="nav_R right" id="divQx">
        <div id="divide">
            <span>&nbsp;<i class="fa fa-arrow-down"></i>&nbsp;划分</span>
        </div>

        <div class="search">
            <span>&nbsp;<i class="fa fa-search"></i>&nbsp;查询</span>
        </div>
    </div>
    <div class="cls">
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary" id="list-panel" style="border-color: #eeeeee;">
            <div class="panel-body">
                <div class="panel-nav">
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;大厅任务
                    </div>
                    <div class="Nav_Right">


                    </div>
                    <div class="cls"></div>
                </div>

                <table id="myTable" class="table table-striped  table-condensed table-responsive"
                       style="width:100%;font-size: 13px;font-family: " 微软雅黑
                "></table>

            </div>

        </div>
    </div>
</div>

<div id="selectDistributor" style="display: none;">
    <h4 class="modal-header">请选择渠道商</h4>
    <div class="modal-body">
        <div class="form-group">
            <select id="distributor" class="form-control" style="height: initial;">
            </select>
        </div>
        <button id="confirm" class="btn btn-success form-control" style="background-color: #27c24c"><span
                class="glyphicon glyphicon-send">&nbsp;</span><span>确认</span></button>
    </div>
</div>
<style type="text/css">
    td{
        text-align: center;
    }
</style>
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
            $("#divide").on("click", MissionHall.divideClick);
            $("#confirm").on("click", MissionHall.confirmDistributor);
        },
        TableInit: function () {
            var oTableInit = new Object();
            //初始化Table
            oTableInit.Init = function () {
                $('#myTable').bootstrapTable({
                    url: CTX + '/SiteLease/AdminGetMission',         //请求后台的URL（*）
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
                            field: 'keywordNum',
                            align: 'left',
                            valign: 'middle',
                            title: '词数',
                            //events: ReadMailEvents

                        },
                        {
                            field: 'homePageNum',
                            title: '首页数',
                            width: 200,
                            align: 'center',
                            valign: 'middle'
                        },
                        {
                            field: 'time',
                            title: '时间',
                            align: 'center',
                            valign: 'middle',
                            formatter: function (value, row, index) {
                                var date = new Date(value);
                                return date.toLocaleString();
                            }
                        },
                        {
                            field: 'id',
                            title: '编号',
                            align: 'center',
                            valign: 'middle',
                            visible: false

                        },
                        {
                            field: 'orderstate',
                            align: 'center',
                            valign: 'middle',
                            title: '状态',
                            formatter: function (value, row, index) {
                                if (value == "1") {
                                    return "已分配"
                                }
                                else if (value == "2") {
                                    return "已划分";
                                } else if (value == "3") {
                                    return "已有预定";
                                } else if (value == "4") {
                                    return "预定完成";
                                } else if (value == "5") {
                                    return "等待客户订购";
                                } else if (value == "6") {
                                    return "订购完成";
                                }
                                return value;
                            }
                        },
                        {
                            field: 'username',
                            align: 'center',
                            valign: 'middle',
                            title: '渠道商',
                            formatter: function (value, row, index) {
                                return value;
                            }
                        },
                        {
                            field: 'cz',
                            align: 'center',
                            valign: 'middle',
                            title: '操作',
                            events: operateEvents,
                            formatter: function (value, row, index) {
                                var a = "<a style='color: #ff0000;' id='details'>详情</a>";
                                return a;
                            }

                        }
                    ]
                });
            };

            function stateFormatter(value, row, index) {
                if (row.orderstate > 3)
                    return {
                        disabled: true,//设置是否可用
                        checked: false//设置选中
                    };
                return value;
            }

            //得到查询的参数
            //oTableInit.queryParams = MissionHall.queryParams;
            oTableInit.queryParams = function (params) {
                var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    limit: params.limit,   //页面大小
                    offset: params.offset  //页码
                };
                return temp;
            };
            window.operateEvents = {
                'click #details': function (e, value, row, index) {
                    $.post(CTX + '/SiteLease/GetDetails', {website: row.website}, function (data) {
                        var str = "<table class=\"table table-hover table-striped table-bordered\">" +
                                "<thead><tr><th>网址</th><th>关键词</th><th>初排</th><th>新排</th><th>搜索引擎</th><th>价格</th><th>时间</th></thead><tbody>";
                        $.each(data, function (index, item) {
                            switch (item.search_support) {
                                case "百度":
                                    str += "<tr><td><span>" + item.website + "</span></td><td>" + item.keywords + "</td><td>" + item.firstRanking + "</td><td>" + item.newRanking + "</td><td>" + item.search_support + "</td><td>" + item.PriceBaiduPc + "</td><td><span>" + new Date(item.time).toLocaleString() + "</span></td></tr>";
                                    break;
                                case "手机百度":
                                    str += "<tr><td><span>" + item.website + "</span></td><td>" + item.keywords + "</td><td>" + item.firstRanking + "</td><td>" + item.newRanking + "</td><td>" + item.search_support + "</td><td>" + item.PriceBaiduWap + "</td><td><span>" + new Date(item.time).toLocaleString() + "</span></td></tr>";
                                    break;
                                case "搜狗":
                                    str += "<tr><td><span>" + item.website + "</span></td><td>" + item.keywords + "</td><td>" + item.firstRanking + "</td><td>" + item.newRanking + "</td><td>" + item.search_support + "</td><td>" + item.PriceSogouPc + "</td><td><span>" + new Date(item.time).toLocaleString() + "</span></td></tr>";
                                    break;
                                case "360":
                                    str += "<tr><td><span>" + item.website + "</span></td><td>" + item.keywords + "</td><td>" + item.firstRanking + "</td><td>" + item.newRanking + "</td><td>" + item.search_support + "</td><td>" + item.PriceSoPc + "</td><td><span>" + new Date(item.time).toLocaleString() + "</span></td></tr>";
                                    break;
                                case "神马":
                                    str += "<tr><td><span>" + item.website + "</span></td><td>" + item.keywords + "</td><td>" + item.firstRanking + "</td><td>" + item.newRanking + "</td><td>" + item.search_support + "</td><td>" + item.PriceSm + "</td><td><span>" + new Date(item.time).toLocaleString() + "</span></td></tr>";
                                    break;
                            }
                        });
                        str += "</tbody></table>";
                        layer.open({
                            type: 1,
                            title: '查看详情',
                            skin: 'layui-layer-molv',
                            shade: 0.6,
                            area: ['70%'],
                            content: str //注意，如果str是object，那么需要字符拼接。
                        });
                    });
                }
            };
            return oTableInit;
        },
        divideClick: function () {
            var selectContent = $('#myTable').bootstrapTable('getSelections');
            //console.info(selectContent);
            if (selectContent.length == 0) {
                layer.msg("请先选择行");
                return;
            }
            $.post(CTX + '/SiteLease/GetDistributor', {}, function (data) {

                var str = "<option value=\"0\">--请选择--</option>";
                $.each(data, function (index, item) {
                    str += "<option value=" + item.id + ">" + item.userName + "</option>";
                });
                $("#distributor").empty().append(str);
            });

            index1 = layer.open({
                type: 1,
                title: '划分订单 已选择行数：' + selectContent.length + '行',
                skin: 'layui-layer-molv',
                shade: 0.6,
                area: ['30%', '31%'],
                content: $('#selectDistributor'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                end: function () {
                    $("#selectDistributor").hide();
                }
            });
            return;
        },
        confirmDistributor: function () {
            if ($("#distributor").val() == "0") {
                layer.msg("请选择渠道商");
                return;
            }
            var selectContent = $('#myTable').bootstrapTable('getSelections');
            $.post(CTX + '/SiteLease/DivideOrder', {
                selectContent: selectContent,
                distributor: $("#distributor").val(),
                len: selectContent.length
            }, function (data) {
                if (data.code == "1") {
                    layer.msg("成功");
                    layer.close(index1);
                    $("#myTable").bootstrapTable("refresh");
                    return;
                }
                else if (data.code == "-1" || data.code == "-3") {
                    layer.msg(data.message);
                    return;
                }
                layer.msg("失败");
            });
        }
    };
</script>







</@base.html>