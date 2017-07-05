/**
 * Created by 鱼在我这里。 on 2017/3/16.
 */

var searchUserName = null;
var searchState = null;
$(document).ready(function () {

    $("input[name='isOffer']").click(function () {
        switch ($("input[name='isOffer']:checked").attr("id")) {
            case "open":
                $(".setkeyword").show();
                $("#keywordNumLabel").show();
                $("#roteLabel").show();
                break;
            case "close":
                $(".setkeyword").hide();
                $("#keywordNumLabel").hide();
                $("#roteLabel").hide();
                break;
        }
    });
    $("#confim").click(function () {
        switch ($("input[name='isOffer']:checked").attr("id")) {
            case "open":
                if ($("#keywordNum").val() == "") {
                    layer.msg("请输入关键词数量");
                    return;
                }
                if ($("#rote").val() == "") {
                    layer.msg("请输入倍率");
                    return;
                }
                if (isNaN($("#keywordNum").val())) {
                    layer.msg("请输入数字");
                    return;
                }
                $.ajax({
                    url: CTX + "/optimizationTool/SetOffer",
                    type: "post",
                    data: {
                        type: 1,
                        keywordNum: $("#keywordNum").val(),
                        rote: $("#rote").val(),
                        dataUser: $("#confim").attr("dataUser")
                    },
                    success: function (data) {
                        if (data.code == 0) {
                            layer.close(index1);
                            layer.msg("成功");
                        }
                        else {
                            layer.msg(data.message);
                        }
                    }
                })
                break;
            case "close":
                $.ajax({
                    url: CTX + "/optimizationTool/SetOffer",
                    type: "post",
                    data: {type: 0, keywordNum: "0", dataUser: $("#confim").attr("dataUser")},
                    success: function (data) {
                        if (data.code == 0) {
                            layer.close(index1);
                            layer.msg("成功");
                        }
                        else {
                            layer.msg(data.message);
                        }
                    }
                });
                break;
        }
    });
//显示搜索内容
    $(".search").click(function () {
        if ($(".Navs2").css("display") == "block") {
            $(".Navs2").slideUp();
        }
        else {
            $(".Navs2").slideDown();
        }
    })
    //复选框
    $(".updateDailiRole").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        if (selectContent == "") {
            alert('请选择一列数据!');

        } else {
            if (confirm("是否开通代理权限?")) {
                $.ajax({
                    type: "post",
                    data: {selectContent: selectContent, length: len},
                    url: CTX + "/customer/updateDailiRole",
                    dataType: "json",
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


    //添加客户
    $(".addMember").click(function () {

        $(".modal-backdrop").show();
        $(".addMemberDiv").slideDown();
        $(".modal-title").html("添加客户");
        $("#addMemberId").val("1");
        $("#nameDiv1").hide();
        $("#nameDiv").show();
        $("#viewbalance").show();
        $("#viewstate").hide();
        $("#viewpwd").show();
        $("input[name='realName']").val("");
        $("input[name='contact']").val("");
        $("input[name='phone']").val("");
        $("input[name='qq']").val("");
        $(".addOperatorcmt").show();
        $(".updateOperatorcmt").hide();

    })
    //添加代理商
    $(".addAGENT").click(function () {
        $(".modal-backdrop").show();
        $(".addMemberDiv").slideDown();
        $(".modal-title").html("添加代理商");
        $("#addMemberId").val("2");
        $("#nameDiv1").hide();
        $("#nameDiv").show();
        $("#viewbalance").show();
        $("#viewstate").hide();
        $("#viewpwd").show();
        $("input[name='realName']").val("");
        $("input[name='contact']").val("");
        $("input[name='phone']").val("");
        $("input[name='qq']").val("");
        $(".addOperatorcmt").show();
        $(".updateOperatorcmt").hide();
    })
    //添加助理
    $(".addASSISTANT").click(function () {
        $(".modal-backdrop").show();
        $(".addMemberDiv").slideDown();
        $(".modal-title").html("添加助理");
        $("#addMemberId").val("3");
        $("#nameDiv1").hide();
        $("#nameDiv").show();
        $("#viewbalance").show();
        $("#viewstate").hide();
        $("#viewpwd").show();
        $("input[name='realName']").val("");
        $("input[name='contact']").val("");
        $("input[name='phone']").val("");
        $("input[name='qq']").val("");
        $(".addOperatorcmt").show();
        $(".updateOperatorcmt").hide();
    })
    $(".close").click(function () {
        $(".addMemberDiv").slideUp();
        $(".modal-backdrop").hide();
        $(".RechargeDiv").slideUp();


    })
    $(".cancel").click(function () {
        $(".addMemberDiv").slideUp();
        $(".modal-backdrop").hide();
        $(".RechargeDiv").slideUp();

    })

    var userName = false;
    var password = false;

//用户名
    $("input[name='userName']").blur(function () {

        if ($("input[name='userName']").val() != "") {
            if (/^[A-Za-z]\w{5,12}$/.test($("input[name='userName']").val())) {
                $.ajax({
                    type: "get",
                    data: {userName: $("input[name='userName']").val()},
                    url: CTX + "/user/register/validUserName",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 200) {
                            //返回结果code==200代表正确
                            //验证可以使用,
                            userName = true;
                        } else {
                            //验证为已注册,不能使用,
                            $(".pdlogid").css({"color": "#ff0000"}).text("用户名已存在！");
                            userName = false;
                        }

                    }

                })

            }
            else {
                //格式不对
                userName = false;
                $(".pdlogid").css({"color": "#ff0000"}).text("用户名格式不正确！");
            }

        }
    });
//密码
    $("input[name='password']").blur(function () {

        if ($("input[name='password']").val() != "") {
            if (/^\w{6,12}$/.test($(this).val())) {
                password = true;

            }
            else {
                //格式不对
                password = false;
                $(".pdpwd").css({"color": "#ff0000"}).text("密码格式不正确！");
            }
        }
    });
    $("input[name='userName']").focus(function () {
        $(".pdlogid").css({"color": "#ff0000"}).text("");
        userName = false;

    });
    //添加客户
    $(".addOperatorcmt").click(function () {
        if (userName && password) {
            $.ajax({
                type: "post",
                data: {
                    userName: $("input[name='userName']").val(),
                    password: $("input[name='password']").val(),
                    addMemberId: $("#addMemberId").val(),
                    realName: $("input[name='realName']").val(),
                    contact: $("input[name='contact']").val(),
                    phone: $("input[name='phone']").val(),
                    qq: $("input[name='qq']").val(),
                    balance: $("#balance").val()
                },
                url: CTX + "/customer/customerList",
                dataType: "json",
                success: function (result) {
                    if (result.code == 200) {
                        $(".modal-backdrop").hide();
                        $(".addMemberDiv").slideUp();

                        $('#myTable').bootstrapTable('refresh');

                        alert(result.message);
                    }
                    else {
                        alert(result.message);
                    }

                }

            })
        }
        else {
            alert("信息填写不正确！");
        }

    })

    //充值确认
    $(".Rechargecmt").click(function () {
        var RechargeSum = $("#RechargeSum").val();
        var customerId = $("#customerId").val();
        if (!isNaN(RechargeSum) && RechargeSum > 0) {
            $.ajax({
                type: "post",
                url: CTX + "/customer/Recharge",
                data: {customerId: customerId, RechargeSum: RechargeSum},
                success: function (result) {
                    if (result.code == 200) {
                        alert(result.message);
                        $(".modal-backdrop").hide();
                        $(".RechargeDiv").slideUp();
                        $('#myTable').bootstrapTable('refresh');
                    }
                    else {
                        alert(result.message);
                    }
                }

            })
        }
        else {
            alert("请填入正确的充值金额！");
        }
    })

    //退款确认
    $(".Refoundcmt").click(function () {
        var RechargeSum = $("#RechargeSum").val();
        var customerId = $("#customerId").val();
        if (!isNaN(RechargeSum) && RechargeSum > 0) {
            $.ajax({
                type: "post",
                url: CTX + "/customer/Refound",
                data: {customerId: customerId, RechargeSum: RechargeSum},
                success: function (result) {
                    if (result.code == 200) {
                        alert(result.message);
                        $(".modal-backdrop").hide();
                        $(".RechargeDiv").slideUp();
                        $('#myTable').bootstrapTable('refresh');
                    }
                    else {
                        alert(result.message);
                    }
                }

            })
        }
        else {
            alert("请填入正确的退款金额！");
        }
    })

    //更改客户信息
    $(".updateOperatorcmt").click(function () {
        if (/^[A-Za-z]\w{5,18}$/.test($("input[name='userName1']").val())) {
            $.ajax({
                type: 'post',
                url: CTX + "/operator/updateOperator",
                data: {
                    id: $("#customerId").val(),
                    userName: $("input[name='userName1']").val(),
                    realName: $("input[name='realName']").val(),
                    contact: $("input[name='contact']").val(),
                    phone: $("input[name='phone']").val(),
                    qq: $("input[name='qq']").val(),
                    status: $("#viewstatus option:selected").val()
                },
                success: function (result) {
                    if (result.code == 200) {
                        alert(result.message);
                        $(".addOperatorDiv").slideUp();
                        $(".addMemberDiv").slideUp();
                        $(".modal-backdrop").hide();
                        $('#myTable').bootstrapTable('refresh');
                    }
                    else {
                        alert(result.Message);
                    }
                }
            })
        }
        else {
            alert("用户名不能为空或格式错误!");
        }
    })
    //分配订单确认
    $("#billTypeCmt").click(function () {
        var userId = $("#getUserId").val();
        var kuaipai = null;
        var baonian = null;
        var chuzu = null;
        if ($("#kuaipai").is(':checked')) {
            kuaipai = 1;
        }
        if ($("#baonian").is(':checked')) {
            baonian = 2;
        }
        if ($("#chuzu").is(':checked')) {
            chuzu = 3;
        }
        $.ajax({
            type: 'post',
            url: CTX + "/customer/billTypeCmt",
            data: {kuaipai: kuaipai, baonian: baonian, chuzu: chuzu, userId: userId},
            success: function (result) {
                if (result == 200) {
                    layer.alert(result.message, {
                        skin: 'layui-layer-molv' //样式类名  自定义样式
                        , anim: 1 //动画类型
                        , icon: 4   // icon
                    });
                }
                else {
                    layer.alert(result.message, {
                        skin: 'layui-layer-molv' //样式类名  自定义样式
                        , anim: 6 //动画类型
                        , icon: 4   // icon
                    });
                }
                $('#myTable').bootstrapTable('refresh');
            }
        })


    })

})
$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    var oTable1 = new TableInit();
    oTable1.Init();

    //2.初始化Button的点击事件
    //var oButtonInit = new ButtonInit();
    //oButtonInit.Init();


});

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#myTable').bootstrapTable({
            url: CTX + '/customer/getCustomerList',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 20,                       //每页的记录行数（*）
            pageList: [100, 200, 300, 500, 1000],        //可供选择的每页的行数（*）
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
                }, {
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
                    visible: false

                },
                {
                    field: 'userName',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '客户',
                    formatter: function (value, row, index) {
                        var a = "";

                        a = "<span style='color:#4382CF;cursor:pointer;' id='details'>" + value + "</span>";
                        return a;
                    }

                },
                {
                    field: 'kehuRoleName',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '角色',

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
                    valign: 'middle',
                    title: 'qq'
                },
                {
                    field: 'phone',
                    align: 'center',
                    valign: 'middle',
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
                    title: '最新登录',

                },

                {
                    field: "loginCount",
                    align: 'center',
                    valign: 'middle',
                    title: '登录次数',


                },
                {
                    field: "missionCount",
                    align: 'center',
                    valign: 'middle',
                    title: '任务数',


                },
                {
                    field: "balance",
                    align: 'center',
                    valign: 'middle',
                    title: '余额',


                },
                {
                    field: "status",
                    align: 'center',
                    valign: 'middle',
                    title: '状态',
                    formatter: function (value, row, index) {

                        var a = "";
                        if (value == true) {
                            a = "<span style='color:#4382CF;cursor:pointer;' id='details'>正常</span>";
                        }
                        else {
                            a = "<span>冻结中</span>";
                        }
                        return a;
                    }


                },
                {
                    field: "dailiRole",
                    align: 'center',
                    valign: 'middle',
                    title: '代理权限',
                    visible: false

                },

                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    width: '230',
                    valign: 'middle',
                    formatter: function (value, row, index) {

                        var a = '';
                        if (row.roleName == 'ASSISTANT') {
                            a = "<span style='color:#4382CF;cursor:pointer;' id='details'>资料</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='changepwd'>改密</span>   ";

                        }
                        else if (row.roleName == 'SUPER_ADMIN') {
                            a = "<span style='color:#4382CF;cursor:pointer;' id='recharge'>充值</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='refund'>退款</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='details'>资料</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='changepwd'>改密</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='OfferSetUp' data-user='" + value + "'>报价</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='AdminwebsiteLeaseSet'>网租</span>  " +
                                " <span style='color:#4382CF;cursor:pointer;' id='billType'> 订单属性</span> ";

                        }
                        else if (row.roleName == 'DISTRIBUTOR') {
                            a = "<span style='color:#4382CF;cursor:pointer;' id='recharge'>充值</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='refund'>退款</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='details'>资料</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='changepwd'>改密</span>   ";
                            if (row.kehuRoleName == "代理商") {
                                a += "<span style='color:#4382CF;cursor:pointer;' id='OfferSetUpAgent' data-user='" + value + "'>报价</span>   ";
                                if ($("#leasepower").val() == 1) {
                                    a += "<span style='color:#4382CF;cursor:pointer;' id='websiteLeaseSet'>网租</span>   ";
                                }
                            }
                        }else if(row.roleName == 'AGENT')
                        {
                            a = "<span style='color:#4382CF;cursor:pointer;' id='recharge'>充值</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='refund'>退款</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='details'>资料</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='changepwd'>改密</span>   "+
                                "<span style='color:#4382CF;cursor:pointer;' id='OfferSetUpAgent' data-user='" + value + "'>报价</span> ";
                        }
                        else {
                            a = "<span style='color:#4382CF;cursor:pointer;' id='recharge'>充值</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='refund'>退款</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='details'>资料</span>   " +
                                "<span style='color:#4382CF;cursor:pointer;' id='changepwd'>改密</span>   ";

                        }

                        return a;
                    },
                    events: operateEvents


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
            searchUserName: searchUserName,
            searchState: searchState,
        };
        return temp;
    }
    window.operateEvents = {
        'click #recharge': function (e, value, row, index) {
            $(".modal-backdrop").show();
            $(".RechargeDiv").slideDown();
            $(".modal-title").html("客户充值");
            $(".Amount").html("充值金额");
            $(".Rechargecmt").show();
            $(".Refoundcmt").hide();
            $("#customerId").val(row.customerId);
        },
        'click #refund': function (e, value, row, index) {
            $(".modal-backdrop").show();
            $(".RechargeDiv").slideDown();
            $(".modal-title").html("客户退款");
            $(".Amount").html("退款金额");
            $(".Rechargecmt").hide();
            $(".Refoundcmt").show();
            $("#customerId").val(row.customerId);
        },
        'click #details': function (e, value, row, index) {
            $(".modal-backdrop").show();
            $(".addMemberDiv").slideDown();
            $("#viewpwd").hide();
            $("#nameDiv1").show();
            $("#viewstate").show();
            $("#nameDiv").hide();
            $("#viewbalance").hide();
            $(".addOperatorcmt").hide();
            $(".updateOperatorcmt").show();
            $(".modal-title").html("客户信息");
            $("#customerId").val("");
            $("input[name='userName1']").val("");
            $("input[name='realName']").val("");
            $("input[name='contact']").val("");
            $("input[name='phone']").val("");
            $("input[name='qq']").val("");
            $("#customerId").val(row.customerId);
            $("input[name='userName1']").val(row.userName);
            $("input[name='realName']").val(row.realName);
            $("input[name='contact']").val(row.contact);
            $("input[name='phone']").val(row.phone);
            $("input[name='qq']").val(row.qq);
            $("#viewstatus").empty();
            if (row.status == "1") {
                $("#viewstatus").append("<option value='1'>正常</option>");
                $("#viewstatus").append("<option value='0'>冻结</option>");
            }
            else {
                $("#viewstatus").append("<option value='0'>冻结</option>");
                $("#viewstatus").append("<option value='1'>正常</option>");
            }
        },
        'click #changepwd': function (e, value, row, index) {
            if (confirm("是否重置密码？(默认重置密码:123456   重置完毕后请尽快修改默认密码)")) {
                $.ajax({
                    type: 'post',
                    url: CTX + "/operator/updatePwd",
                    data: {id: row.customerId},
                    success: function (result) {
                        if (result.code == 200) {
                            alert(result.message);
                            $('#myTable').bootstrapTable('refresh');
                        }
                        else {
                            alert(result.Message);
                        }
                    }
                })
            }
        },
        'click #delete': function (e, value, row, index) {
            if (confirm("是否删除当前用户？")) {
                $.ajax({
                    type: 'post',
                    url: CTX + "/operator/deleteUser",
                    data: {id: row.customerId},
                    success: function (result) {
                        if (result.code == 200) {
                            alert(result.message);
                            $('#myTable').bootstrapTable('refresh');
                        }
                        else {
                            alert(result.Message);
                        }
                    }
                })
            }
        },
        'click #OfferSetUp': function (e, value, row, index) {
            $("#confim").attr("dataUser", row.customerId);
            $.ajax({
                url: CTX + "/optimizationTool/GetOffer",
                type: "post",
                data: {dataUser: row.customerId},
                success: function (data) {
                    if (data.code == 0) {
                        $("#open").removeAttr("checked");
                        $("#close").prop("checked", "checked");
                        $("#keywordNum").val("0").hide();
                        $("#rote").val("").hide();
                        $("#keywordNumLabel").hide();
                        $("#roteLabel").hide();
                    }
                    else {
                        $("#open").prop("checked", "checked");
                        $("#close").removeAttr("checked");
                        $("#keywordNum").val(data.data.requestsecond).show();
                        $("#rote").val(data.data.rate).show();
                        $("#keywordNumLabel").show();
                        $("#roteLabel").show();
                    }
                }
            })
            index1 = layer.open({
                type: 1,
                title: '报价设置',
                skin: 'layui-layer-molv',
                shade: 0.6,
                area: ['30%', '45%'],
                content: $('#offerSetUp'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                end: function () {
                    $("#offerSetUp").hide();
                }
            });
        },
        'click #OfferSetUpAgent': function (e, value, row, index) {
            $("#confim").attr("dataUser", row.customerId);
            $.ajax({
                url: CTX + "/optimizationTool/GetOfferAgent",
                type: "post",
                data: {dataUser: row.customerId},
                success: function (data) {
                    if (data.code == 0) {
                        $("#open").removeAttr("checked");
                        $("#close").prop("checked", "checked");
                        $("#keywordNum").val("0").hide();
                        $("#rote").val("").hide();
                        $("#keywordNumLabel").hide();
                        $("#roteLabel").hide();
                    }
                    else {
                        $("#open").prop("checked", "checked");
                        $("#close").removeAttr("checked");
                        $("#keywordNum").val(data.data.requestsecond).show();
                        $("#rote").val(data.data.rate).show();
                        $("#keywordNumLabel").show();
                        $("#roteLabel").show();
                    }
                }
            })
            index1 = layer.open({
                type: 1,
                title: '报价设置',
                skin: 'layui-layer-molv',
                shade: 0.6,
                area: ['30%', '45%'],
                content: $('#offerSetUp'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                end: function () {
                    $("#offerSetUp").hide();
                }
            });
        },
        'click #websiteLeaseSet': function (e, value, row, index) {
            layer.confirm('请选择该代理商的网站出租平台权限设置', {
                btn: ['开通', '关闭'] //按钮
            }, function () {
                $.post(CTX + '/optimizationTool/OpenAgentWebsitePower', {
                    data: row.customerId,
                    type: 1,
                }, function (data) {
                    if (data.code == "1") {
                        layer.msg("成功");
                        return;
                    }
                    else if (data.code == "-1" || data.code == "-2") {
                        layer.msg(data.message);
                        return;
                    }
                    else {
                        layer.msg("失败");
                        return;
                    }
                })
            }, function () {
                $.post(CTX + '/optimizationTool/OpenAgentWebsitePower', {
                    data: row.customerId,
                    type: 0
                }, function (data) {
                    if (data.code == "1") {
                        layer.msg("成功");
                        return;
                    }
                    else if (data.code == "-1" || data.code == "-2") {
                        layer.msg(data.message);
                        return;
                    }
                    else {
                        layer.msg("失败");
                        return;
                    }
                })
            });

        },
        'click #AdminwebsiteLeaseSet': function (e, value, row, index) {
            layer.confirm('请选择该渠道商的网站出租平台权限设置', {
                btn: ['开通', '关闭'] //按钮
            }, function () {
                $.post(CTX + '/optimizationTool/OpenDistributorWebsitePower', {
                    data: row.customerId,
                    type: 1,
                }, function (data) {
                    if (data.code == "1") {
                        layer.msg("成功");
                        return;
                    }
                    else if (data.code == "-1" || data.code == "-2") {
                        layer.msg(data.message);
                        return;
                    }
                    else {
                        layer.msg("失败");
                        return;
                    }
                })
            }, function () {
                $.post(CTX + '/optimizationTool/OpenDistributorWebsitePower', {
                    data: row.customerId,
                    type: 0
                }, function (data) {
                    if (data.code == "1") {
                        layer.msg("成功");
                        return;
                    }
                    else if (data.code == "-1" || data.code == "-2") {
                        layer.msg(data.message);
                        return;
                    }
                    else {
                        layer.msg("失败");
                        return;
                    }
                })
            });
        },
        'click #billType': function (e, value, row, index) {
            $("#getUserId").val(row.customerId);

            $.ajax({
                type: 'get',
                url: CTX + '/customer/getUserBillType',
                data: {userId: row.customerId},
                success: function (result) {
                    console.log(result);
                    if (result != null) {
                        for (var i = 0; i < result.length; i++) {
                            if (result[i]["billType"] == 1) {
                                $("#zhengchang").attr('checked', true);
                            }
                            if (result[i]["billType"] == 2) {
                                $("#kuaipai").attr('checked', true);
                            }
                            if (result[i]["billType"] == 3) {
                                $("#baonian").attr('checked', true);
                            }
                            if (result[i]["billType"] == 4) {
                                $("#chuzu").attr('checked', true);
                            }

                        }
                    }
                }
            })


            layer.open({
                type: 1,
                title: '订单属性分配',
                skin: 'layui-layer-molv',
                shade: 0.6,
                area: ['20%', '30%'],
                content: $('#billTypeDiv'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                end: function () {
                    $("#billTypeDiv").hide();
                }
            });
        }
    }


    return oTableInit;
};
//搜索按钮
$("#searchButton").click(function () {
    if ($("#searchUserName").val() != "") {
        searchUserName = $("#searchUserName").val();
    }
    else {
        searchUserName = null;
    }
    if ($("#searchState option:selected").text() != "--请选择--") {
        searchState = $("#searchState option:selected").val();
    }
    else {
        searchState = null;
    }
    $('#myTable').bootstrapTable('refresh');
});
$(function () {
    $("#queren").click(function () {

        $('#myTable').bootstrapTable('refresh');
    });

    $("#websiteLeasePower").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        //console.info(selectContent);
        if (selectContent.length == 0) {
            layer.msg("请先选择行");
            return;
        }//询问框
        layer.confirm('是否开通已选择渠道商的网站出租平台权限？', {
            btn: ['开通', '关闭'] //按钮
        }, function () {
            $.post(CTX + '/optimizationTool/OpenWebsitePower', {
                data: selectContent,
                type: 1,
                len: selectContent.length
            }, function (data) {
                if (data.code == "1") {
                    layer.msg("成功");
                    return;
                }
                else {
                    layer.msg("失败");
                    return;
                }
            })
        }, function () {
            $.post(CTX + '/optimizationTool/OpenWebsitePower', {
                data: selectContent, type: 0,
                len: selectContent.length
            }, function (data) {
                if (data.code == "1") {
                    layer.msg("成功");
                    return;
                }
                else {
                    layer.msg("失败");
                    return;
                }
            })
        });

    });


    $("#checkData").click(function () {
        var  index;
        layer.confirm('是否校正数据？', {
            btn: ['开通', '关闭'] //按钮
        }, function () {
                $.ajax({
                    type:'post',
                    url:CTX+'/customer/checkData',
                    beforeSend: function () {
                        index  = layer.load(1, {
                            shade: [0.1,'#fff'] //0.1透明度的白色背景
                        });
                    },
                    success:function (result) {
                        layer.alert(result.message, {
                            skin: 'layui-layer-molv' //样式类名
                            ,closeBtn: 0
                        });
                        layer.close(index);
                        
                    }
                    
                })
        })

    });
});


