<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "写信息">
<link href="${ctx}/static/css/Message/font-awesome.min.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/custom.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/Index.css" rel="stylesheet">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-3">
            <div class="ibox float-e-margins">
                <div class="ibox-content mailbox-content">
                    <div class="file-manager">
                        <a class="btn btn-block btn-primary compose-mail" href="#" onclick="$('.page-content').empty().load(CTX+'/Message/WriteMail');">写信</a>
                        <div class="space-25"></div>
                        <h5>文件夹</h5>
                        <ul class="folder-list m-b-md" style="padding: 0">
                            <li>
                                <a href="/Mail/Index">
                                    <i class="fa fa-inbox "></i> 收件箱 <span class="label label-warning pull-right"
                                                                           id="MailNum">0</span>
                                </a>
                            </li>
                            <li>
                                <a href="#" onclick="$('.page-content').empty().load(CTX+'/Message/SendBox');"> <i class="fa fa-envelope-o"></i>
                                    发件箱<span class="label label-warning pull-right" id="ReMailNum">0</span>
                                </a>
                            </li>
                            <li>
                                <a href="#"> <i class="fa fa-certificate"></i> 重要</a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa fa-file-text-o"></i> 草稿 <span
                                        class="label label-danger pull-right">0</span>
                                </a>
                            </li>
                            <li>
                                <a href="mailbox.html"> <i class="fa fa-trash-o"></i> 垃圾箱</a>
                            </li>
                        </ul>
                        <h5>分类</h5>
                        <ul class="category-list" style="padding: 0">
                            <li>
                                <a href="#"> <i class="fa fa-circle text-navy"></i> 工作</a>
                            </li>
                            <li>
                                <a href="#"> <i class="fa fa-circle text-danger"></i> 文档</a>
                            </li>
                            <li>
                                <a href="#"> <i class="fa fa-circle text-primary"></i> 社交</a>
                            </li>
                            <li>
                                <a href="#"> <i class="fa fa-circle text-info"></i> 广告</a>
                            </li>
                            <li>
                                <a href="#"> <i class="fa fa-circle text-warning"></i> 客户端</a>
                            </li>
                        </ul>


                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-9 animated fadeInRight">
            <div class="mail-box-header">

                <form method="get" action="index.html" class="pull-right mail-search">
                    <div class="input-group">
                        <input type="text" class="form-control input-sm" name="search" placeholder="搜索邮件标题，正文等">
                        <div class="input-group-btn">
                            <button type="submit" class="btn btn-sm btn-primary">
                                搜索
                            </button>
                        </div>
                    </div>
                </form>
                <h2>
                    发件箱 (<span id="MailAllNum">0</span>)
                </h2>
                <div class="mail-tools tooltip-demo m-t-md">
                    <div class="btn-group pull-right">
                        <button class="btn btn-white btn-sm">
                            <i class="fa fa-arrow-left"></i>
                        </button>
                        <button class="btn btn-white btn-sm">
                            <i class="fa fa-arrow-right"></i>
                        </button>

                    </div>
                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" id="refresh"
                            title="刷新邮件列表"><i class="fa fa-refresh"></i> 刷新
                    </button>
                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top"
                            onclick="operation(1);" id="updateread"
                            title="标为已读">
                        <i class="fa fa-eye"></i>
                    </button>
                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top"
                            onclick="operation(2);" title="标为重要">
                        <i class="fa fa-exclamation"></i>
                    </button>
                    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top"
                            onclick="operation(3);" title="标为垃圾邮件">
                        <i class="fa fa-trash-o"></i>
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


    $(function () {
        MailNum();
        ReMailNum();
        MailAllNum();
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
                url: CTX + '/Message/GetSendBox',         //请求后台的URL（*）
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
                        field: 'inuserid',
                        title: '收件人',
                        align: 'center',
                        valign: 'middle',
                        width: 70

                    },

                    {
                        field: 'title',
                        align: 'center',
                        valign: 'middle',
                        title: '标题',
                        formatter: function (value, row, index) {

                            var a = "";
                            a = '<span class="title" style="cursor:pointer;">' + value + '</span>';

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
                            var date=new Date(value);
                            return date.toLocaleString();
                        }
                    },

                    {
                        field: 'dealtstate',
                        title: '状态',
                        align: 'center',
                        valign: 'middle',
                        width: 70,
                        formatter: function (value, row, index) {
                            var a = "";
                            if (value == 0) {
                                a = '<span class="label label-success StateId" data-state="0">未处理</span>';
                            }
                            if (value == 1) {
                                a = '<span class="label label-default StateId" data-state="1">已处理</span>';
                            }

                            return a;

                        },

                    },


                ]
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   //页面大小
                offset: params.offset  //页码
            };
            return temp;
        }
        window.ReadMailEvents = {
            'click .title': function (e, value, row, index) {
                var StateId = $(".StateId").attr("data-state");
                $(".page-content").empty().load(CTX+"/Message/ReadMail?MailId=" + row.id + "&StateId=" + StateId).fadeIn(1000);
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
    $(function () {
        $("#refresh").click(function () {
            $("#myTable").bootstrapTable('refresh');
        });
        /* $("#updateread").click(operation(1));*/
    });
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
