/**
 * Created by 鱼在我这里。 on 2017/3/19.
 */
var website=null;
var keywords=null;
var searchName=null;
var searchUserName=null;
var searchState=2;
var searchStandard=null;
var firstRanking1=null;
var firstRanking2=null;
var newRanking1=null;
var newRanking2=null;
var newchange1=null;
var newchange2=null;
var addTime1=null;
var addTime2=null;
$(document).ready(function () {

    //优化调整（显示）
    $("#OptimizationUpdate").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        if(selectContent == "") {
            alert('请选择一列数据!');

        }else{
            $(".modal-backdrop").show();
            $(".OptimizationUpdateDiv").slideDown();
        }
    })
    //优化上线
    $("#OptimizationStart").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        if(selectContent == "") {
            alert('请选择一列数据!');

        }else{
            if(confirm("是否上线优化？"))
            {
                var len =selectContent.length;
                var state=1;
                $.ajax({
                    type:"post",
                    url:CTX+"/order/updateYBYstate",
                    data:{selectContent:selectContent,length:len,state:state},
                    beforeSend: function () {
                        $(".pload").show();
                        $(".modal-backdrop").show();
                    },
                    success:function (result) {
                        if(result.code==200)
                        {
                            alert(result.message);
                            $('#myTable').bootstrapTable('refresh');
                            $(".pload").hide();
                            $(".modal-backdrop").hide();
                        }
                        else
                        {
                            alert(result.message);
                            $(".pload").hide();
                            $(".modal-backdrop").hide();
                        }

                    }
                })
            }
        }
    })
    //优化离线
    $("#OptimizationStop").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        if(selectContent == "") {
            alert('请选择一列数据!');

        }else{
          if(confirm("是否离线优化？"))
          {
              var len =selectContent.length;
              var state=100;
              $.ajax({
                  type:"post",
                  url:CTX+"/order/updateYBYstate",
                  data:{selectContent:selectContent,length:len,state:state},
                  beforeSend: function () {
                      $(".pload").show();
                      $(".modal-backdrop").show();
                  },
                  success:function (result) {
                      if(result.code==200)
                      {
                          alert(result.message);
                          $('#myTable').bootstrapTable('refresh');
                          $(".pload").hide();
                          $(".modal-backdrop").hide();
                      }
                      else
                      {
                          alert(result.message);
                          $(".pload").hide();
                          $(".modal-backdrop").hide();
                      }

                  }
              })
          }
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
        if($("#searchStandard option:selected").val()!="--请选择--"&&$("#searchStandard option:selected").val()!="0")//是否达标
        {
            searchStandard=  $("#searchStandard").val();
        }
        else
        {
            searchStandard=null;
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
            height: 700,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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
                    field: "opstate",
                    align: 'center',
                    valign: 'middle',
                    title: '优化状态',
                    formatter:function (value,row,index) {
                        var a="";
                        if(value==1)
                        {
                            a="<span style='color:#94b86e;'>优化中</span>";
                        }
                        else
                        {
                            a="<span>离线</span>";
                        }
                        return a;
                    }


                },
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
            searchStandard:searchStandard,
            firstRanking1:firstRanking1,
            firstRanking2:firstRanking2,
            newRanking1:newRanking1,
            newRanking2:newRanking2,
            newchange1:newchange1,
            newchange2:newchange2,
            addTime1:addTime1,
            addTime2:addTime2
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





$(function () {
    $("#queren").click(function () {

        $('#myTable').bootstrapTable('refresh');
    });

});








