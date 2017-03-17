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
            $(".modal-backdrop").hide();

        }
        else {
            $(".modal-backdrop").show();
            $(".samepriceDiv").slideDown();
            $(".differentpriceDiv").slideUp();
            $(".Navs2").slideUp();
        }

    })
//提交相同价订单
    $(".samepricecmt").click(function () {


        var search= $("#searchengineid ").val();
        var keyword= $("#keyword").val();
        var url= $("#url").val();

        var rankend = parseInt(jQuery("input[name='samePricerankend']").val());
        var price = parseFloat(jQuery("input[name='samePriceprice']").val());
        var rankend1 = parseInt(jQuery("input[name='samePricerankend1']").val());
        var price1 = parseFloat(jQuery("input[name='samePriceprice1']").val());
        var rankend2 = parseInt(jQuery("input[name='samePricerankend2']").val());
        var price2 = parseFloat(jQuery("input[name='samePriceprice2']").val());
        var rankend3 = parseInt(jQuery("input[name='samePricerankend3']").val());
        var price3 = parseFloat(jQuery("input[name='samePriceprice3']").val());

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
            if(search==""||keyword==""||url=="")
            {
                alert("请将信息填写完整！");
            }
            else
            {
                var keyword_arr=$.trim(keyword).split('\n');
                var url_arr=$.trim(url).split('\n');
                if(keyword_arr.length>1&&keyword_arr.length!=url_arr.length)
                {
                    alert("网址行数为1或者与关键词一一对应");
                }
                else
                {
                    var Arankend = jQuery("input[name='samePricerankend']").val();
                    var Aprice = jQuery("input[name='samePriceprice']").val();
                    var Arankend1 = jQuery("input[name='samePricerankend1']").val();
                    var Aprice1 = jQuery("input[name='samePriceprice1']").val();
                    var Arankend2 = jQuery("input[name='samePricerankend2']").val();
                    var Aprice2 = jQuery("input[name='samePriceprice2']").val();
                    var Arankend3 =jQuery("input[name='samePricerankend3']").val();
                    var Aprice3 = jQuery("input[name='samePriceprice3']").val();
                    $.ajax({
                        type:"get",
                        url:CTX+"/bill/list/sameprice",
                        dataType:'json',
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        data:{
                            search:search,
                            keyword:keyword,
                            url:url,
                            rankend:Arankend,
                            price:Aprice,
                            rankend1:Arankend1,
                            price1:Aprice1,
                            rankend2:Arankend2,
                            price2:Aprice2,
                            rankend3:Arankend3,
                            price3:Aprice3

                        },
                        beforeSend: function () {
                            $("#pload").show();
                        },

                        success:function (result) {
                            $("#pload").hide();
                            if(result.code==200)
                            {

                                if(result.message=="")
                                {
                                    alert("导入成功!");
                                    $(".modal-backdrop").hide();
                                    $(".samepriceDiv").slideUp();
                                    $('#myTable').bootstrapTable('refresh');
                                }
                                else
                                {

                                    alert(result.message+" 已经存在!");
                                }
                            }
                            else
                            {

                                alert("系统繁忙，请稍后再试！");
                            }
                        }

                    })
                }

            }


        }
        else
        {
            alert("前N名依次增大，并且值介于1-50之间，收费依次减小，并且值大于0小于1000；前N名和收费必须同时提供");

        }





    })
    //不同价导入
    $("#Differentprice").click(function () {
        if($(".differentpriceDiv").css("display")=="block") {
            $(".differentpriceDiv").slideUp();

        }
        else {
            $(".modal-backdrop").show();
            $(".differentpriceDiv").slideDown();
            $(".samepriceDiv").slideUp();
            $(".Navs2").slideUp();
        }


    })
    $(".close").click(function () {
        $(".samepriceDiv").slideUp();
        $(".differentpriceDiv").slideUp();
        $(".changepriceDiv").slideUp();
        $(".modal-backdrop").hide();
        $("#billCostDetail").hide();
    })
    $(".cancel").click(function () {
        $(".samepriceDiv").slideUp();
        $(".differentpriceDiv").slideUp();
        $(".changepriceDiv").slideUp();
        $(".modal-backdrop").hide();
        $("#billCostDetail").hide();
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


    //不同价订单导入
    $(".dfpricecmt").click(function () {
        var dfsearch=$("#dfsearch").val();
        var dfrankend=$("#dfrankend").val();
        var dfkeyword=$("#dfkeyword").val();
        var dfurl=$("#dfurl").val();
        var dfprice=$("#dfprice").val();
        $.ajax({
            type:'get',
            url:CTX+"/bill/list/diffrentprice",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType:'json',
            data:{
                dfsearch:dfsearch,
                dfrankend:dfrankend,
                dfkeyword:dfkeyword,
                dfurl:dfurl,
                dfprice:dfprice
             },
            beforeSend: function () {
                $("#pload").show();
            },
            success:function (result) {
                $("#pload").hide();
                if(result.code==200)
                {

                    if(result.message=="")
                    {
                        alert("导入成功!");
                        $(".differentpriceDiv").slideUp();
                        $(".modal-backdrop").hide();
                        $('#myTable').bootstrapTable('refresh');
                    }
                    else
                    {

                        alert(result.message+" 已经存在!");
                    }
                }
                else
                {

                    alert("系统繁忙，请稍后再试！");
                }
            }
            
        })
    })
    
    
     $("#testpm").click(function () {
         $.ajax({
             type: "get",
             url: CTX + "/bill/testpm",
             dataType:'json',
             beforeSend: function () {
                 $("#pload").show();
             },
             success:function (result) {
                 $("#pload").hide();
                 if(result.code==200)
                 {
                     alert("查询成功！");
                     $('#myTable').bootstrapTable('refresh');
                 }


             }
             
         })
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
            url: '/bill/bill/getBillDetails',         //请求后台的URL（*）
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
                    title: '初排',
                    formatter:function (value,row,index) {
                        var a="";
                        if(value>50)
                        {
                            a="<span>" +">50"+"</span>";
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
                        else
                        {
                            a="<span>" +value+"</span>";
                        }
                        return a;
                    }


                },
                {
                    field: 'priceOne',
                    align: 'center',
                    valign: 'middle',
                    title: '价格1',

                },

                {
                    field: 'priceTwo',
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
                    formatter:function (value,row,index) {
                        var a="";
                        if(value==0)
                        {
                            a="<span style='color: #54728c'>-</span>";
                        }
                        else if(value==null)
                        {
                            a="<span style='color: #54728c'>-</span>";
                        }
                        else
                        {
                            a="<span style='color: #54728c'>¥ "+value+"</span>";
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
                    field: "monthConsumption",
                    align: 'center',
                    valign: 'middle',
                    title: '本月消费',

                },
                {
                    field: "state",
                    align: 'center',
                    valign: 'middle',
                    title: '合作状态',
                    formatter:function (value,row,index) {
                        var a="";
                        if(value==1)
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
function detail( ) {
    var bid=$("input[name='Bid']").val();


}






