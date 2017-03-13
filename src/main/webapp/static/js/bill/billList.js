$(document).ready(function () {
    //显示导入价格内容
    $(".Import").click(function (e) {
        if($(".ImportPrice").css("display")=="block") {
            $(".ImportPrice").slideUp();
        }
        else
        {
            $(".ImportPrice").slideDown();
            $(".samepriceDiv").slideUp();
            $(".differentpriceDiv").slideUp();
            $(".Navs2").slideUp();
        }
    })

    //相同价导入
    $("#Sameprice").click(function () {

        if($(".samepriceDiv").css("display")=="block") {
            $(".samepriceDiv").slideUp();

        }
        else {
            $(".samepriceDiv").slideDown();
            $(".differentpriceDiv").slideUp();
            $(".Navs2").slideUp();
        }

    })

    //不同价导入
    $("#Differentprice").click(function () {
        if($(".differentpriceDiv").css("display")=="block") {
            $(".differentpriceDiv").slideUp();

        }
        else {
            $(".differentpriceDiv").slideDown();
            $(".samepriceDiv").slideUp();
            $(".Navs2").slideUp();
        }


    })
    $(".close").click(function () {
        $(".samepriceDiv").slideUp();
        $(".differentpriceDiv").slideUp();
    })
    //显示搜索内容
    $(".search").click(function () {
        if($(".Navs2").css("display")=="block"){
            $(".Navs2").slideUp();

        }
        else
        {
            $(".Navs2").slideDown();
            $(".samepriceDiv").slideUp();
            $(".differentpriceDiv").slideUp();
            $(".ImportPrice").slideUp();
        }


    })

    //提交相同价订单
    $(".samepricecmt").click(function () {
        var search= $("#searchengineid ").val();
        var keyword= $("#keyword").val();
        var url= $("#url").val();
        var rankend=$("input[name='rankend']").val();
        var price=$("input[name='price']").val();
        var rankend1=$("input[name='rankend1']").val();
        var price1=$("input[name='price1']").val();
        var rankend2=$("input[name='rankend2']").val();
        var price2=$("input[name='price2']").val();
        var rankend3=$("input[name='rankend3']").val();
        var price3=$("input[name='price3']").val();
       $.ajax({
           type:"get",
           url:CTX+"/bill/list/sameprice",
           dataType:'json',
           contentType: "application/x-www-form-urlencoded; charset=utf-8",
           data:{
               search:search,
               keyword:keyword,
               url:url,
               rankend:rankend,
               price:price,
               rankend1:rankend1,
               price1:price1,
               rankend2:rankend2,
               price2:price2,
               rankend3:rankend3,
               price3:price3


               },
           beforeSend: function () {
               $("#pload").show();
           },

           success:function () {
               $("#pload").hide();
               alert("导入成功！");
           }

       })

    })





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
            url: '/bill/billList/getUser',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            queryParams: oTableInit.queryParams,//传递参数（*）
            queryParamsType: "",
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 600,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "Id",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            showExport: true,                     //是否显示导出
            exportDataType: "basic",
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
                    valign: 'middle',
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
                    title: '初排'

                },
                {
                    field: 'newRanking',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '新排',
                },
                {
                    field: 'firstPrice',
                    align: 'center',
                    valign: 'middle',
                    title: '价格1',

                },

                {
                    field: 'secondPrice',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '价格2',

                },
                {
                    field: 'dayOptimization',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '日优化',

                },
                {
                    field: 'allOptimization',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '总优化',

                },
                {
                    field: 'dayConsumption',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '当天消费',

                },
                {
                    field: "standardDays",
                    align: 'center',
                    valign: 'middle',
                    title: '达标天',

                },
                {
                    field: "monthConsumption",
                    align: 'center',
                    valign: 'middle',
                    title: '本月消费',

                },
                {
                    field: "OrderState",
                    align: 'center',
                    valign: 'middle',
                    title: '合作状态',

                },
                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    valign: 'middle',

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

        };
        return temp;
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
    $("#queren").click(function () {

        $('#myTable').bootstrapTable('refresh');
    });

});






