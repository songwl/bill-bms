/**
 * Created by Administrator on 2017/5/25.
 */

$(".price_explanation").click(function () {
    var keywords=$.trim($("#tKeywordMulti").val());
   $.ajax({
       type:'post',
       url:CTX+'/optimizationTool/keywordpricesearchClick',
       data:{keywords:keywords},
       success:function (result) {
           
       }

   })
})


