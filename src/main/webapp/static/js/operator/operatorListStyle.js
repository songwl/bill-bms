/**
 * Created by Administrator on 2017/3/17.
 */
//添加操作员
$("#addOperator").click(function () {

        $(".modal-backdrop").show();
        $(".addOperatorDiv").slideDown();


})
$(".close").click(function () {
    $(".addOperatorDiv").slideUp();
    $(".modal-backdrop").hide();

})
$(".cancel").click(function () {
    $(".addOperatorDiv").slideUp();
    $(".modal-backdrop").hide();
})


//用户名
$("#logid").blur(function () {
    if ($("#logid").val() != "")
    {
        var userName=$("#logid").val();
        if (/^[A-Za-z]\w{5,12}$/.test($("#logid").val())) {


            $.ajax({
                type: "get",
                data:{userName:userName},
                url:CTX+"/user/register/validUserName",
                dataType:"json",
                success:function (result) {
                    if (result.code==200){ //返回结果code==200代表正确
                        //验证可以使用,
                        $(".pdlogid").css({ "color": "#00ff00" }).text("可以使用！");

                    }else{
                        //验证为已注册,不能使用,
                        $(".pdlogid").css({ "color": "#ff0000" }).text("用户名已存在！");

                    }

                }

            })

        }
        else {
            //格式不对

            $(".pdlogid").css({ "color": "#ff0000" }).text("用户名格式不正确！");
        }

    }


});