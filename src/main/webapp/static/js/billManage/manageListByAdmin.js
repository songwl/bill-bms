
/**
 * Created by 鱼在我这里。 on 2017/3/16.
 */
var website = null;
var searchUserName = null;
//显示搜索内容
$(".search").click(function () {
    if($(".Navs2").css("display")=="block"){
        $(".Navs2").slideUp();

    }
    else
    {
        $(".Navs2").slideDown();

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

    if ($("#searchUserName").val() != "--请选择--") {
        searchUserName = $("#searchUserName").val();
    }
    else {
        searchUserName = null;
    }

    $('#myTable').bootstrapTable('refresh');
});

$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();



});
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#myTable').bootstrapTable({
            url:CTX+ '/billManage/manageListByAdminTable',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            //pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            //pageSize: 500,                       //每页的记录行数（*）
            //pageList: [500,1000],        //可供选择的每页的行数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            queryParams: oTableInit.queryParams,//传递参数（*）
            queryParamsType: "",
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            //height: 700,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "Id",                     //每一行的唯一标识，一般为主键列
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
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '序号',

                },
                {
                    field: 'sqlId',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: 'sql序号',
                    visible:false

                },
                {
                    field: 'userName',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '客户',

                },
                {
                    field: 'commissioner',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '操作员',

                },
                {
                    field: 'webSite',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '网址',


                }, {
                    field: 'billCount',
                    align: 'center',
                    valign: 'middle',
                    title: '任务数',

                },
                {
                    field: 'keywordsCompletionRate',
                    align: 'center',
                    valign: 'middle',
                    title: '完成率',
                    formatter:function (value,row,index) {
                        var a="";
                        if(value>60.00)
                        {
                            a="<span style='color:#ff0000;font-weight: 800;'>"+value+"%</span>";
                        }
                        else
                        {
                            a="<span>" +value+"%</span>";
                        }
                        return a;
                    }
                },
                {
                    field: 'optimizationDays',
                    align: 'center',
                    valign: 'middle',
                    title: '接单日期',

                },
/*
                {
                    field: 'dayConsumption',
                    align: 'center',
                    valign: 'middle',
                    title: '今日消费',

                },*/
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
            searchUserName: searchUserName,
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




