/**
 * Created by Administrator on 2017/3/17.
 */
$("#billExamine").click(function () {
    var selectContent = $('#myTable').bootstrapTable('getSelections');
    if(selectContent == "") {
        alert('请选择一列数据!');

    }else{
        $(".modal-backdrop").show();
        $(".billExamineDiv").slideDown();

    }
})
$(".close").click(function () {
    $(".billExamineDiv").slideUp();
    $(".modal-backdrop").hide();

})
$(".cancel").click(function () {
    $(".billExamineDiv").slideUp();
    $(".modal-backdrop").hide();
})
//渠道商审核订单
    $(".qudaoShenhe").click(function () {

        var rankend = parseInt(jQuery("input[name='rankend']").val());
        var price = parseFloat(jQuery("input[name='price']").val());
        var rankend1 = parseInt(jQuery("input[name='rankend1']").val());
        var price1 = parseFloat(jQuery("input[name='price1']").val());
        var rankend2 = parseInt(jQuery("input[name='rankend2']").val());
        var price2 = parseFloat(jQuery("input[name='price2']").val());
        var rankend3 = parseInt(jQuery("input[name='rankend3']").val());
        var price3 = parseFloat(jQuery("input[name='price3']").val());
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

                var rankendA = parseInt(jQuery("input[name='rankend']").val());
                var priceA = parseFloat(jQuery("input[name='price']").val());
                var rankend1A = parseInt(jQuery("input[name='rankend1']").val());
                var price1A = parseFloat(jQuery("input[name='price1']").val());
                var rankend2A = parseInt(jQuery("input[name='rankend2']").val());
                var price2A = parseFloat(jQuery("input[name='price2']").val());
                var rankend3A = parseInt(jQuery("input[name='rankend3']").val());
                var price3A = parseFloat(jQuery("input[name='price3']").val());
                var selectContent = $('#myTable').bootstrapTable('getSelections');
                var len =selectContent.length;

                $.ajax({
                    type:'post',
                    url:CTX+"/bill/billList/distributorPrice",
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
                    success:function (result) {
                        if(result.code==200)
                        {
                            alert("审核成功");
                            $(".billExamineDiv").slideUp();
                            $(".modal-backdrop").hide();
                            $('#myTable').bootstrapTable('refresh');
                        }
                        else
                        {
                            window.location.href=CTX+"/user/register";
                        }
                    }

                })


        }
        else
        {
            alert("前N名依次增大，并且值介于1-50之间，收费依次减小，并且值大于0小于1000；前N名和收费必须同时提供");

        }


    })
//管理员审核订单
$(".adminshenhe").click(function () {

    var rankend = parseInt(jQuery("input[name='adminrankend']").val());
    var price = parseFloat(jQuery("input[name='adminprice']").val());
    var rankend1 = parseInt(jQuery("input[name='adminrankend1']").val());
    var price1 = parseFloat(jQuery("input[name='adminprice1']").val());
    var rankend2 = parseInt(jQuery("input[name='adminrankend2']").val());
    var price2 = parseFloat(jQuery("input[name='adminprice2']").val());
    var rankend3 = parseInt(jQuery("input[name='adminrankend3']").val());
    var price3 = parseFloat(jQuery("input[name='adminprice3']").val());
    var caozuoyuan=$("#caozuoyuan  option:selected").val();
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
        && 1000 > price && price > 0||caozuoyuan=="--请选择--"
    )
    {

        var rankendA = parseInt(jQuery("input[name='adminrankend']").val());
        var priceA = parseFloat(jQuery("input[name='adminprice']").val());
        var rankend1A = parseInt(jQuery("input[name='adminrankend1']").val());
        var price1A = parseFloat(jQuery("input[name='adminprice1']").val());
        var rankend2A = parseInt(jQuery("input[name='adminrankend2']").val());
        var price2A = parseFloat(jQuery("input[name='adminprice2']").val());
        var rankend3A = parseInt(jQuery("input[name='adminrankend3']").val());
        var price3A = parseFloat(jQuery("input[name='adminprice3']").val());
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len =selectContent.length;
        var caozuoyuan1=$("#caozuoyuan  option:selected").val();
        $.ajax({
            type:'post',
            url:CTX+"/bill/billList/adminPrice",
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
                checkboxLength:len,
                caozuoyuan:caozuoyuan1
            },
            success:function (result) {
                if(result.code==200)
                {
                    alert("审核成功");
                    $(".billExamineDiv").slideUp();
                    $(".modal-backdrop").hide();
                    $('#myTable').bootstrapTable('refresh');
                }
                else
                {
                    window.location.href=CTX+"/user/register"
                }
            }

        })
    }
    else
    {
        alert("前N名依次增大，并且值介于1-50之间，收费依次减小，并且值大于0小于1000；前N名和收费必须同时提供，专员必须选择");

    }


})
