/**
 * Created by Administrator on 2017/4/10.
 */
function toIndex() {

        var url = CTX+"/home";
        if (url != null && url != 'javascript:;') {
            $.get(url, function(data) {
                $('.page-content').html(data);
            });
        }
    }
$(document).ready(function () {
    toIndex();
    $("#clickUser").click(function () {

        if($(".userDetails").css("display")=="block")
        {
            $(".userDetails").slideUp();
        }
        else
        {
            $(".userDetails").slideDown();
            $(".messageShow").slideUp();
        }
    })
    $("#clickMessage").click(function () {

        if($(".messageShow").css("display")=="block")
        {
            $(".messageShow").slideUp();
        }
        else
        {
            $(".messageShow").slideDown();
            $(".userDetails").slideUp();
        }
    })
    $("#menuClick").click(function () {
        $("#menuShow").css({"display":"none"});
    })


    $(".bmsclose").click(function () {

        $(".modal-backdrop").hide();
        $(".updateUserDiv").slideUp();
    })
    $(".bmscancel").click(function () {

        $(".modal-backdrop").hide();
        $(".updateUserDiv").slideUp();
        $(".updatePwdDiv").slideUp();

    })

    //信息修改
    $(".updateUser").click(function () {
        $(".modal-backdrop").show();
        $(".updateUserDiv").slideDown();
    })
    $(".updateUserCmt").click(function () {
           $.ajax({
               type:"post",
               url:CTX+"/user/updateUser",
               data:{
                   userName: $("input[name='userNameBms']").val(),
                   realName:$("input[name='realNameBms']").val(),
                  contact:$("input[name='contactBms']").val(),
                   phone:$("input[name='phoneBms']").val(),
                   qq:$("input[name='qqBms']").val(),
               },
               success:function (result) {
                   if(result.code==200)
                   {
                       alert(result.message);

                   }
                   else
                   {
                       alert(result.message);
                   }
               }
               

           })
    })
   //修改密码
    $(".updatePwd").click(function () {
        $(".modal-backdrop").show();
        $(".updatePwdDiv").slideDown();
    })
    var pwdold=false;
    var pwdnew=false;
    var pwdok=false;
    $("#passold").blur(function () {
        $.ajax({
            type:'get',
            url:CTX+'/user/checkPwd',
            data:{password: $("#passold").val()},
            success:function (result) {
                if(result.code==200)
                {
                    $(".passoldDiv").text(result.message);
                    $(".passoldDiv").show();
                    pwdold=true;
                }
                else
                {
                    $(".passoldDiv").text(result.message);
                    $(".passoldDiv").show();
                    pwdold=false;
                }
            }
        })
    })
    $("#passold").focus(function () {
        $(".passoldDiv").text("");
        $(".passoldDiv").hide();
        pwdold=false;
    })

    $("#passnew").blur(function () {
         if ($("#passnew").val() != "")
         {
             if (/^\w{6,12}$/.test($(this).val())) {
                 pwdnew = true;
             }
             else {
                 //格式不对
                 pwdnew = false;
                 $(".passnewDiv").css({ "color": "#ff0000" }).text("密码格式不正确！");
                 $(".passnewDiv").show();
             }
         }
     })
     $("#passnew").focus(function () {
         $(".passnewDiv").hide();
         $(".passnewDiv").text("");
         pwdnew = false;
         pwdok=false;
     });

    //确认密码
    $("#passok").blur(function () {
        if ($("#passok").val() != ""&&pwdnew==true)
        {
            if ($("#passok").val() == $("#passnew").val()) {
                pwdok = true;
            }
            else {
                //格式不对
                pwdok = false;
                $(".passokDiv").css({ "color": "#ff0000" }).text("两次密码不一致！");
                $(".passokDiv").show();
            }
        }
    });
    $("#passok").focus(function () {
        $(".passokDiv").hide();
        $(".passokDiv").text("");
        pwdok=false;
    });
    $(".updatePwdcmt").click(function () {
        if(pwdold&&pwdnew&&pwdok)
        {
                $.ajax({
                    type:"post",
                    url:CTX+"/user/updateUserPwd",
                    data:{
                           password: $("#passok").val()
                    },
                    success:function (result) {
                        if(result.code==200)
                        {
                            alert(result.message);
                            $(".modal-backdrop").hide();
                            $(".updatePwdDiv").slideUp();

                        }
                        else
                        {
                            alert(result.message);
                        }
                    }


                })
        }
        else
        {
            alert("信息有误,请重新尝试！");
        }
    })
    $("#messageClick").click(function () {
        var index1;
        index1 = layer.open({
            type: 1,
            title: '消息盒子',
            skin: 'layui-layer-molv',
            shade: 0.6,
            area: ['35%', '60%'],
            content: $('#messageDiv'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            end: function (e, u) {
                $('#messageDiv').hide();
            }
        });
    })
    setInterval(function(){
        if($("#shandong").text()!="0")
        {
            $("#shandong").fadeOut(200).fadeIn(200);
        }

    },1000);


    pushMessageListCount();


})

//获取消息个数
function pushMessageListCount() {

    $.ajax({
        type:'get',
        url:CTX+'/pushMessageListCount',
        success:function (result) {
             if(result!=0)
             {
                 $("#shandong").text(result);
                 $("#shandong").css("display","inline-block");
             }
             else
             {
                 $("#shandong").text(result);
                $("#shandong").hide();
             }
        }
    })
}
//更改消息状态
function confirmMessage(form) {
    $.ajax({
        type:'get',
        url:CTX+'/confirmMessage',
        data:{messageId:form.messageId.value},
        success:function (result) {
            if(result.code!=200)
            {
                layer.alert(result.message, {
                    skin: 'layui-layer-molv' //样式类名  自定义样式
                    , anim: 6 //动画类型
                    , icon: 4   // icon
                });
            }
            else
            {
                $(form).fadeOut();
                pushMessageListCount();
            }
        }
    })

}

