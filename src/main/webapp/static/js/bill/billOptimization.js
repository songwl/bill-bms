/**
 * Created by 鱼在我这里。 on 2017/3/19.
 */
var website=null;
var keywords=null;
var searchName=null;
var searchUserName=null;
var searchState=2;
var firstRanking1=null;
var firstRanking2=null;
var newRanking1=null;
var newRanking2=null;
var newchange1=null;
var newchange2=null;
var addTime1=null;
var addTime2=null;
var groupId=null;
var standardDays1 = null;
var standardDays2 = null;
$(document).ready(function () {

    //优化调整（显示）
    $("#OptimizationUpdate").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        if(selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        }else{
            $(".modal-backdrop").show();
            $(".OptimizationUpdateDiv").slideDown();
        }
    })
    //优化启动
    $("#youhuaqidong").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        if (selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        } else {
            if (confirm("是否启动优化?")) {
                $.ajax({
                    type: "post",
                    url: CTX + "/order/billList/optimizationStart",
                    data: {selectContent: selectContent, length: len},
                    success: function (result) {
                        if (result.code == 200) {
                            alert(result.message);
                            $('#myTable').bootstrapTable('refresh');
                        }
                        else {
                            alert(result.message);
                        }
                    }

                })
            }
        }
    })
    //优化停止(合作停)
    $("#hezuotingzhi").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        var index;
        if (selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        } else {
            if (confirm("是否停止优化?")) {
                $.ajax({
                    type: "post",
                    url: CTX + "/order/billList/optimizationStop",
                    data: {selectContent: selectContent, length: len},
                    beforeSend: function () {
                        index  = layer.load(1, {
                            shade: [0.1,'#fff'] //0.1透明度的白色背景
                        });
                    },
                    success: function (result) {
                        if (result.code == 200) {
                            layer.alert(result.message, {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0
                            })
                            $('#myTable').bootstrapTable('refresh');
                        }
                        else {
                            layer.alert(result.message, {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0
                            })
                        }
                        layer.close(index);
                    }

                })
            }
        }
    })

    //点击上线
    $("#OptimizationStart").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var index;
        if(selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        }else{

            layer.confirm('是否上线优化？', {
                btn: ['确定', '取消']
            }, function () {
                var len = selectContent.length;
                var state = 1;
                $.ajax({
                    type: "post",
                    url: CTX + "/order/updateYBYstate",
                    data: {selectContent: selectContent, length: len, state: state},
                    beforeSend: function () {
                        index = layer.load(1, {
                            shade: [0.1, '#fff'] //0.1透明度的白色背景
                        });
                    },
                    success: function (result) {
                        if (result.code == 200) {
                            layer.alert(result.message, {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0
                            });
                            $('#myTable').bootstrapTable('refresh');

                        }
                        else {
                            layer.alert(result.message, {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0
                            });

                        }
                        layer.close(index);
                    }
                })

            })

        }
    })
    //点击离线
    $("#OptimizationStop").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var index;
        if(selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        }else{
            layer.confirm('是否优化离线？', {
                btn: ['确定', '取消']
            }, function () {
                var len =selectContent.length;
                var state=100;
                $.ajax({
                    type:"post",
                    url:CTX+"/order/updateYBYstate",
                    data:{selectContent:selectContent,length:len,state:state},
                    beforeSend: function () {
                        index = layer.load(1, {
                            shade: [0.1, '#fff'] //0.1透明度的白色背景
                        });
                    },
                    success:function (result) {
                        if(result.code==200)
                        {
                            layer.alert(result.message, {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0
                            });
                            $('#myTable').bootstrapTable('refresh');

                        }
                        else
                        {
                            layer.alert(result.message, {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0
                            });
                        }
                        layer.close(index);

                    }
                })
            })
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
        if(!isNaN($("#OptimizationUpdateNum").val())&&$("#OptimizationUpdateNum").val()>0)
        {
            var num= $("#OptimizationUpdateNum").val();
            $.ajax({
                type:"post",
                url:CTX+"/order/OptimizationUpdate",
                data:{selectContent:selectContent,length:len,num:num},
                beforeSend: function () {
                    $(".pload").show();

                },
                success:function (result) {
                    if(result.code==200)
                    {
                        alert(result.message);
                        $('#myTable').bootstrapTable('refresh');
                        $(".modal-backdrop").hide();
                        $(".pload").hide();
                        $(".OptimizationUpdateDiv").slideUp();

                    }
                }
            })
        }
        else
        {
            alert("请填写正确的优化指数");
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

        if ($("#standardDays1").val() != "")//关键词
        {
            standardDays1 = $.trim($("#standardDays1").val())
        }
        else {
            standardDays1 = null;
        }
        if ($("#standardDays2").val() != "")//关键词
        {
            standardDays2 = $.trim($("#standardDays2").val())
        }
        else {
            standardDays2 = null;
        }
        if($("#firstRanking1").val()!="")//初排1
        {
            firstRanking1=$.trim($("#firstRanking1").val());
        }
        else
        {
            firstRanking1=null;
        }
        if($("#firstRanking2").val()!="")//初排2
        {
            firstRanking2=$.trim($("#firstRanking2").val());
        }
        else
        {
            firstRanking2=null;
        }
        if($("#newRanking1").val()!="")//新排1
        {
            newRanking1=$.trim($("#newRanking1").val());
        }
        else
        {
            newRanking1=null;
        }
        if($("#newRanking2").val()!="")//新排2
        {
            newRanking2=$.trim($("#newRanking2").val());
        }
        else
        {
            newRanking2=null;
        }
        if($("#newchange1").val()!="")//新变1
        {
            newchange1=$.trim($("#newchange1").val());
        }
        else
        {
            newchange1=null;
        }
        if($("#newchange2").val()!="")//新变2
        {
            newchange2=$.trim($("#newchange2").val());
        }
        else
        {
            newchange2=null;
        }
        if($("#addTime1").val()!="")//新排2
        {
            addTime1=$.trim($("#addTime1").val());
        }
        else
        {
            addTime1=null;
        }
        if($("#addTime2").val()!="")//新排2
        {
            addTime2=$.trim($("#addTime2").val());
        }
        else
        {
            addTime2=null;
        }
        if ($("#selectGroupId").val() != "0") {
            groupId = $("#selectGroupId").val();
        }
        else {
            groupId = null;
        }

        $('#myTable').bootstrapTable('refresh');
    });
//创建分组显示
    $("#billCreateGroupClick").click(function () {
        index1 = layer.open({
            type: 1,
            title: '创建分组',
            skin: 'layui-layer-molv',
            shade: 0.6,
            area: ['50%', '90%'],
            content: $('#billCreateGroupDiv'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            end: function (e, u) {
                $('#billCreateGroupDiv').hide();
            }
        });

    })
    //批设分组显示
    $("#billToGroupClick").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        if (selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        } else {

            index1 = layer.open({
                type: 1,
                title: '批设分组',
                skin: 'layui-layer-molv',
                shade: 0.6,
                area: ['50%', '90%'],
                content: $('#billToGroupDiv'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                end: function (e, u) {
                    $('#billToGroupDiv').hide();
                }
            });
        }



    })
    //创建分组
    $("#createGroup").click(function () {
        layer.prompt({title: '请输入分组名称，并确认', formType: 3}, function(pass, index){
            if(pass!='')
            {
                $.ajax({
                    type:'post',
                    url:CTX+'/order/createGroup',
                    data:{groupName:pass},
                    beforeSend: function () {
                        index  = layer.load(1, {
                            shade: [0.1,'#fff'] //0.1透明度的白色背景
                        });
                    },
                    success:function (result) {
                        if(result.message=="0")
                        {
                            layer.msg("分组创建失败！");
                            $('#groupTable').bootstrapTable('refresh');
                        }
                        else if(result.message=="1")
                        {
                            layer.msg("分组创建成功！");
                            $('#groupTable').bootstrapTable('refresh');
                            $('#billToGroupTable').bootstrapTable('refresh');
                        }
                        else
                        {
                            layer.msg("分组名称已经存在！");
                            $('#groupTable').bootstrapTable('refresh');
                        }

                    }
                })
            }
            layer.close(index);
        })
    })

    //分组提交
    $("#toGroupCmt").click(function () {
        var selectContent = $('#billToGroupTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        var selectContentTable = $('#myTable').bootstrapTable('getSelections');
        var lenTable = selectContentTable.length;
        var index1;
        if (selectContent == "") {
            layer.alert('请选择一个分组', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });
        } else {
            $.ajax({
                type:'post',
                url:CTX+"/order/billToGroupCmt",
                data:{selectContent:selectContent,selectContentTable:selectContentTable,lenTable:lenTable},
                beforeSend: function () {
                    index1 = layer.load(1, {
                        shade: [0.1, '#fff'] //0.1透明度的白色背景
                    });
                },
                success: function (result) {
                    layer.alert(result.message, {
                        skin: 'layui-layer-molv' //样式类名
                        , closeBtn: 0
                    });
                    $('#groupTable').bootstrapTable('refresh');
                    $('#billToGroupTable').bootstrapTable('refresh');
                    layer.close(index1);
                }
            })
        }
    })
    //查询排名（出租订单）
    $("#searchRanking").click(function () {
        layer.confirm('是否启动查询排名？（注：查询可能消耗时间较长，请耐心等待）', {
            btn: ['确定', '取消']
        }, function () {
            $.ajax({
                type:'get',
                url:CTX+"/order/searchRanking",
                beforeSend: function () {
                    index1 = layer.load(1, {
                        shade: [0.1, '#fff'] //0.1透明度的白色背景
                    });
                },
                success:function (result) {
                    if(result.message!=null)
                    {
                        layer.alert(result.message+" 查询有误", {
                            skin: 'layui-layer-molv' //样式类名
                            , closeBtn: 0
                        });
                        $('#myTable').bootstrapTable('refresh');
                    }
                    else
                    {
                        layer.alert(" 查询成功!", {
                            skin: 'layui-layer-molv' //样式类名
                            , closeBtn: 0
                        });
                        $('#myTable').bootstrapTable('refresh');
                    }
                    layer.close(index1);
                }

            });
        })

    })


})
$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    var oTable2 = new TableInit2();
    oTable2.Init();
    var oTable3= new TableInit3();
    oTable3.Init();



});
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#myTable').bootstrapTable({
            url: CTX+'/order/getBillOptimization',         //请求后台的URL（*）
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
                    field: 'billAscriptionName',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '操作员',

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


                        a="<span>"+value+"</span>";

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
                        return  a;
                    }


                },
                {
                    field: 'changeRanking',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '新变',
                    formatter:function (value,row,index) {
                        var a="";
                        if(value==null||value==0)
                        {
                            a="<span style='color:#FF0000;font-weight: bold;'>-</span>";
                        }
                        else if(value>0)
                        {
                            a="<span>"+value+"</span>&nbsp;&nbsp;<img src='/static/img/up.ico' style='width:12px;height:12px;'>";
                        }
                        else
                        {
                            a="<span>" + value +"</span>&nbsp;&nbsp;<img src='/static/img/down.ico' style='width:12px;height:12px;'>";
                        }
                        return a;
                    },
                    visible:false
                },

                {
                    field: "standardDays",
                    align: 'center',
                    valign: 'middle',
                    title: '达标天',
                    sortable: true,

                },

                {
                    field: "dayOptimization",
                    align: 'center',
                    valign: 'middle',
                    title: '日优化',
                    sortable: true,

                },
                {
                    field: "allOptimization",
                    align: 'center',
                    valign: 'middle',
                    title: '总优化',
                    sortable: true,

                },
                {
                    field: "opstate",
                    align: 'center',
                    valign: 'middle',
                    title: '优化状态',
                    formatter:function (value,row,index) {
                        var a="";
                        if(value==1)
                        {
                            a="<span style='color:#09c;'>优化中</span>";
                        }
                        else
                        {
                            a="<span>离线</span>";
                        }
                        $("#length").html(row.length+"条记录");
                        return a;

                    },
                    sortable: true

                } ,


                /* {
                 field: 'operate',
                 title: '操作',
                 align: 'center',
                 valign: 'middle',
                 formatter:function (value,row,index) {
                 var a="<span style='color:#4382CF;cursor:pointer;' id='details'>详情</span>";

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
            website:$("#website").val(),
            way:2,
            website:website,
            keywords:keywords,
            searchName:searchName,
            searchUserName:searchUserName,
            state:searchState,
            firstRanking1:firstRanking1,
            firstRanking2:firstRanking2,
            newRanking1:newRanking1,
            newRanking2:newRanking2,
            newchange1:newchange1,
            newchange2:newchange2,
            addTime1:addTime1,
            addTime2:addTime2,
            groupId:groupId,
            standardDays1: standardDays1,
            standardDays2: standardDays2
        };
        return temp;
    }
    /* window.operateEvents = {
     'click #details': function (e, value, row, index) {
     $("#billCostDetail").show();
     $(".modal-backdrop").show();

     }

     }*/


    return oTableInit;
};

var TableInit2 = function () {
    var oTableInit2 = new Object();
    //初始化Table
    oTableInit2.Init = function () {
        $('#groupTable').bootstrapTable({
            url: CTX + '/order/getBillGroupTable',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 15,                       //每页的记录行数（*）
            pageList: [30, 50, 100],        //可供选择的每页的行数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            queryParams: oTableInit2.queryParams,//传递参数（*）
            queryParamsType: "",
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            /* height: 330,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度*/
            uniqueId: "Id",                     //每一行的唯一标识，一般为主键列
            singleSelect : true,
            rowStyle: function (row, index) {
                //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                var strclass = "";
                if ((row.id) % 2 == 0) {
                    strclass = '';
                }
                else {
                    strclass = 'active';
                }
                return {classes: strclass}
            },
            columns: [
                {
                    checkbox: true
                },
                {
                    field: 'id',
                    align: 'center',
                    valign: 'middle',
                    title: '数据库编号',
                    visible:false

                },
                {
                    field: 'group_name',
                    align: 'center',
                    valign: 'middle',
                    title: '分组名称',

                },
                {
                    field: 'task_count',
                    align: 'center',
                    valign: 'middle',
                    title: '任务数',

                },
                {
                    field: 'create_time',
                    align: 'center',
                    valign: 'middle',
                    title: '增加时间',
                    formatter: function (value, row, index) {
                        var time=new Date(value);
                        var a = "<span style='color:#4382CF;cursor:pointer;' id='deleteGroup'>"+time.toLocaleDateString()+"</span> ";

                        return a;
                    },

                }
                , {
                    field: 'costDate',
                    align: 'center',
                    valign: 'middle',
                    title: '操作',
                    formatter: function (value, row, index) {
                        var a = "<span style='color:#4382CF;cursor:pointer;' id='deleteGroup'>删除</span> ";

                        return a;
                    },
                    events: operateEvents

                },
            ],

        });
    };

    //得到查询的参数
    oTableInit2.queryParams = function (params) {
        var temp2 = {
            limit: params.pageSize,   //页面大小
            offset: params.pageNumber,  //页码
            sortOrder: params.sortOrder,
            sortName: params.sortName,

        };
        return temp2;
    }
    window.operateEvents = {
        'click #deleteGroup': function (e, value, row, index) {
            layer.confirm('是否删除当前分组？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                $.ajax({
                    type:'post',
                    url:CTX+'/order/deleteGroup',
                    data:{groupId:row.id},
                    success:function (result) {
                        layer.alert(result.message, {
                            skin: 'layui-layer-molv' //样式类名  自定义样式
                            , anim: 4 //动画类型
                            , icon: 2   // icon
                        });
                        $('#groupTable').bootstrapTable('refresh');
                        $('#billToGroupTable').bootstrapTable('refresh');

                    }

                })
            })
        },

    }
    return oTableInit2;
};
var TableInit3 = function () {
    var oTableInit3 = new Object();
    //初始化Table
    oTableInit3.Init = function () {
        $('#billToGroupTable').bootstrapTable({
            url: CTX + '/order/getBillGroupTable',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 15,                       //每页的记录行数（*）
            pageList: [30, 50, 100],        //可供选择的每页的行数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            queryParams: oTableInit3.queryParams,//传递参数（*）
            queryParamsType: "",
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            /* height: 330,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度*/
            uniqueId: "Id",                     //每一行的唯一标识，一般为主键列
            singleSelect : true,
            rowStyle: function (row, index) {
                //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                var strclass = "";
                if ((row.id) % 2 == 0) {
                    strclass = '';
                }
                else {
                    strclass = 'active';
                }
                return {classes: strclass}
            },
            columns: [
                {
                    checkbox: true
                },
                {
                    field: 'id',
                    align: 'center',
                    valign: 'middle',
                    title: '数据库编号',
                    visible:false

                },
                {
                    field: 'group_name',
                    align: 'center',
                    valign: 'middle',
                    title: '分组名称',

                },

                {
                    field: 'create_time',
                    align: 'center',
                    valign: 'middle',
                    title: '增加时间',
                    formatter: function (value, row, index) {
                        var time=new Date(value);
                        var a = "<span style='color:#4382CF;cursor:pointer;' id='deleteGroup'>"+time.toLocaleDateString()+"</span> ";

                        return a;
                    },

                } ,
                {
                    field: 'task_count',
                    align: 'center',
                    valign: 'middle',
                    title: '任务数',

                },
            ],

        });
    };

    //得到查询的参数
    oTableInit3.queryParams = function (params) {
        var temp3 = {
            limit: params.pageSize,   //页面大小
            offset: params.pageNumber,  //页码
            sortOrder: params.sortOrder,
            sortName: params.sortName,
        };
        return temp3;
    }
    return oTableInit3;
};



$(function () {
    $("#queren").click(function () {

        $('#myTable').bootstrapTable('refresh');
    });

});








