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
        <div id="addBillByKehu">
            <span>&nbsp;<i class="fa fa-arrow-down"></i>&nbsp;导入</span>
        </div>

        <div class="search">
            <span>&nbsp;<i class="fa fa-search"></i>&nbsp;查询</span>
        </div>
    </div>
    <div class="cls">
    </div>
</div>
<div class="Mission-box">

    <table id="myTable" class="table table-striped "></table>

</div>
<script type="text/javascript">
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
                    url: CTX + '/SiteLease/GetMission',         //请求后台的URL（*）
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
                        console.info(data)
                        var str = "<table class=\"table table-hover table-striped table-bordered\">" +
                                "<thead><tr><th>网址</th><th>关键词</th><th>初排</th><th>新排</th><th>时间</th></thead><tbody>";
                        $.each(data, function (index, item) {
                            str += "<tr><td><span>" + item.website + "</span></td><td>" + item.keywords + "</td><td>" + item.firstRanking + "</td><td>" + item.newRanking + "</td><td><span>" + new Date(item.time).toLocaleString() + "</span></td></tr>";
                        });
                        str += "</tbody></table>";
                        layer.open({
                            type: 1,
                            title: '查看详情',
                            skin: 'layui-layer-molv',
                            shade: 0.6,
                            /*area: ['30%', '40%'],*/
                            content: str //注意，如果str是object，那么需要字符拼接。
                        });
                    });
                }
            };
            return oTableInit;
        },

        //得到查询的参数
        queryParams: function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   //页面大小
                offset: params.offset  //页码
            };
            return temp;
        }
    };
</script>







</@base.html>