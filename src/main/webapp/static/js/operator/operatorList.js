
/**
 * Created by Administrator on 2017/3/16.
 */
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
$(".openLeaseRole").click(function () {
    var selectContent = $('#myTable').bootstrapTable('getSelections');
    //console.info(selectContent);
    if (selectContent.length == 0) {
        layer.msg("请先选择一列数据！");
        return;
    }//询问框
    layer.confirm('是否 开通/关闭 已选择渠道商的网站出租平台权限？', {
        btn: ['开通', '关闭'] //按钮
    }, function () {
        $.post(CTX + '/optimizationTool/OpenWebsitePower', {
            data: selectContent,
            type: 1,
            len: selectContent.length
        }, function (data) {
            if (data.code == "1") {
                layer.msg("开通成功");
                return;
            }
            else {
                layer.msg("开通失败");
                return;
            }
        })
        $('#myTable').bootstrapTable('refresh');
    }, function () {
        $.post(CTX + '/optimizationTool/OpenWebsitePower', {
            data: selectContent, type: 0,
            len: selectContent.length
        }, function (data) {
            if (data.code == "1") {
                layer.msg("关闭成功");
                return;
            }
            else {
                layer.msg("关闭失败");
                return;
            }
        })
        $('#myTable').bootstrapTable('refresh');
    });

});

var userName=false;
var password=false;
var searchUserName=null;
var searchState=null;
//用户名
$("input[name='userName']").blur(function () {

    if ($("#userName1").val() != "")
    {
        if (/^[A-Za-z]\w{5,12}$/.test($("#userName1").val())) {


            $.ajax({
                type: "get",
                data:{userName:$("#userName1").val()},
                url:CTX+"/user/register/validUserName",
                dataType:"json",
                success:function (result) {
                    if (result.code==200){ //返回结果code==200代表正确
                        //验证可以使用,
                        userName=true;
                    }else{
                        //验证为已注册,不能使用,
                        $(".pdlogid").css({ "color": "#ff0000" }).text("用户名已存在！");
                        userName = false;
                    }

                }

            })

        }
        else {
            //格式不对
            userName = false;
            $(".pdlogid").css({ "color": "#ff0000" }).text("用户名格式不正确！");
        }

    }
});
//密码
$("input[name='password']").blur(function () {

    if ($("input[name='password']").val()!= "")
    {
        if (/^\w{6,12}$/.test($(this).val())) {
            password = true;

        }
        else {
            //格式不对
            password = false;
            $(".pdpwd").css({ "color": "#ff0000" }).text("密码格式不正确！");
        }
    }
});

$("input[name='userName']").focus(function () {
    $(".pdlogid").css({ "color": "#ff0000" }).text("");
    userName = false;

});

//添加操作员确认
$(".addOperatorcmt").click(function () {
    if(userName&&password)
    {
        $.ajax({
            type: "post",
            data:{userName:$("#userName1").val(),
                password:$("input[name='password']").val(),
                realName:$("input[name='realName']").val(),
                contact:$("input[name='contact']").val(),
                phone:$("input[name='phone']").val(),
                qq:$("input[name='qq']").val(),
            },
            url:CTX+"/operator/list",
            dataType:"json",
            success:function (result) {

                  if(result.code==200)
                  {
                      alert(result.message);
                      $(".addOperatorDiv").slideUp();
                      $(".modal-backdrop").hide();
                      $('#myTable').bootstrapTable('refresh');
                  }
                  else
                  {
                      alert(result.Message);
                  }

            }

        })
    }
  else
    {
        alert("填写信息有误，请重新输入！");
    }
})



//更改操作员信息
$(".updateOperatorcmt").click(function () {
    if(/^[A-Za-z]\w{5,12}$/.test($("input[name='userName1']").val()))
    {
          $.ajax({
              type:'post',
              url:CTX+"/operator/updateOperator",
              data:{
                  id:$("input[name='operator']").val(),
                  userName:  $("input[name='userName1']").val(),
                  realName:$("input[name='realName']").val(),
                  contact:$("input[name='contact']").val(),
                  phone:$("input[name='phone']").val(),
                  qq:$("input[name='qq']").val(),
                  status:$("#viewstatus option:selected").val()
              },
              success:function (result) {
                  if(result.code==200)
                  {
                      alert(result.message);
                      $(".addOperatorDiv").slideUp();
                      $(".modal-backdrop").hide();
                      $('#myTable').bootstrapTable('refresh');
                  }
                  else
                  {
                      alert(result.Message);
                  }
              }
          })
    }
    else
    {
        alert("用户名不能为空或格式错误!");
    }
})

//添加操作员
    $("#addOperator").click(function () {

        $(".modal-backdrop").show();
        $(".addOperatorDiv").slideDown();
        $("#nameDiv").show();
        $("#nameDiv1").hide();
        $(".modal-title").html("添加操作员");
       $(".addOperatorcmt").show();
        $(".updateOperatorcmt").hide();
        $("#viewpwd").show();
        $("#viewstate").hide();
        $("input[name='userName']").val("");
        $("input[name='realName']").val("");
        $("input[name='contact']").val("");
        $("input[name='phone']").val("");
        $("input[name='qq']").val("");


    })
    $(".close").click(function () {
        $(".addOperatorDiv").slideUp();
        $(".modal-backdrop").hide();


    })
    $(".cancel").click(function () {
        $(".addOperatorDiv").slideUp();
        $(".modal-backdrop").hide();

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
            url: CTX+'/operator/getOperator',         //请求后台的URL（*）
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
            showColumns: false,                  //是否显示所有的列
            showRefresh: false,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            //height: 700,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "Id",                     //每一行的唯一标识，一般为主键列
            /*   showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
           detailView: false,                   //是否显示父子表
            showExport: true,                     //是否显示导出
            exportDataType: "basic",*/
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
                    field: 'customerId',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '数据库编号',
                    visible:false

                },
                {
                    field: 'userName',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '操作员',
                    formatter:function (value,row,index) {
                        var a="";

                            a="<span style='color:#4382CF;cursor:pointer;' id='details'>"+value+"</span>";


                        return a;
                    }

                },
                {
                    field: 'realName',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '真实姓名'
                }, {
                    field: 'contact',
                    align: 'center',
                    valign: 'middle',
                    title: '联系人',

                }, {
                    field: 'qq',
                    align: 'center',
                    sortable: true,
                    valign: 'middle',
                    title: 'qq'
                },
                {
                    field: 'phone',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '电话'
                },

                {
                    field: 'createTime',
                    align: 'center',
                    valign: 'middle',
                    title: '注册时间',

                },

                {
                    field: 'lastLoginTime',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '最新登录',

                },

                {
                    field: "loginCount",
                    align: 'center',
                    valign: 'middle',
                    title: '登录次数',



                },
                {
                    field: "status",
                    align: 'center',
                    valign: 'middle',
                    title: '状态',
                    formatter:function (value,row,index) {
                        var a="";
                        if(value==1)
                        {
                       a="<span style='color:#4382CF;cursor:pointer;' id='details'>正常</span>";
                        }
                        else
                        {
                            a="<span>冻结中</span>";
                        }
                        return a;
                    }



                },
                {
                    field: "leaseRole",
                    align: 'center',
                    valign: 'middle',
                    title: '租站',
                    formatter:function (value,row,index) {
                        var a="";
                        if(value==1)
                        {
                            a="<span style='color:#4382CF;cursor:pointer;' id='details'>有</span>";
                        }
                        else
                        {
                            a="<span style='color:#4382CF;'>-</span>";
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
                        var a="<span style='color:#4382CF;cursor:pointer;' id='details'>资料</span>   " +
                            "<span style='color:#4382CF;cursor:pointer;' id='changepwd'>改密</span>   "

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
            searchUserName :searchUserName,
            searchState:searchState,

        };
        return temp;
    }
    window.operateEvents = {
        'click #details': function (e, value, row, index) {
            $(".modal-backdrop").show();
            $(".addOperatorDiv").slideDown();
            $("#viewpwd").hide();
            $("#viewstate").show();
            $("#nameDiv1").show();
            $("#nameDiv").hide();
            $(".updateOperatorcmt").show();
            $(".addOperatorcmt").hide();
            $(".modal-title").html("操作员详情");
            $("input[name='userName1']").val("");
            $("input[name='realName']").val("");
            $("input[name='contact']").val("");
            $("input[name='phone']").val("");
            $("input[name='qq']").val("");
            $("input[name='userName1']").val(row.userName);
            $("input[name='realName']").val(row.realName);
            $("input[name='contact']").val(row.contact);
            $("input[name='phone']").val(row.phone);
            $("input[name='qq']").val(row.qq);
            $("#viewstatus").empty();
            if(row.status=="1")
            {
                $("#viewstatus").append("<option value='1'>正常</option>");
                $("#viewstatus").append("<option value='0'>冻结</option>");
            }
            else {
                $("#viewstatus").append("<option value='0'>冻结</option>");
                $("#viewstatus").append("<option value='1'>正常</option>");
            }
            $("input[name='operator']").empty();
            $("input[name='operator']").val(row.customerId);
        },
        'click #changepwd': function (e, value, row, index)
        {
            if(confirm("是否重置密码？"))
            {
                $.ajax({
                    type:'post',
                    url:CTX+ "/operator/updatePwd",
                    data:{id:row.customerId},
                    success:function (result) {
                        if(result.code==200)
                        {
                            alert(result.message);
                            $('#myTable').bootstrapTable('refresh');
                        }
                        else
                        {
                            alert(result.Message);
                        }
                    }
                })
            }
        }
    }
    return oTableInit;
};
//搜索按钮
$("#searchButton").click(function () {
    if($("#searchUserName").val()!="")
    {
        searchUserName=$("#searchUserName").val();
    }
    else
    {
        searchUserName=null;
    }
    if($("#searchState option:selected").text()!="--请选择--")
    {
        searchState=$("#searchState option:selected").val();
    }
    else
    {
        searchState=null;
    }
    $('#myTable').bootstrapTable('refresh');
});
$(function () {
    $("#queren").click(function () {

        $('#myTable').bootstrapTable('refresh');
    });

});



