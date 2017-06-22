var website = null;
var keywords = null;
var searchName = null;
var searchUserName = null;
var searchState = 2;
var searchState2 = null;
var searchStandard = null;
var standardDays = null;
var createTime = null;
var length;
    $(function () {
        if ($("#way").val() == 1) {
            $("#applyStopBill").show();
        }
        else {
            $("#applyStopBill").hide();
        }
    })

    $(".close").click(function () {

        $(".modal-backdrop").hide();
        $("#billCostDetail").hide();
        $(".changepriceDiv").slideUp();


    })
    $(".cancel").click(function () {

        $(".modal-backdrop").hide();
        $("#billCostDetail").hide();
        $(".changepriceDiv").slideUp();


    })
/*    //显示搜索内容
    $(".search").click(function () {
        if ($(".Navs2").css("display") == "block") {
            $(".Navs2").slideUp();
        }
        else {
            $(".Navs2").slideDown();
        }
    })*/
    //今日达标
    $("#searchState").change(function () {
        var state = $("#searchState option:selected").val();
        if (state == 1) {
            searchState = 2;
        }
        else if (state == 2) {
            searchState = 2;
        }
        else {
            searchState = 3;
            searchState2 = null;
        }
    })
    //复选框
    $("#btn_update").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        if (selectContent == "") {
            alert('请选择一列数据!');

        } else {
            $(".modal-backdrop").show();
            $(".changepriceDiv").slideDown();

        }
    })
    //搜索按钮
    $("#searchButton").click(function () {
        if ($("#website").val() != "")//网址
        {
            website = $.trim($("#website").val());
        }
        else {
            website = null
        }
        if ($("#keywords").val() != "")//关键词
        {
            keywords = $.trim($("#keywords").val())
        }
        else {
            keywords = null;
        }

        if ($("#searchName option:selected").text() != "--请选择--") {
            searchName = $("#searchName option:selected").text();
        }
        else {
            searchName = null;
        }
        if ($("#searchUserName").val() != "--请选择--") {
            searchUserName = $("#searchUserName").val();
        }
        else {
            searchUserName = null;
        }
        if ($("#searchStandard option:selected").val() != "--请选择--" && $("#searchStandard option:selected").val() != "0") {
            searchStandard = $("#searchStandard").val();
        }
        else {
            searchStandard = null;
        }

        if ($("#standardDays").val() != "")//关键词
        {
            standardDays = $.trim($("#standardDays").val())
        }
        else {
            standardDays = null;
        }
        if ($("#createTime").val() != "")//关键词
        {
            createTime = $.trim($("#createTime").val())
        }
        else {
            createTime = null;
        }
        $('#myTable').bootstrapTable('refresh');
    });

$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    var oTable1 = new TableInit1();
    oTable1.Init();
    //2.初始化Button的点击事件
    /*  var oButtonInit = new ButtonInit();
     oButtonInit.Init();*/

});
var TableInit = function () {
    var oTableInit = new Object();
    console.log(oTableInit);
    //初始化Table
    oTableInit.Init = function () {
        $('#myTable').bootstrapTable({
            url: CTX + '/billManage/getStandardBillTable',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 50,                       //每页的记录行数（*）
            pageList: [100, 200, 300, 500, 1000],        //可供选择的每页的行数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            queryParams: oTableInit.queryParams,//传递参数（*）
            queryParamsType: "",
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            //height: 700,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "Id",                     //每一行的唯一标识，一般为主键列
            showExport: true,  //是否显示导出
            exportDataType: "basic",
            rowStyle: function (row, index) {
                //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                var strclass = "";
                if ((row.id) % 2 == 0) {
                    strclass = '';
                }
                else {
                    strclass = 'active';
                }
                return {classes: strclass}
            },
            columns: [
                {
                    checkbox: true
                },
                {
                    field: 'displayId',
                    align: 'center',
                    valign: 'middle',
                    title: '序号',

                },
                {
                    field: 'id',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '数据库序号',
                    visible: false
                },
                {
                    field: 'userName',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '客户',

                },
                {
                    field: 'keywords',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '关键词'
                }, {
                    field: 'website',
                    align: 'center',
                    sortable: true,
                    title: '网址',

                }, {
                    field: 'searchName',
                    align: 'center',
                    sortable: true,
                    valign: 'middle',
                    title: '搜索引擎'
                },
                {
                    field: 'createTime',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '增加时间'
                },
                {
                    field: 'firstRanking',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '初排',
                    formatter: function (value, row, index) {
                        var a = "";
                        if (value > 50) {
                            a = "<span>" + ">50" + "</span>";
                        }
                        else {
                            a = "<span>" + value + "</span>";
                        }
                        return a;
                    }
                },
                {
                    field: 'newRanking',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '新排',
                    formatter: function (value, row, index) {
                        var a = "";
                        if (value <= 3) {
                            a = "<span style='color:#FF0000;font-weight: bold;'>" + value + "</span>";
                        }
                        else if (value > 50) {
                            a = "<span>" + ">50" + "</span>";
                        }
                        else {
                            a = "<span>" + value + "</span>";
                        }
                        return a;
                    }


                },

                {
                    field: "standardDays",
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '达标天',

                },
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
            website: website,
            keywords: keywords,
            searchName: searchName,
            searchUserName: searchUserName,
            state: 2,
            state2: searchState2,
            searchStandard: null,
            standardDays: 30,
            createTime: createTime
        };
        return temp;
    }



    return oTableInit;
    /*  e.preventDefault();
     var url = this.href;
     if (url != null && url != 'javascript:;') {
     $.get(url, function (data) {
     $('.page-content').html(data);
     });
     }*/
};






$(function () {
    $("#queren").click(function () {

        $('#myTable').bootstrapTable('refresh');
    });

});








