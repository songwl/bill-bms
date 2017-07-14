
var website = null;
var keywords = null;
var searchName = null;
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
    $('#myTable').bootstrapTable('refresh');
});
//切换订单页面
$("#pass").click(function () {
    $('.page-content').empty().load(CTX+'/order/billApplyStopPass');
})
$("#pass1").click(function () {
    $('.page-content').empty().load(CTX+'/order/billApplyToOptimization');
})

//申请优化审核
$("#applyPass").click(function () {
    var selectContent = $('#myTable').bootstrapTable('getSelections');
    var len =selectContent.length;
    if(selectContent == "") {
        layer.alert('请选择一列数据', {
            skin: 'layui-layer-molv' //样式类名  自定义样式
            ,anim: 6 //动画类型
            ,icon: 4   // icon
        });

    }else{
        layer.confirm('是否通过申请优化？',{
            btn:['确定','取消']
        },function () {
            $.ajax({
                type:"post",
                url:CTX+"/order/billApplyToOptimizationPass",
                data:{ selectContent:selectContent,length:len},
                success:function (result) {
                    if(result.code==200)
                    {
                        $('#myTable').bootstrapTable('refresh');
                    }
                    layer.alert(result.message, {
                        skin: 'layui-layer-molv' //样式类名
                        ,closeBtn: 0
                    })
                }

            })

        })


    }
})
//申请停单不通过
$("#applyNotPass").click(function () {
    var selectContent = $('#myTable').bootstrapTable('getSelections');
    var len =selectContent.length;
    if(selectContent == "") {
        alert('请选择一列数据!');

    }else{
        if(confirm("是否不通过审核?"))
        {
            $.ajax({
                type:"post",
                url:CTX+"/order/applyStopBillNotPass",
                data:{ selectContent:selectContent,length:len},
                success:function (result) {
                    if(result.code==200)
                    {
                        alert(result.message);
                        $('#myTable').bootstrapTable('refresh');
                    }
                    else
                    {
                        alert(result.message);
                    }
                }

            })
        }
    }
})


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
            url: CTX+'/order/billApplyToOptimizationTable',         //请求后台的URL（*）
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
                    sortable: true,

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
                    field: "standardDays",
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '达标天',

                },
                {
                    field: "opstate",
                    align: 'center',
                    valign: 'middle',
                    title: '状态',
                    formatter:function (value,row,index) {
                        var a="";

                        a="<span style='color:#94b86e;'>申请优化中</span>";

                        return a;
                    }
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
