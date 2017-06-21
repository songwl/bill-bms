/**
 * Created by Administrator on 2017/6/13.
 */
//切换订单
$(".pass").click(function () {
    $('.page-content').empty().load(CTX+'/order/pendingAuditView');
})
$(".pass1").click(function () {
    $('.page-content').empty().load(CTX+'/order/pendingAuditView1');
})
//审核弹窗
$("#billExamine").click(function () {

    var selectContent = $('#myTable').bootstrapTable('getSelections');
    if (selectContent == "") {
        alert('请选择一列数据!');

    } else {
        $(".billExamineDiv").slideDown();
        $(".modal-backdrop").show();
    }
})

//审核不通过
$("#billNotExamine").click(function () {
    var selectContent = $('#myTable').bootstrapTable('getSelections');
    var len = selectContent.length;
    var index;
    if (selectContent=="")
    {
        layer.alert('请选择一列数据', {
            skin: 'layui-layer-molv' //样式类名  自定义样式
            ,anim: 6 //动画类型
            ,icon: 4   // icon

        });
    }
    else
    {
        layer.confirm('是否不通过审核？',{
                btn:['确定','取消']
            },function () {
                $.ajax({
                    type:'post',
                    data: {selectContent: selectContent, length: len},
                    url:CTX+'/order/billNotExamine',
                    beforeSend: function () {
                        index  = layer.load(1, {
                            shade: [0.1,'#fff'] //0.1透明度的白色背景
                        });
                    },
                    success: function (result) {
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
//审核订单提交
//渠道商审核订单
$(".qudaoShenhe").click(function () {

    var rankend = parseInt(jQuery("input[name='rankend']").val());
    var price = parseFloat(jQuery("input[name='price']").val());
    var rankend1 = parseInt(jQuery("input[name='rankend1']").val());
    var price1 = parseFloat(jQuery("input[name='price1']").val());
    var rankend2 = parseInt(jQuery("input[name='rankend2']").val());
    var price2 = parseFloat(jQuery("input[name='price2']").val());
    var rankend3 = parseInt(jQuery("input[name='rankend3']").val());
    var price3 = parseFloat(jQuery("input[name='price3']").val());
    if (!isNaN(rankend) && !isNaN(rankend1) && !isNaN(rankend2) && !isNaN(rankend3)
        && !isNaN(price) && !isNaN(price1) && !isNaN(price2) && !isNaN(price3)
        && rankend > 0 && rankend1 > rankend && rankend2 > rankend1 && rankend3 > rankend2 && 51 > rankend3
        && 1000 > price && price > price1 && price1 > price2 && price2 > price3 && price3 > 0
        ||
        !isNaN(rankend) && !isNaN(rankend1) && !isNaN(rankend2) && isNaN(rankend3)
        && !isNaN(price) && !isNaN(price1) && !isNaN(price2) && isNaN(price3)
        && rankend > 0 && rankend1 > rankend && rankend2 > rankend1 && 51 > rankend2
        && 1000 > price && price > price1 && price1 > price2 && price2 > 0
        ||
        !isNaN(rankend) && !isNaN(rankend1) && isNaN(rankend2) && isNaN(rankend3)
        && !isNaN(price) && !isNaN(price1) && isNaN(price2) && isNaN(price3)
        && rankend > 0 && rankend1 > rankend && 51 > rankend1
        && 1000 > price && price > price1 && price1 > 0
        ||
        !isNaN(rankend) && isNaN(rankend1) && isNaN(rankend2) && isNaN(rankend3)
        && !isNaN(price) && isNaN(price1) && isNaN(price2) && isNaN(price3)
        && rankend > 0 && 51 > rankend
        && 1000 > price && price > 0
    )
    {

        var rankendA = parseInt(jQuery("input[name='rankend']").val());
        var priceA = parseFloat(jQuery("input[name='price']").val());
        var rankend1A = parseInt(jQuery("input[name='rankend1']").val());
        var price1A = parseFloat(jQuery("input[name='price1']").val());
        var rankend2A = parseInt(jQuery("input[name='rankend2']").val());
        var price2A = parseFloat(jQuery("input[name='price2']").val());
        var rankend3A = parseInt(jQuery("input[name='rankend3']").val());
        var price3A = parseFloat(jQuery("input[name='price3']").val());
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len =selectContent.length;

        $.ajax({
            type:'post',
            url:CTX+"/order/pendingAuditView1ListCmt",
            dataType:'json',
            data:{
                rankend:rankendA,
                price:priceA,
                rankend1:rankend1A,
                price1:price1A,
                rankend2:rankend2A,
                price2:price2A,
                rankend3:rankend3A,
                price3:price3A,
                selectContent:selectContent,
                checkboxLength:len
            },
            beforeSend: function () {
                $(".pload").show();
                $('.qudaoShenhe').attr('disabled',"true");
            },
            success:function (result) {
                if(result.code==200)
                {
                    alert(result.message);
                    $(".billExamineDiv").slideUp();
                    $(".modal-backdrop").hide();
                    $(".pload").hide();
                    $('#myTable').bootstrapTable('refresh');
                    jQuery("input[name='rankend']").val("");
                    jQuery("input[name='price']").val("");
                    jQuery("input[name='rankend1']").val("");
                    jQuery("input[name='price1']").val("");
                    jQuery("input[name='rankend2']").val("");
                    jQuery("input[name='price2']").val("");
                    jQuery("input[name='rankend3']").val("");
                    jQuery("input[name='price3']").val("");
                    $('.qudaoShenhe').removeAttr("disabled");
                }
                else
                {
                    $(".pload").hide();
                    alert(result.message);
                    jQuery("input[name='rankend']").val("");
                    jQuery("input[name='price']").val("");
                    jQuery("input[name='rankend1']").val("");
                    jQuery("input[name='price1']").val("");
                    jQuery("input[name='rankend2']").val("");
                    jQuery("input[name='price2']").val("");
                    jQuery("input[name='rankend3']").val("");
                    jQuery("input[name='price3']").val("");
                    $('.qudaoShenhe').removeAttr("disabled");
                }
            }

        })


    }
    else
    {
        alert("前N名依次增大，并且值介于1-50之间，收费依次减小，并且值大于0小于1000；前N名和收费必须同时提供");

    }


})


$(".close").click(function () {

    $(".modal-backdrop").hide();
    $(".billExamineDiv").hide();

})
$(".cancel").click(function () {

    $(".modal-backdrop").hide();
    $(".billExamineDiv").hide();


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
            url: CTX+'/order/pendingAuditView1List',         //请求后台的URL（*）
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
                    title: '增加时间',
                    formatter:function (value,row,index) {
                        var date=new Date(value);
                        return date.toLocaleDateString();
                    }
                },
                {
                    field: 'updateUserId',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '修改者ID',
                    visible:false
                },
                {
                    field: "state",
                    align: 'center',
                    valign: 'middle',
                    title: '状态',
                    formatter:function (value,row,index) {
                        var  a="<span style='color:#94b86e;'>待审核</span>";

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
