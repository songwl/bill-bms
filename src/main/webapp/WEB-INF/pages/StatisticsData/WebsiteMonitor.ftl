<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "网站监控">
<link href="${ctx}/static/css/Message/font-awesome.min.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/custom.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/Index.css" rel="stylesheet">

<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
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

    .news {
        margin-bottom: 1px;
        padding: 0px 10px;
        background: #fff;
        border-bottom: 1px solid #e7e7e7;
    }

    .news ul li {
        overflow: hidden;
        zoom: 1;
        background: #fff;
        height: 30px;
        line-height: 30px;
        padding-left: 5px;
        width: 100%
    }

    .news ul li .newsLt {
        width: 40%;
        float: left;
        color: #666;
        font-size: 12px;
        text-align: left;
    }

    .news ul li .newsLt i {
        font-style: normal;
        color: #ff0000;
    }

    .news ul li .newsLt b {
        color: #333;
    }

    .news ul li .newsRt {
        width: 60%;
        float: right;
        font-size: 12px;
        color: #8c8c8c;
        text-align: left;

    }

    .news ul li .newsRt b {
        vertical-align: 1px;
    }
</style>
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>网站出租平台</span> > <span>网站监控</span>
    </div>
    <div class="nav_R right" id="divQx">

    </div>
    <div class="cls">
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary" id="list-panel" style="border-color: #eeeeee;">
            <div class="panel-body">
                <div class="panel-nav">
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;网站监控
                    </div>
                    <div class="Nav_Right">


                    </div>
                    <div class="cls"></div>
                </div>
                <#if iscan=1>
                    <div class="row row-bg">
                        <div class="col-sm-6 col-md-2 hidden-xs">
                            <div class="dashboard-stat red">
                                <div class="visual">

                                    <i class="fa fa-user"></i>
                                </div>
                                <div class="details">
                                    <div class="number">操作员数</div>
                                    <div class="desc" id="UserCount"></div>
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6 col-md-2 hidden-xs">
                            <div class="dashboard-stat blue">
                                <div class="visual">

                                    <i class="fa fa-home"></i>
                                </div>
                                <div class="details">
                                    <div class="number">总词数</div>
                                    <div class="desc" id="WordCount"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-md-2 hidden-xs">
                            <div class="dashboard-stat blue">
                                <div class="visual">
                                    <i class="fa fa-dollar"></i>
                                </div>
                                <div class="details">
                                    <div class="number">总排名数</div>
                                    <div class="desc" id="RankingCount"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-md-2 hidden-xs">
                            <div class="dashboard-stat green">
                                <div class="visual">
                                    <i class="fa fa-dollar"></i>
                                </div>
                                <div class="details">
                                    <div class="number">首页率</div>
                                    <div class="desc" id="HomepagePro"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-md-2 hidden-xs">
                            <div class="dashboard-stat purple">
                                <div class="visual">
                                    <i class="fa fa-tasks"></i>
                                </div>
                                <div class="details">
                                    <div class="number">总前三数</div>
                                    <div class="desc" id="TopThreeCount"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-md-2 hidden-xs">
                            <div class="dashboard-stat blue">
                                <div class="visual">
                                    <i class="fa fa-tasks"></i>
                                </div>
                                <div class="details">
                                    <div class="number">前三占有率</div>
                                    <div class="desc" id="TopThreePro"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </#if>
                <table id="myTable" class="table table-striped  table-condensed table-responsive"
                       style="width:100%;font-size: 13px;font-family: " 微软雅黑
                "></table>

            </div>

        </div>
    </div>
</div>

<style type="text/css">
    td {
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
                    MissionHall.LoadFa();
                },
                TableInit: function () {
                    var oTableInit = new Object();
                    //初始化Table
                    oTableInit.Init = function () {
                        $('#myTable').bootstrapTable({
                                    url: CTX + '/StatisticsData/GetWebsiteMonitor',         //请求后台的URL（*）
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
                                            field: 'bill_ascription',
                                            align: 'center',
                                            valign: 'middle',
                                            title: '操作员',
                                            formatter: function (value, row, index) {
                                                return value;
                                            }
                                        },
                                        {
                                            field: 'KeywordNum',
                                            align: 'center',
                                            width: 200,
                                            valign: 'middle',
                                            title: '词数',
                                            //events: ReadMailEvents

                                        },
                                        {
                                            field: 'Ranking',
                                            title: '排名数',
                                            width: 200,
                                            align: 'center',
                                            valign: 'middle'
                                        },
                                        {
                                            field: 'syl',
                                            title: '首页率',
                                            align: 'center',
                                            valign: 'middle',
                                            formatter: function (value, row, index) {
                                                return parseInt((row.Ranking / row.KeywordNum).toFixed(2) * 100) + "%";
                                            }
                                        },
                                        {
                                            field: 'TopThree',
                                            title: '前三数',
                                            width: 200,
                                            align: 'center',
                                            valign: 'middle'
                                        },
                                        {
                                            field: 'qszyl',
                                            title: '前三占有率',
                                            align: 'center',
                                            valign: 'middle',
                                            formatter: function (value, row, index) {
                                                return parseInt(((row.TopThree / row.KeywordNum).toFixed(2) * 100)) + "%";
                                            },

                                        },
                                    ]
                                }
                        );
                    }
                    ;

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
                    return oTableInit;
                },
                LoadFa: function () {
                    $.ajax({
                        url: CTX + "/StatisticsData/LoadFa",
                        type: "post",
                        success: function (data) {
                            $("#UserCount").empty().text(data.UserNum);
                            $("#WordCount").empty().text(data.KeywordNum);
                            $("#RankingCount").empty().text(data.Rankingnum);
                            $("#HomepagePro").empty().text(parseInt((data.Rankingnum / data.KeywordNum).toFixed(2) * 100) + "%");
                            $("#TopThreeCount").empty().text(data.TopThreenum);
                            $("#TopThreePro").empty().text(parseInt((data.TopThreenum / data.KeywordNum).toFixed(2) * 100) + "%");
                        }
                    })
                }
            }
    ;
</script>







</@base.html>