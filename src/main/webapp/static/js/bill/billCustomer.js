/**
 * Created by 鱼在我这里。 on 2017/3/19.
 */
var website=null;
var keywords=null;
var searchName=null;
var searchUserName=null;
var searchState=2;
var searchState2=null;
var searchStandard=null;
var standardDays=null;
var addTime=null;
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
        $(".billExamineDiv").slideUp();
        $(".billChangeDailiDiv").slideUp();
        $(".billChangeToKeHuDiv").slideUp();

    })
    $(".cancel").click(function () {
        $(".samepriceDiv").slideUp();
        $(".differentpriceDiv").slideUp();
        $(".changepriceDiv").slideUp();
        $(".modal-backdrop").hide();
        $("#billCostDetail").hide();
        $(".billExamineDiv").slideUp();
        $(".billChangeDailiDiv").slideUp();
        $(".billChangeToKeHuDiv").slideUp();
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
        if($("#searchUserName").val()!="--请选择--")
        {
            searchUserName=  $("#searchUserName").val();
        }
        else
        {
            searchUserName=null;
        }
        if($("#searchStandard option:selected").val()!="--请选择--"&&$("#searchStandard option:selected").val()!="0")
        {
            searchStandard=  $("#searchStandard").val();
        }
        else
        {
            searchStandard=null;
        }
        if($("#standardDays").val()!="")
        {
            standardDays=$("#standardDays").val();
        }
        else
        {
            standardDays=null;
        }
        if($("#addTime").val()!="")
        {
            addTime=$("#addTime").val();
        }
        else
        {
            addTime=null;
        }
        $('#myTable').bootstrapTable('refresh');
    });
    $("#searchState").change(function () {
        var state=$("#searchState option:selected").val();
        if(state==1)
        {
            searchState=2;
        }
        else if(state==2)
        {
            searchState=2;
        }
        else
        {
            searchState=3;
            searchState2=null;
        }


    })
    $("#continue").click(function () {
        $("#continue").css({"background":"#eee"});
        $("#stop").css({"background":"#fff"});
        $("#all").css({"background":"#fff"});
        searchState=2;
        searchState2=null;
        $('#myTable').bootstrapTable('refresh');
    })
    $("#stop").click(function () {
        $("#stop").css({"background":"#eee"});
        $("#continue").css({"background":"#fff"});
        $("#all").css({"background":"#fff"});
        searchState=3;
        searchState2=null;
        $('#myTable').bootstrapTable('refresh');
    })
    $("#all").click(function () {
        $("#stop").css({"background":"#fff"});
        $("#continue").css({"background":"#fff"});
        $("#all").css({"background":"#eee"});
        searchState=2;
        searchState2=3;
        $('#myTable').bootstrapTable('refresh');
    })
    $("#btn_update").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        if(selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        }else {

            $(".modal-backdrop").show();
            $(".billExamineDiv").slideDown();
        }

    })
    //调价确认
    $(".updatePricecmt").click(function () {
        var rankend = parseInt(jQuery("input[name='updaterankend']").val());
        var price = parseFloat(jQuery("input[name='updateprice']").val());
        var rankend1 = parseInt(jQuery("input[name='updaterankend1']").val());
        var price1 = parseFloat(jQuery("input[name='updateprice1']").val());
        var rankend2 = parseInt(jQuery("input[name='updaterankend2']").val());
        var price2 = parseFloat(jQuery("input[name='updateprice2']").val());
        var rankend3 = parseInt(jQuery("input[name='updaterankend3']").val());
        var price3 = parseFloat(jQuery("input[name='updateprice3']").val());
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

            var rankendA = parseInt(jQuery("input[name='updaterankend']").val());
            var priceA = parseFloat(jQuery("input[name='updateprice']").val());
            var rankend1A = parseInt(jQuery("input[name='updaterankend1']").val());
            var price1A = parseFloat(jQuery("input[name='updateprice1']").val());
            var rankend2A = parseInt(jQuery("input[name='updaterankend2']").val());
            var price2A = parseFloat(jQuery("input[name='updateprice2']").val());
            var rankend3A = parseInt(jQuery("input[name='updaterankend3']").val());
            var price3A = parseFloat(jQuery("input[name='updateprice3']").val());
            var selectContent = $('#myTable').bootstrapTable('getSelections');
            var len =selectContent.length;
            $.ajax({
                type:'post',
                url:CTX+"/order/billList/updatePrice",
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
                    $('.updatePricecmt').attr('disabled',"true");
                },
                success:function (result) {
                    if(result.code==200)
                    {
                        layer.alert(result.message, {
                            skin: 'layui-layer-molv' //样式类名
                            ,closeBtn: 0
                        });
                        $(".billExamineDiv").slideUp();
                        $(".modal-backdrop").hide();
                        $(".pload").hide();
                        $('#myTable').bootstrapTable('refresh');
                        jQuery("input[name='updaterankend']").val("");
                        jQuery("input[name='updateprice']").val("");
                        jQuery("input[name='updaterankend1']").val("");
                        jQuery("input[name='updateprice1']").val("");
                        jQuery("input[name='updaterankend2']").val("");
                        jQuery("input[name='updateprice2']").val("");
                        jQuery("input[name='updaterankend3']").val("");
                        jQuery("input[name='updateprice3']").val("");
                        $('.updatePricecmt').removeAttr("disabled");
                    }
                    else
                    {
                        $(".pload").hide();
                        layer.alert(result.message, {
                            skin: 'layui-layer-molv' //样式类名
                            ,closeBtn: 0
                        });
                        jQuery("input[name='updaterankend']").val("");
                        jQuery("input[name='updateprice']").val("");
                        jQuery("input[name='updaterankend1']").val("");
                        jQuery("input[name='updateprice1']").val("");
                        jQuery("input[name='updaterankend2']").val("");
                        jQuery("input[name='updateprice2']").val("");
                        jQuery("input[name='updaterankend3']").val("");
                        jQuery("input[name='updateprice3']").val("");
                        $('.updatePricecmt').removeAttr("disabled");
                    }
                }

            })


        }
        else
        {
            layer.alert('前N名依次增大，并且值介于1-50之间，收费依次减小，并且值大于0小于1000；前N名和收费必须同时提供', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });
        }

    })

    //订单切换客户
    $("#billToChange").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        if (selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });
        }
        else {

            $(".modal-backdrop").show();
            $(".billChangeToKeHuDiv").slideDown();

        }
    })
    $(".billChangeToKeHucmt").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        var kehu = $("#selectKeHulist  option:selected").val();

        $.ajax({
            type: "post",
            url: CTX + "/order/billChangeToKeHucmt",
            data: {selectContent: selectContent, length: len, kehu: kehu},
            success: function (result) {
                if (result.code == 200) {
                    $('#myTable').bootstrapTable('refresh');
                    $(".billChangeToKeHuDiv").slideUp();
                    $(".modal-backdrop").hide();
                }
                layer.alert(result.message, {
                    skin: 'layui-layer-molv' //样式类名
                    , closeBtn: 0
                });


            }

        })
    })

    //订单切换代理商
    $("#billChangeDailiClick").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        if(selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        }else {
            $(".modal-backdrop").show();
            $(".billChangeDailiDiv").slideDown();
        }
    })
    $("#dailiListSelect").change(function () {
        if($("#dailiListSelect option:selected").val()!="0")
        {
            $("#kehuListSelect").empty();
            var str="<option value='0'>--请选择--</option>";
            $.ajax({
                type:'get',
                url:CTX+"/order/getDailiKehu",
                data:{userId:$("#dailiListSelect option:selected").val()},
                success:function (result) {


                        if(result.data!=null)
                        {
                            for(var i=0;i<result.data.length;i++)
                            {
                                str+="<option value='"+result.data[i]['id']+"'>"+result.data[i]['userName']+"</option>";
                            }

                        }
                        console.log(str);
                    $("#kehuListSelect").append(str);

                }
            })
        }
        else
        {
            $("#kehuListSelect").empty();
            var str="<option value='0'>--请选择--</option>";
            $("#kehuListSelect").append(str);
        }

    })
    //切换订单确认\]
    $(".billChangeDailiCmt").click(function () {
        var index;
        var rankend = parseInt(jQuery("input[name='changeDailirankend']").val());
        var price = parseFloat(jQuery("input[name='changeDailiprice']").val());
        var rankend1 = parseInt(jQuery("input[name='changeDailirankend1']").val());
        var price1 = parseFloat(jQuery("input[name='changeDailiprice1']").val());
        var rankend2 = parseInt(jQuery("input[name='changeDailirankend2']").val());
        var price2 = parseFloat(jQuery("input[name='changeDailiprice2']").val());
        var rankend3 = parseInt(jQuery("input[name='changeDailirankend3']").val());
        var price3 = parseFloat(jQuery("input[name='changeDailiprice3']").val());

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
            var selectContent = $('#myTable').bootstrapTable('getSelections');
            var len =selectContent.length;
            var dailiUserId=$("#dailiListSelect option:selected").val();
            var kehuUserId=$("#kehuListSelect option:selected").val();
            if($("#dailiListSelect option:selected").val()!="0"&&$("#kehuListSelect option:selected").val()!="0")
            {
                $.ajax({
                    type: 'post',
                    url: CTX + "/order/billChangeDailiCmt",
                    dataType: 'json',
                    data: {
                        rankend: rankend,
                        price: price,
                        rankend1: rankend1,
                        price1: price1,
                        rankend2: rankend2,
                        price2: price2,
                        rankend3: rankend3,
                        price3: price3,
                        selectContent: selectContent,
                        checkboxLength: len,
                        dailiUserId:dailiUserId,
                        kehuUserId:kehuUserId
                    },
                    beforeSend: function () {
                        index  = layer.load(1, {
                            shade: [0.1,'#fff'] //0.1透明度的白色背景
                        });
                    },
                    success:function (result) {

                        layer.close(index);
                        $(".modal-backdrop").hide();
                            if(result.code==200)
                            {
                                layer.alert(result.message, {
                                    skin: 'layui-layer-molv' //样式类名  自定义样式
                                    , anim: 2 //动画类型
                                    , icon: 4   // icon
                                });

                                $(".billChangeDailiDiv").slideUp();
                                $('#myTable').bootstrapTable('refresh');
                            }
                            else
                            {
                                result.message+=result.message+"  订单出错";
                                layer.alert(result.message, {
                                    skin: 'layui-layer-molv' //样式类名  自定义样式
                                    , anim: 2 //动画类型
                                    , icon: 4   // icon
                                });
                            }
                    }
                })
            }
            else
            {
                layer.alert('代理商和客户必须同时选择！', {
                    skin: 'layui-layer-molv' //样式类名  自定义样式
                    , anim: 6 //动画类型
                    , icon: 4   // icon
                });
            }


        }
        else
        {
            layer.alert('前N名依次增大，并且值介于1-50之间，收费依次减小，并且值大于0小于1000；前N名和收费必须同时提供', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });
        }

    })
})
$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    var oTable1 = new TableInit1();
    oTable1.Init();
});
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#myTable').bootstrapTable({
            url: CTX+'/order/getCustomerBill',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 50,                       //每页的记录行数（*）
            pageList: [100,200,300,500,1000],        //可供选择的每页的行数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            queryParams: oTableInit.queryParams,//传递参数（*）
            queryParamsType: "",
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            //height: 700,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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
                    field: 'dayConsumption',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '当天消费',
                    formatter:function (value,row,index) {
                        var a="";
                        if(value==0)
                        {
                            a="<span style='color: #4382CF'>-</span>";
                        }
                        else if(value==null)
                        {
                            a="<span style='color: #4382CF'>-</span>";
                        }
                        else
                        {
                            a="<span style='color: #4382CF'>¥ "+value+"</span>";
                        }

                        return a;
                    }

                },

                {
                    field: "monthConsumption",
                    align: 'center',
                    valign: 'middle',
                    title: '本月消费',
                    formatter:function (value,row,index) {
                        var a="";
                        if(value==0)
                        {
                            a="<span style='color: #4382CF'>-</span>";
                        }
                        else if(value==null)
                        {
                            a="<span style='color: #4382CF'>-</span>";
                        }
                        else
                        {
                            a="<span style='color: #4382CF'>¥ "+value+"</span>";
                        }


                        return a;
                    }

                },
                {
                    field: "standardDays",
                    align: 'center',
                    valign: 'middle',
                    title: '达标天',
                    sortable: true,

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
                        $("#length").html(row.length+"条记录");
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
            website:website,
            keywords:keywords,
            searchName:searchName,
            searchUserName:searchUserName,
            state:searchState,
            state2:searchState2,
            searchStandard:searchStandard,
            standardDays:standardDays,
            addTime:addTime

        };
        return temp;
    }
    window.operateEvents = {
        'click #details': function (e, value, row, index) {
            $("#detailTable").empty();
            $("#billCostDetail").show();
            $(".modal-backdrop").show();
            var str="";
            for(var i=0;i<row.billPriceList.length;i++)
            {
                str+="<tr><td>前<span class='red'>"+row.billPriceList[i]['billRankingStandard']+"</span>名</td><td>￥"+row.billPriceList[i]['price']+"</td></tr>";
            }
            $("#detailTable").append(str);
            $("input[name='Bid']").val(row.id);

            $('#pricetable').bootstrapTable('refresh');
        }

    }


    return oTableInit;
};
var TableInit1 = function () {
    var oTableInit1 = new Object();
    //初始化Table
    oTableInit1.Init = function () {
        $('#pricetable').bootstrapTable({
            url: CTX+'/order/getPriceDetails',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 20,                       //每页的记录行数（*）
            pageList: [20, 50, 100],        //可供选择的每页的行数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            queryParams: oTableInit1.queryParams,//传递参数（*）
            queryParamsType: "",
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 330,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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
                    field: 'id',
                    align: 'center',
                    valign: 'middle',
                    title: '序号',

                },

                {
                    field: 'ranking',
                    align: 'center',
                    valign: 'middle',
                    title: '名次',

                },
                {
                    field: 'costAmount',
                    align: 'center',
                    valign: 'middle',
                    title: '金额',

                }, {
                    field: 'costDate',
                    align: 'center',
                    valign: 'middle',
                    title: '消费日期',

                },
            ],

        });
    };

    //得到查询的参数
    oTableInit1.queryParams = function (params) {
        var temp1 = {
            limit: params.pageSize,   //页面大小
            offset: params.pageNumber,  //页码
            way:3,//上下游
            billId: $("input[name='Bid']").val()

        };
        return temp1;
    }

    return oTableInit1;
};




$(function () {
    $("#queren").click(function () {

        $('#myTable').bootstrapTable('refresh');
    });

});
$(function () {
    $("#queren").click(function () {

        $('#pricetable').bootstrapTable('refresh');
    });

});









