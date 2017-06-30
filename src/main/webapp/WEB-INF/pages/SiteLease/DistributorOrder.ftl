<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "写信息">
<link href="${ctx}/static/css/Message/font-awesome.min.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/custom.css" rel="stylesheet">
<#--<link href="${ctx}/static/css/Message/Index.css" rel="stylesheet">-->

<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>网站出租平台</span> > <span>任务大厅</span>
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
<div id="reserveDiv" style="display: none">
    <table class="table table-bordered table-striped" style="text-align: center;vertical-align: middle">
        <thead>
        <tr>
            <th>预定人</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="tbodyReserve">
        <tr>
            <td><span class="">张三</span></td>
            <td>
                <button class="btn btn-success form-control">充值</button>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<script type="text/javascript">
    var index1;
    var index2;
    $(function () {
        MissionHall.init();
    });
    var MissionHall = {
        init: function () {
            //1.初始化Table
            var oTable = new MissionHall.TableInit();
            oTable.Init();
        },
        TableInit: function () {
            var oTableInit = new Object();
            //初始化Table
            oTableInit.Init = function () {
                $('#myTable').bootstrapTable({
                    url: CTX + '/SiteLease/GetReceiveIdMission',         //请求后台的URL（*）
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
                            checkbox: true
                        },
                        {
                            field: 'id',
                            title: '编号',
                            align: 'center',
                            valign: 'middle',
                            visible: false

                        },
                        {
                            field: 'reserveId',
                            title: '预定人',
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
                                }
                                else if (value == "3") {
                                    return "已有预定";
                                }
                                else if (value == "4") {
                                    return "已完成预定";
                                } else if (value == "5") {
                                    return "等待客户订购";
                                } else if (value == "6") {
                                    return "客户已订购";
                                }
                                return value;
                            }
                        },
                        {
                            field: 'ydrs',
                            title: '预定人数',
                            align: 'center',
                            valign: 'middle',
                            formatter: function (value, row, index) {
                                if (row.orderstate == 3) {
                                    var len = row.reserveId.split(",").length - 1;
                                    return len;
                                }
                                return "-";
                            }
                        },
                        {
                            field: 'cz',
                            align: 'center',
                            valign: 'middle',
                            title: '操作',
                            events: operateEvents,
                            formatter: function (value, row, index) {
                                var a = "<a style='color: #a6cfff;cursor: pointer;' id='details'>详情</a>   ";
                                if (row.orderstate < 4) {
                                    a += "<a style='color: #AE5B1E; cursor:pointer;' id='recharge'>充值</a>"
                                }
                                if(row.orderstate >= 4) {
                                    a += "<a style='color: #AE5B1E; cursor:pointer;' id='WhoReserve'>预定人</a>"
                                }

                                return a;
                            }

                        }
                    ]
                });
            };
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
                },
                'click #recharge': function (e, value, row, index) {
                    $.post(CTX + '/SiteLease/GetReserve', {website: row.website}, function (data) {
                        var str = "";
                        $.each(data, function (index, item) {
                            str += "<tr>" +
                                    "<td style='vertical-align: middle;'>" + item.userName + "</td>" +
                                    "<td>" +
                                    "<button class=\"btn btn-success form-control\" onclick='MissionHall.Recharge(" + item.id + ",\"" + row.website + "\");'>充值</button>" +
                                    "</td></tr>";
                        });
                        $("#tbodyReserve").empty().append(str);
                        index1 = layer.open({
                            type: 1,
                            title: '查看详情',
                            skin: 'layui-layer-molv',
                            shade: 0.6,
                            area: ['20%'],
                            content: $("#reserveDiv"), //注意，如果str是object，那么需要字符拼接。
                            end: function () {
                                $("#reserveDiv").hide();
                            }
                        });
                    });
                },
                'click #WhoReserve': function (e, value, row, index) {
                    $.post(CTX + '/SiteLease/GetReserve', {website: row.website}, function (data) {
                        var str = "";
                        $.each(data, function (index, item) {
                            str += "<tr>" +
                                    "<td style='vertical-align: middle;'>" + item.userName + "</td>" +
                                    "<td>" +
                                    "无" +
                                    "</td></tr>";
                        });
                        $("#tbodyReserve").empty().append(str);
                        index1 = layer.open({
                            type: 1,
                            title: '查看详情',
                            skin: 'layui-layer-molv',
                            shade: 0.6,
                            area: ['20%'],
                            content: $("#reserveDiv"), //注意，如果str是object，那么需要字符拼接。
                            end: function () {
                                $("#reserveDiv").hide();
                            }
                        });
                    });
                }
            };
            return oTableInit;
        },
        Recharge: function (userid, website) {
            index2 = layer.prompt({title: '充值金额', formType: 3}, function (pass, index) {
                if (pass == '' || isNaN(pass) || parseFloat(pass) < 1000) {
                    layer.msg("输入的金额不能小于1000");
                    return;
                }
                $.post(CTX + "/SiteLease/Recharge", {
                    userid: userid,
                    website: website,
                    SumMoney: pass
                }, function (data) {
                    if (data.code == "1") {
                        layer.msg("成功");
                        layer.close(index1);
                        layer.close(index2);
                        $("#myTable").bootstrapTable("refresh");
                        return;
                    }
                    else if (data.code == "-1"||data.code == "-3"||data.code == "-4") {
                        layer.msg(data.message);
                        return;
                    }
                    else {
                        layer.msg("失败");
                        return;
                    }
                })
            })

        }
    };
</script>







</@base.html>