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
        <i class="fa fa-home">&nbsp;</i><span>公告系统</span> > <span>公告查询</span>
    </div>

    <div class="cls">
    </div>
</div>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12 animated fadeInRight">
            <div class="col-sm-12" style="padding-left: 0px;padding-right: 0px;">
                <div style="float: left">
                    <img src="${ctx}/static/img/bg_p4.gif"  class="usertx">
                </div>
                <div style="float: right">
                    <img src="${ctx}/static/img/bg_p3.gif"  class="usertx">
                </div>
                <div style="clear: both;"></div>
            </div>
            <div class="mail-box-header">

                <div class="pull-right mail-search">
                    <div class="input-group">
                        <input type="text" class="form-control input-sm" name="search" placeholder="搜索公告标题，正文等">
                        <div class="input-group-btn">
                            <button class="btn btn-sm btn-primary" id="Search">
                                搜索
                            </button>
                        </div>
                    </div>
                </div>
                <h2>
                    公告查询（已接收公告） <span id="MailAllNum"></span>
                </h2>
                <div class="mail-tools tooltip-demo m-t-md">
                    <div class="btn-group pull-right">
                        <#if loginUser.hasRole("SUPER_ADMIN")||loginUser.hasRole("DISTRIBUTOR")||loginUser.hasRole("AGENT")||loginUser.hasRole("ADMIN")>
                            <button class="btn btn-white btn-sm fsgg" data-toggle="0" onclick="loadSendOrReceive(0)">
                                <i class="fa fa-arrow-up">已发送公告</i>
                            </button>
                        </#if>

                    </div>
                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" id="refresh"
                            title="刷新公告列表"><i class="fa fa-refresh"></i> 刷新
                    </button>
                </div>
            </div>
            <div class="mail-box">


                <table id="myTable" class="table table-striped "></table>


            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    function MailNum() {
        $.ajax({
            url: CTX+"/Message/MailNum",
            success: function (data) {
                $("#MailNum").text(data);
            }
        })
    }
    //setInterval('MailNum()', 500);
    function ReMailNum() {
        $.ajax({
            url: CTX+"/Message/ReMailNum",
            success: function (data) {
                $("#MailAllNum").text(data.message);
            }
        })
    }
    //setInterval('ReMailNum()', 500);
    function MailAllNum() {
        $.ajax({
            url: CTX+"/Message/SendMailAllNum",
            success: function (data) {
                $("#ReMailNum").text(data.message);
            }
        })
    }
    //setInterval('MailAllNum()', 500);

    var SearchContent = "";
    $(function () {
        /*MailNum();
        ReMailNum();
        MailAllNum();*/
        $("#Search").click(function () {
            SearchContent = $("[name='search']").val();
            num = $("[check]").attr("data-toggle");
            $("#myTable").bootstrapTable('refresh');
        });
        $("#refresh").click(function () {
            SearchContent = $("[name='search']").val();
            num = $("[check]").attr("data-toggle");
            $("#myTable").bootstrapTable('refresh');
        });
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

        //2.初始化Button的点击事件
        var oButtonInit = new ButtonInit();
        oButtonInit.Init();

    });
    //0是发送 1是接收
    function loadSendOrReceive(nn) {
        $(".page-content").empty().load(CTX+"/Message/NoticeSearch").fadeIn(1000);
    }

    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#myTable').bootstrapTable({
                url: CTX + '/Message/GetNoticeSearch',         //请求后台的URL（*）
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

                columns: [{
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
                        field: 'title',
                        align: 'left',
                        valign: 'middle',
                        title: '标题',
                        formatter: function (value, row, index) {

                            var a = "";
                            a = '<span class="title" style="color: #1e7bb4;font-weight: bold;cursor:pointer;">' + value + '</span>';

                            return a;
                        },
                        events: ReadMailEvents

                    },
                    {
                        field: 'sendtime',
                        title: '日期',
                        width: 200,
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value, row, index) {
                            var date = new Date(value);
                            return date.toLocaleString();
                        }
                    },
                ]
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   //页面大小
                offset: params.offset,  //页码
                type: 1,
                SearchContent: SearchContent
            };
            return temp;
        }
        window.ReadMailEvents = {
            'click .title': function (e, value, row, index) {
                $(".page-content").empty().load(CTX+"/Message/ReadNoticeReceiveContent?NoticeId=" + row.id).fadeIn(1000);
                //window.location.href = "/Message/ReadMail?MailId=" + row.id + "&StateId=" + StateId;

            }

        }

        return oTableInit;
    };


    var ButtonInit = function () {
        var oInit = new Object();
        var postdata = {};

        oInit.Init = function () {

        };

        return oInit;
    };
    function operation(types) {
        var allTableData = $('#myTable').bootstrapTable('getSelections');//获取表格的所有内容行
        console.info(allTableData);
        $.ajax({
            type: 'get',
            url: CTX + '/Message/GoOperation',
            data: {data: allTableData, type: types},
            success: function (data) {
                if (data.message != "1") {
                    alert("失败！");
                }
                $("#myTable").bootstrapTable('refresh');
                return;
            }
        });
    }
</script>

</@base.html>
