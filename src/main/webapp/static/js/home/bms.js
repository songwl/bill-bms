/**
 * Created by Administrator on 2017/4/10.
 */
    function toIndex() {

        var url = "/bill/home";
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
                 $(".passnewDiv").css({ "color": "#ff0000" }).text("密码格式正确！");
                 $(".passnewDiv").show();
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
                $(".passokDiv").css({ "color": "#ff0000" }).text("两次密码一致！");
                $(".passokDiv").show();
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
})
