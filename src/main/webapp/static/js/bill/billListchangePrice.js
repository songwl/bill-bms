/**
 * Created by Administrator on 2017/3/15.
 */
$(document).ready(function () {

    $(".changeprice").click(function () {

        var rankend = parseInt(jQuery("input[name='changerankend']").val());
        var price = parseFloat(jQuery("input[name='changeprice']").val());
        var rankend1 = parseInt(jQuery("input[name='changerankend1']").val());
        var price1 = parseFloat(jQuery("input[name='changeprice1']").val());
        var rankend2 = parseInt(jQuery("input[name='changerankend2']").val());
        var price2 = parseFloat(jQuery("input[name='changeprice2']").val());
        var rankend3 = parseInt(jQuery("input[name='changerankend3']").val());
        var price3 = parseFloat(jQuery("input[name='changeprice3']").val());
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
            var changerankend = jQuery("input[name='changerankend']").val();
            var changeprice = jQuery("input[name='changeprice']").val();
            var changerankend1 = jQuery("input[name='changerankend1']").val();
            var changeprice1 = jQuery("input[name='changeprice1']").val();
            var changerankend2 = jQuery("input[name='changerankend2']").val();
            var changeprice2 = jQuery("input[name='changeprice2']").val();
            var changerankend3 = jQuery("input[name='changerankend3']").val();
            var changeprice3 = jQuery("input[name='changeprice3']").val();
            var selectContent = $('#myTable').bootstrapTable('getSelections');
            alert(selectContent);
            $.ajax({
                type:'get',
                url:CTX+"/bill/billList/updatePrice",
                data:{
                    rankend:changerankend,
                    price:changeprice,
                    rankend1:changerankend1,
                    price1:changeprice1,
                    rankend2:changerankend2,
                    price2:changeprice2,
                    rankend3:changerankend3,
                    price3:changeprice3,
                    selectContent:selectContent
                },
                success:function (result) {
                    
                }
                
            })
        }
        else
        {
            alert("前N名依次增大，并且值介于1-50之间，收费依次减小，并且值大于0小于1000；前N名和收费必须同时提供");

        }


    })
})