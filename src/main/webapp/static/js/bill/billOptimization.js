/**
 * Created by 鱼在我这里。 on 2017/3/19.
 */
var website=null;
var keywords=null;
var searchName=null;
var searchUserName=null;
var searchState=2;
var searchStandard=null;
$(document).ready(function () {

    //复选框
    $("#OptimizationUpdate").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        if(selectContent == "") {
            alert('请选择一列数据!');

        }else{
            $(".modal-backdrop").show();
            $(".OptimizationUpdateDiv").slideDown();

        }
    })
    $(".close").click(function () {
        $(".modal-backdrop").hide();
        $(".OptimizationUpdateDiv").slideUp();
    })
    $(".cancel").click(function () {
        $(".modal-backdrop").hide();
        $(".OptimizationUpdateDiv").slideUp();
    })
    //调整优化
    $(".OptimizationUpdatecmt").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len =selectContent.length;
            if($("#OptimizationUpdateSelect option:selected").val()!="--请选择--")
            {
               var num= $("#OptimizationUpdateSelect option:selected").val();
                $.ajax({
                    type:"post",
                    url:CTX+"/bill/OptimizationUpdate",
                    data:{selectContent:selectContent,length:len,num:num},
                    success:function (result) {
                          if(result.code==200)
                          {
                              alert(result.message);
                              $('#myTable').bootstrapTable('refresh');
                              $(".modal-backdrop").hide();
                              $(".OptimizationUpdateDiv").slideUp();

                          }
                    }
                })
            }
            else
            {
                alert("请选择优化指数");
            }

    })

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
        if($("#searchName option:selected").text()!="--请选择--")//搜索引擎
        {
            searchName=$("#searchName option:selected").text();
        }
        else
        {
            searchName=null;
        }
        if($("#searchUserName").val()!="--请选择--")//客户名称
        {
            searchUserName=  $("#searchUserName").val();
        }
        else
        {
            searchUserName=null;
        }
        if($("#searchStandard option:selected").val()!="--请选择--"&&$("#searchStandard option:selected").val()!="0")//是否达标
        {
            searchStandard=  $("#searchStandard").val();
        }
        else
        {
            searchStandard=null;
        }
        $('#myTable').bootstrapTable('refresh');
    });


})
$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    var oTable1 = new TableInit();
    oTable1.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();


});
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#myTable').bootstrapTable({
            url: CTX+'/bill/getBillOptimization',         //请求后台的URL（*）
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
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 700,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "Id",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            showExport: true,                     //是否显示导出
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
                },
                {
                    field: 'displayId',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '序号',

                },
                {
                    field: 'id',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '序号',
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
                    title: '初排',
                    formatter:function (value,row,index) {
                        var a="";
                        if(value>50)
                        {
                            a="<span>" +">50"+"</span>";
                        }
                        else
                        {

                            a="<span>"+value+"</span>";
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
                    formatter:function (value,row,index) {
                        var a="";
                        if(value<=3)
                        {
                            a="<span style='color:#FF0000;font-weight: bold;'>" +value+"</span>";
                        }
                        else if(value>50)
                        {
                            a="<span>" +">50"+"</span>";
                        }
                        else
                        {
                            a="<span>" +value+"</span>";
                        }
                        return a;
                    }


                },
                {
                    field: 'billPriceList',
                    align: 'center',
                    valign: 'middle',
                    title: '价格1',
                    formatter:function (value,row,index) {
                        var a="";
                        a=value[0]["price"];
                        return a;
                    }

                },

                {
                    field: 'billPriceList',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '价格2',
                    formatter:function (value,row,index) {
                        var a="";
                        if(value[1]!=null)
                        {
                            a=value[1]["price"];
                        }
                        else
                        {
                            a="-";
                        }

                        return a;
                    }

                },
                {
                    field: "standardDays",
                    align: 'center',
                    valign: 'middle',
                    title: '达标天',

                },

                {
                    field: "dayOptimization",
                    align: 'center',
                    valign: 'middle',
                    title: '日优化',

                },
                {
                    field: "allOptimization",
                    align: 'center',
                    valign: 'middle',
                    title: '总优化',

                },
                {
                    field: "state",
                    align: 'center',
                    valign: 'middle',
                    title: '合作状态',
                    formatter:function (value,row,index) {
                        var a="";
                        if(value==2)
                        {
                            a="<span style='color:#94b86e;'>优化中</span>";
                        }
                        else
                        {
                            a="<span>合作停</span>";
                        }
                        return a;
                    }


                },
                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    valign: 'middle',
                    formatter:function (value,row,index) {
                        var a="<span style='color:#4382CF;cursor:pointer;' id='details'>详情</span>";

                        return a;
                    },
                    events:operateEvents

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
            website:$("#website").val(),
            way:2,
            website:website,
            keywords:keywords,
            searchName:searchName,
            searchUserName:searchUserName,
            state:searchState,
            searchStandard:searchStandard
        };
        return temp;
    }
    window.operateEvents = {
        'click #details': function (e, value, row, index) {
            $("#billCostDetail").show();
            $(".modal-backdrop").show();

        }

    }


    return oTableInit;
};





$(function () {
    $("#queren").click(function () {

        $('#myTable').bootstrapTable('refresh');
    });

});








