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

    var bool = false;
    if (keywordarr.length >= 1 && urlarr.length >= 1 && pricearr.length >= 1
        && keywordarr.length == urlarr.length && keywordarr.length == pricearr.length
        && rankend > 1 && rankend < 50 && search != "--请选择--" && customerIds != "--请选择--") {
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
            url: CTX + "/bill/list/diffrentprice",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: 'json',
            data: {
                dfsearch: search,
                customerIds: customerIds,
                dfrankend: rankend,
                dfkeyword: keyword,
                dfurl: url,
                dfprice: price
            },
            beforeSend: function () {
                $(".pload").show();
            },
            success: function (result) {
                $(".pload").hide();
                if (result.code == 200) {
                    if (result.message == "") {
                        alert("导入成功!");
                        $(".differentpriceDiv").slideUp();
                        $(".modal-backdrop").hide();
                        $('#myTable').bootstrapTable('refresh');
                    }
                    else {

                        alert(result.message + " 已经存在!");
                    }
                }
                else {

                    alert("系统繁忙，请稍后再试！");
                }
            }

        })

    }
    else {
        alert("填写信息不完整或信息有误，请检查订单信息")
    }
});






