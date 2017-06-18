/**
 * Created by Administrator on 2017/3/16.
 */
var website=null;
var keywords=null;
var searchName=null;
var searchUserName=null;
var searchState=1;
$(document).ready(function () {
    //切换订单
    $(".pass").click(function () {
        $('.page-content').empty().load(CTX+'/order/pendingAuditView');
    })
    $(".pass1").click(function () {
        $('.page-content').empty().load(CTX+'/order/pendingAuditView1');
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
//删除订单
    $("#deleteBill").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        var index;
        if(selectContent == "") {
            layer.alert('请选择一列数据!', {
                skin: 'layui-layer-molv' //样式类名
                ,closeBtn: 0
            });

        }else{
            layer.confirm('是否删除订单？',{
                    btn:['确定','取消']
                },function () {
                    $.ajax({
                        type:'post',
                        url:CTX+'/order/deleteBillPendingAuditView',
                        data:{selectContent: selectContent, length: len},
                        beforeSend: function () {
                            index  = layer.load(1, {
                                shade: [0.1,'#fff'] //0.1透明度的白色背景
                            });
                        },
                        success:function (result) {
                            if (result.code == 200) {

                                layer.alert(result.message, {
                                    skin: 'layui-layer-molv' //样式类名
                                    ,closeBtn: 0
                                });
                                $('#myTable').bootstrapTable('refresh');

                            }
                            else {
                                layer.alert(result.message, {
                                    skin: 'layui-layer-molv' //样式类名
                                    ,closeBtn: 0
                                });

                            }
                            layer.close(index);
                        }

                    })
                }
            )


        }
    })

    //复选框
    $("#btn_update").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        if(selectContent == "") {
            alert('请选择一列数据!');

        }else{
            $(".modal-backdrop").show();
            $(".changepriceDiv").slideDown();

        }
    })
    $("#searchButton").click(function () {

        if($("#website").val()!="")
        {
            website=$("#website").val();
        }
        else
        {
            website=null
        }
        if($("#keywords").val()!="")
        {
            keywords=$("#keywords").val();
        }
        else
        {
            keywords=null;
        }
        if($("#searchName option:selected").text()!="--请选择--")
        {
            searchName=$("#searchName option:selected").text();
        }
        else
        {
            searchName=null;
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
            url: CTX+'/order/pendingAuditViewList',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 50,                       //每页的记录行数（*）
            pageList:[100,200,300,500,1000],        //可供选择的每页的行数（*）
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

                },{
                    field: 'id',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '数据库序号',
                    visible:false

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
                    field: "state",
                    align: 'center',
                    valign: 'middle',
                    title: '状态',
                    formatter:function (value,row,index) {
                        if(value==-1||value==-0||value==1)
                        {
                            var  a="<span style='color:#94b86e;'>待审核</span>";
                        }
                        else
                        {
                            var  a="<span style='color:#FF0000;'>未通过</span>";
                        }


                        return a;
                    }


                },

                /*  {
                 field: 'operate',
                 title: '操作',
                 align: 'center',
                 valign: 'middle',
                 formatter:function (value,row,index) {
                 var a="<span style='color:#4382CF;cursor:pointer;' id='details'>修改</span>";

                 return a;
                 },
                 events:operateEvents

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
            website:website,
            keywords:keywords,
            searchName:searchName,
            searchUserName:searchUserName,
            state:searchState,

        };
        return temp;
    }
    /*  window.operateEvents = {
     'click #details': function (e, value, row, index) {
     $("#billCostDetail").show();
     $(".modal-backdrop").show();

     }

     }*/


    return oTableInit;
};





$(function () {
    $("#queren").click(function () {

        $('#myTable').bootstrapTable('refresh');
    });

});
function detail( ) {
    var bid=$("input[name='Bid']").val();


}