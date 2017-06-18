/**
 * Created by Administrator on 2017/6/6.
 */

//显示搜索内容
$(".search").click(function () {
    if ($(".Navs2").css("display") == "block") {
        $(".Navs2").slideUp();

    }
    else {
        $(".Navs2").slideDown();

    }
})
var searchTime=null;
$("#searchButton").click(function () {

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
            url:CTX+ '/billManage/performanceStatisticsTable',         //请求后台的URL（*）
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


