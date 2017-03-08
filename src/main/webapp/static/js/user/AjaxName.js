/**
 * Created by Administrator on 2017/3/7.
 */
$(document).ready(function() {
    //粒子背景特效
    $('body').particleground({
        dotColor: '#5cbdaa',
        lineColor: '#5cbdaa'
    });
    //控制变量
    var logid = false;
    var pwd = false;
    var rpwd = false;
    var zhongwen = false;
    //用户名
    $("#logid").blur(function () {
        if ($("#logid").val() != "")
        {
            var userName=$("#logid").val();
            if (/^[A-Za-z]\w{5,12}$/.test($("#logid").val())) {


                $.ajax({
                    type: "get",
                    data:{userName:userName},
                    url:CTX+"/user/validUserName",
                    dataType:"json",
                    success:function (result) {
                        if (result.code==200){ //返回结果code==200代表正确
                            //验证可以使用,然后。。。
                        }else{
                            //验证为已注册,不能使用,然后。。。
                        }
                        var msg = result.message;  //这是返回结果给的消息
                        alert(msg);
                    }

                })

            }
            else {
                //格式不对
                logid = false;
                $(".pdlogid").css({ "color": "#ff0000" }).text("用户名格式不正确！");
            }

        }


    });
    $("#logid").focus(function(){
        $(".pdlogid").css({ "color": "#ff0000" }).text("");

    });

    //密码
    $("#pwd").blur(function () {
        if ($("#pwd").val() != "")
        {
            if (/^\w{6,12}$/.test($(this).val())) {
                pwd = true;
                $(".pdpwd").css({ "color": "#00ff00" }).text("√");
            }
            else {
                //格式不对
                pwd = false;
                $(".pdpwd").css({ "color": "#ff0000" }).text("密码格式不正确！");
            }
        }


    });
    $("#pwd").focus(function () {
        $(".pdpwd").css({ "color": "#ff0000" }).text("");

    });

    //确认密码
    $("#rpwd").blur(function () {

        if ($("#pwd").val() != ""&&pwd==true)
        {
            if ($("#rpwd").val() == $("#pwd").val()) {
                rpwd = true;
                $(".pdrpwd").css({ "color": "#00ff00" }).text("√");
            }
            else {
                //格式不对
                rpwd = false;
                $(".pdrpwd").css({ "color": "#ff0000" }).text("两次密码不一致！");
            }


        }

    });
    $("#rpwd").focus(function () {
        $(".pdrpwd").css({ "color": "#ff0000" }).text("");

    });

    //中文姓名
    $("#zhongwen").blur(function () {
        if ($("#zhongwen").val() != "")
        {
            if (/^[\u4E00-\u9FA5]+$/.test($(this).val())) {
                zhongwen = true;
                $(".zhongwen").css({ "color": "#00ff00" }).text("√");
            }
            else {
                //格式不对
                zhongwen = false;
                $(".zhongwen").css({ "color": "#ff0000" }).text("请输入正确的姓名！");
            }
        }



    });
    $("#zhongwen").focus(function () {
        $(".zhongwen").css({ "color": "#ff0000" }).text("");

    });




});