/**
 * Created by Administrator on 2017/5/25.
 */
var keywords = "";
var clock;
var rote;
$(".price_anniu").click(function () {
   var patt = /^(\d+(\.\d{1,2})?)$/g;
    if (!patt.test($("#rote").val())) {
        layer.msg("请先输入正确的两位小数倍率")
        return;
    }
    keywords = $.trim($("#tKeywordMulti").val());
    rote = $("#rote").val();
    $("#tKeywordMulti").attr("readonly", "readonly");
    $(".price_anniu").text("查询中..").attr("disabled", "disabled");
    $("#rote").attr("disabled", "disabled");
    clickff();
    window.clearInterval(clock);
    clock = setInterval(clickff, 3000);
});

function clickff() {
    if (keywords == "") {
        window.clearInterval(clock);
        $("#tKeywordMulti").removeAttr("readonly");
        $(".price_anniu").text("批量查询").removeAttr("disabled");
        $("#rote").removeAttr("disabled");
        return;
    }
    $.ajax({
        type: 'post',
        url: CTX + '/optimizationTool/keywordpricesearchClick',
        data: {keywords: keywords, rote: $("#rote").val()},
        success: function (result) {
            if (result.code == "-1") {
                window.clearInterval(clock);
                $("#tKeywordMulti").removeAttr("readonly");
                $(".price_anniu").removeAttr("disabled").text("批量查询");
                $("#rote").removeAttr("disabled");
                alert(result.message);
                return;
            }
            if (result.data == null) {
                window.clearInterval(clock);
                $("#tKeywordMulti").removeAttr("readonly");
                $(".price_anniu").removeAttr("disabled").text("批量查询");
                $("#rote").removeAttr("disabled");
                return;
            }
            var str = "";
            var flag = true;
            var strr = "";
            var rote=parseFloat($("#rote").val());
            $.each(result.data, function (index, item) {
                strr += item.keywords + "\n";
                str += "<tr>";
                str += "<td colspan=\"2\">" + item.keywords + "</td>";
                str += item.pricebaidupc == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricebaidupc == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + (rote*item.pricebaidupc).toFixed(2) + "元/天</td>";
                str += item.pricesogoupc == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricesogoupc == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + (rote*item.pricesogoupc).toFixed(2) + "元/天</td>";
                str += item.pricesopc == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricesopc == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + (rote*item.pricesopc).toFixed(2) + "元/天</td>";
                str += item.pricebaiduwap == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricebaiduwap == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + (rote*item.pricebaiduwap).toFixed(2) + "元/天</td>";
                str += item.pricesm == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricesm == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + (rote*item.pricesm).toFixed(2) + "元/天</td>";
                str += "</tr>";
                if (item.pricebaidupc == null) {
                    flag = false;
                }
            });
            $("#t_body").empty().append(str);
            strr = strr.substr(0, strr.length - 1);
            $("#tKeywordMulti").val(strr);
            if (flag) {
                window.clearInterval(clock);
                $("#tKeywordMulti").removeAttr("readonly");
                $(".price_anniu").removeAttr("disabled").text("批量查询");
                $("#rote").removeAttr("disabled");
            }
        },
        error: function () {
            window.clearInterval(clock);
            $("#tKeywordMulti").removeAttr("readonly");
            $(".price_anniu").removeAttr("disabled").text("批量查询");
            $("#rote").removeAttr("disabled");
        }

    })
}

function loopall() {
    $.ajax({
        type: 'post',
        url: CTX + '/optimizationTool/LoopAllKeywords',
        data: null,
        success: function (result) {
            alert(result);
        }
    });
}


