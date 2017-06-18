/**
 * Created by Administrator on 2017/3/17.
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

$("#billExamine").click(function () {
    var selectContent = $('#myTable').bootstrapTable('getSelections');
    if(selectContent == "") {
        alert('请选择一列数据!');

    }else{
        $(".billExamineDiv").slideDown();
        $(".modal-backdrop").show();


    }
})
$(".close").click(function () {
    $(".billExamineDiv").slideUp();
    $(".modal-backdrop").hide();
    $(".pload").hide();
})
$(".cancel").click(function () {
    $(".billExamineDiv").slideUp();
    $(".modal-backdrop").hide();
    $(".pload").hide();
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
                    url:CTX+"/order/billList/distributorPrice",
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
                    beforeSend: function () {
                        $(".pload").show();
                        $('.qudaoShenhe').attr('disabled',"true");
                    },
                    success:function (result) {
                        if(result.code==200)
                        {
                            alert(result.message);
                            $(".billExamineDiv").slideUp();
                            $(".modal-backdrop").hide();
                            $(".pload").hide();
                            $('#myTable').bootstrapTable('refresh');
                           jQuery("input[name='rankend']").val("");
                          jQuery("input[name='price']").val("");
                            jQuery("input[name='rankend1']").val("");
                            jQuery("input[name='price1']").val("");
                          jQuery("input[name='rankend2']").val("");
                            jQuery("input[name='price2']").val("");
                            jQuery("input[name='rankend3']").val("");
                            jQuery("input[name='price3']").val("");
                            $('.qudaoShenhe').removeAttr("disabled");
                        }
                        else
                        {
                            $(".pload").hide();
                            alert(result.message);
                            jQuery("input[name='rankend']").val("");
                            jQuery("input[name='price']").val("");
                            jQuery("input[name='rankend1']").val("");
                            jQuery("input[name='price1']").val("");
                            jQuery("input[name='rankend2']").val("");
                            jQuery("input[name='price2']").val("");
                            jQuery("input[name='rankend3']").val("");
                            jQuery("input[name='price3']").val("");
                            $('.qudaoShenhe').removeAttr("disabled");
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
        && 1000 > price && price > 0
    )
    {
      if(caozuoyuan!="0")
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
              url:CTX+"/order/billList/adminPrice",
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
              beforeSend: function () {
                  $(".pload").show();
                  $('.adminshenhe').attr('disabled',"true");
              },
              success:function (result) {
                  if(result.code==200)
                  {
                      alert("审核成功");
                      $(".billExamineDiv").slideUp();
                      $(".modal-backdrop").hide();
                      $(".pload").hide();
                      $('#myTable').bootstrapTable('refresh');
                      $("#caozuoyuan").val("0");
                      jQuery("input[name='adminrankend']").val("");
                      jQuery("input[name='adminprice']").val("");
                      jQuery("input[name='adminrankend1']").val("");
                      jQuery("input[name='adminprice1']").val("");
                      jQuery("input[name='adminrankend2']").val("");
                      jQuery("input[name='adminprice2']").val("");
                      jQuery("input[name='adminrankend3']").val("");
                      jQuery("input[name='adminprice3']").val("");
                      $('.adminshenhe').removeAttr("disabled");
                  }
                  else
                  {
                      alert(result.message);
                      $(".pload").hide();
                      $("#caozuoyuan").val("0");
                      jQuery("input[name='adminrankend']").val("");
                      jQuery("input[name='adminprice']").val("");
                      jQuery("input[name='adminrankend1']").val("");
                      jQuery("input[name='adminprice1']").val("");
                      jQuery("input[name='adminrankend2']").val("");
                      jQuery("input[name='adminprice2']").val("");
                      jQuery("input[name='adminrankend3']").val("");
                      jQuery("input[name='adminprice3']").val("");
                      $('.adminshenhe').removeAttr("disabled");
                  }
              }

          })
      }
      else
      {
          alert("专员必须选择!");
      }

    }
    else
    {
        alert("前N名依次增大，并且值介于1-50之间，收费依次减小，并且值大于0小于1000；前N名和收费必须同时提供");

    }


})
//审核不通过
$("#billNotExamine").click(function () {
    var selectContent = $('#myTable').bootstrapTable('getSelections');
    var len = selectContent.length;
    var index;
    if (selectContent=="")
    {
        layer.alert('请选择一列数据!', {
            skin: 'layui-layer-molv' //样式类名
            ,closeBtn: 0
        });
    }
    else
    {
        layer.confirm('是否不通过审核？',{
                btn:['确定','取消']
            },function () {
                $.ajax({
                    type:'post',
                    data: {selectContent: selectContent, length: len},
                    url:CTX+'/order/billNotExamine',
                    beforeSend: function () {
                        index  = layer.load(1, {
                            shade: [0.1,'#fff'] //0.1透明度的白色背景
                        });
                    },
                    success: function (result) {
                        if (result.code == 200) {

                            layer.alert(result.message, {
                                skin: 'layui-layer-molv' //样式类名
                                ,closeBtn: 0
                            });
                            $('#myTable').bootstrapTable('refresh');

                        }
                        else {
                            layer.alert(result.message, {
                                skin: 'layui-layer-molv' //样式类名
                                ,closeBtn: 0
                            });

                        }
                        layer.close(index);
                    }

                })
            }
        )
    }
})
