/**
 * Created by 鱼在我这里。 on 2017/3/19.
 */

//不同价订单导入
$(".dfpricecmt").click(function () {
    var dfsearch=$("#dfsearch").val();
    var dfrankend=$("#dfrankend").val();
    var dfkeyword=$("#dfkeyword").val();
    var dfurl=$("#dfurl").val();
    var dfprice=$("#dfprice").val();
    $.ajax({
        type:'post',
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

