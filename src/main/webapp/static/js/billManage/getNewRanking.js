
/**
 * Created by 鱼在我这里。 on 2017/3/16.
 */
//显示搜索内容
var website=null;
var keywords=null;
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
    if($("#website").val()!="")//网址
    {
        website=$.trim($("#website").val());
    }
    else
    {
        website=null
    }
    if($("#keywords").val()!="")//关键词
    {
        keywords=$.trim($("#keywords").val())
    }
    else
    {
        keywords=null;
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
            url:CTX+ '/billManage/getNewRankingTable',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 20,                       //每页的记录行数（*）
            pageList: [20, 50, 100],        //可供选择的每页的行数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            queryParams: oTableInit.queryParams,//传递参数（*）
            queryParamsType: "",
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 700,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "Id",                     //每一行的唯一标识，一般为主键列
            exportDataType: "basic",
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
                    field: 'displayId',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '序号',
                    width:100

                },
                {
                    field: 'id',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: 'sql序号',
                    visible:false

                },
                {
                    field: 'website',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '网址',


                },
                {
                    field: 'keywords',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '关键词',

                },
                {
                    field: 'newRanking',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '最新排名',
                    width:100

                }, {
                    field: 'changeRanking',
                    align: 'center',
                    valign: 'middle',
                    title: '新增排名',
                    width:100
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
            website:website,
            keywords:keywords,

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




