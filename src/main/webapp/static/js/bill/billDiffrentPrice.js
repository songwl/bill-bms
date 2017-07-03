/**
 * Created by 鱼在我这里。 on 2017/3/19.
 */

//不同价订单导入
$(".dfpricecmt").click(function () {
    var search = $("#dfsearch option:selected").text();
    var rankend = $.trim($("#dfrankend").val());
    var keyword = $.trim($("#dfkeyword").val());
    var url = $.trim($("#dfurl").val());
    var price = $.trim($("#dfprice").val());
    var customerIds = $("#selectDiffrent option:selected").val();
    var keywordarr = keyword.split('\n');
    var urlarr = url.split('\n');
    var pricearr = price.split('\n');
    var billType=$("#billType").val();
    var  index;
    var bool = false;
    if (keywordarr.length >= 1 && urlarr.length >= 1 && pricearr.length >= 1
        && keywordarr.length == urlarr.length && keywordarr.length == pricearr.length
        && rankend >= 1 && rankend < 50 && search != "--请选择--" && customerIds != "0") {
        for (var i = 0; i < keywordarr.length; i++) {
            if (keywordarr[i] != '' && urlarr[i] != '' && pricearr[i] != '' && pricearr[i] > 0) {
                bool = true;
            }
            else {
                bool = false;
                break;
            }
        }
    }
    if (bool) {
        $.ajax({
            type: 'post',
            url: CTX + "/order/list/diffrentprice",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: 'json',
            data: {
                dfsearch: search,
                customerIds: customerIds,
                dfrankend: rankend,
                dfkeyword: keyword,
                dfurl: url,
                dfprice: price,
                billType:billType
            },
            beforeSend: function () {
                index  = layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
            },
            success: function (result) {
                $(".pload").hide();
                if (result.code == 200) {
                    if (result.message == "") {
                        $(".differentpriceDiv").slideUp();
                        $(".modal-backdrop").hide();
                        $('#myTable').bootstrapTable('refresh');
                        $("#selectDiffrent").val("0");
                        $("#dfrankend").val("");
                      $("#dfsearch").val("0");
                       $("#dfkeyword").val("");
                       $("#dfurl").val("");
                      $("#dfprice").val("");
                        layer.alert("导入成功!", {
                            skin: 'layui-layer-molv' //样式类名
                            ,closeBtn: 0
                        });

                    }
                    else {
                        layer.alert(result.message, {
                            skin: 'layui-layer-molv' //样式类名
                            ,closeBtn: 0
                        });
                    }
                }
                else {

                    layer.alert("系统繁忙，请稍后再试！", {
                        skin: 'layui-layer-molv' //样式类名
                        ,closeBtn: 0
                    });

                }
                layer.close(index);
            }

        })

    }
    else {
        layer.alert("填写信息不完整或信息有误，请检查订单信息！", {
            skin: 'layui-layer-molv' //样式类名
            ,closeBtn: 0
        });

    }

});






