<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "写信息">
<link href="${ctx}/static/css/Message/font-awesome.min.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/custom.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/Index.css" rel="stylesheet">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12 animated fadeInRight">
            <div class="mail-box-header">

                <div class="pull-right mail-search">
                    <div class="input-group">
                        <input type="text" class="form-control input-sm" name="search" placeholder="搜索公告标题，正文等">
                        <div class="input-group-btn">
                            <button class="btn btn-sm btn-primary" id="Search">
                                <i class="glyphicon glyphicon-search"></i>
                                搜索
                            </button>
                        </div>
                    </div>
                </div>
                <h2>
                    反馈查询 <span id="MailAllNum"></span>
                </h2>
                <div class="mail-tools tooltip-demo m-t-md">
                    <div class="btn-group pull-right">

                        <#if !loginUser.hasRole("SUPER_ADMIN")&&!loginUser.hasRole("SECRETARY")>
                            <button class="btn btn-white btn-sm fsgg" data-toggle="0" check=""
                                    onclick="loadSendOrReceive(0)"
                                    style="background: #eee;">
                                <i class="fa fa-arrow-up">发送反馈</i>
                            </button>
                        </#if>
                        <#if loginUser.hasRole("DISTRIBUTOR")||loginUser.hasRole("SUPER_ADMIN")||loginUser.hasRole("ADMIN")||loginUser.hasRole("COMMISSIONER")||loginUser.hasRole("AGENT")||loginUser.hasRole("SECRETARY")>
                            <button class="btn btn-white btn-sm jsgg" data-toggle="1" check=""
                                    onclick="loadSendOrReceive(1)">
                                <i class="fa fa-arrow-down">接收反馈</i>
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
            url: "/Message/MailNum",
            success: function (data) {
                $("#MailNum").text(data);
            }
        })
    }
    //setInterval('MailNum()', 500);
    function ReMailNum() {
        $.ajax({
            url: "/Message/ReMailNum",
            success: function (data) {
                $("#MailAllNum").text(data.message);
            }
        })
    }
    //setInterval('ReMailNum()', 500);
    function MailAllNum() {
        $.ajax({
            url: "/Message/SendMailAllNum",
            success: function (data) {
                $("#ReMailNum").text(data.message);
            }
        })
    }
    //setInterval('MailAllNum()', 500);

    var num = 1;
        <#if !loginUser.hasRole("SUPER_ADMIN")&&!loginUser.hasRole("SECRETARY")>
        num = 0;
        </#if>
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
        if (nn == 0) {
            $(".fsgg").css("background", "#eee").attr("check", "");
            $(".jsgg").css("background", "#fff").removeAttr("check");
        }
        else {
            $(".jsgg").css("background", "#eee").attr("check", "");
            $(".fsgg").css("background", "#fff").removeAttr("check");
        }
        SearchContent = "";
        num = nn;
        $("#myTable").bootstrapTable('refresh');
    }

    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#myTable').bootstrapTable({
                url: CTX + '/Message/GetFeedbackSearch',         //请求后台的URL（*）
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
                        field: 'affairstate',
                        align: 'center',
                        valign: 'middle',
                        title: '问题类型',
                        formatter: function (value, row, index) {
                            if (value == "1") {
                                return "优化问题";
                            }
                            else if (value == "2") {
                                return "报价问题";
                            }
                            else {
                                return "其他问题";
                            }
                        },
                    },
                    {
                        field: 'title',
                        align: 'left',
                        valign: 'middle',
                        title: '标题',
                        formatter: function (value, row, index) {
                            var a = "";
                            if ((row.senduserid != ${loginUser.id} && row.dealtstate == 3) || (row.senduserid == ${loginUser.id} && row.dealtstate == 2)) {
                                a = '<span class="title" style="color:red;cursor:pointer;font-weight: bold;">' + value + '</span>';
                            }
                            else {
                                a = '<span class="title" style="cursor:pointer;color: #1e7bb4;font-weight: bold;">' + value + '</span>';
                            }
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
                    {
                        field: 'zt',
                        title: '状态',
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value, row, index) {
                            var a = "";
                            if ((row.senduserid != ${loginUser.id} && row.dealtstate == 3) || (row.senduserid == ${loginUser.id} && row.dealtstate == 2)) {
                                a = '<span style="display: inline-block;padding: 1px 8px;color: #fff;border-radius: 5px;background: #51a351;" data-state="0">未查看</span>';
                            }
                            else if (row.dealtstate == 4) {
                                a = '<span style="display: inline-block;padding: 1px 8px;color: #dddddd;border-radius: 5px;background: #fff;" data-state="0">已结束</span>';
                            }
                            else {
                                a = '<span style="display: inline-block;padding: 1px 8px;color: #008040;border-radius: 5px;background: #fff;" data-state="1">已查看</span>';
                            }
                            return a;
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
                type: num,
                SearchContent: SearchContent
            };
            return temp;
        }
        window.ReadMailEvents = {
            'click .title': function (e, value, row, index) {
                $(".page-content").empty().load(CTX + "/Message/ReadFeedback?FeedbackId=" + row.id).fadeIn(1000);
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
