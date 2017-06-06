/**
 * Created by Administrator on 2017/5/25.
 */
var keywords = "";
var clock;
$(".price_explanation").click(function () {
    keywords = $.trim($("#tKeywordMulti").val());
    clickff();
    window.clearInterval(clock);
    clock = setInterval(clickff, 3000);
});

function clickff() {
    if (keywords == "") {
        window.clearInterval(clock);
        return;
    }
    $.ajax({
        type: 'post',
        url: CTX + '/optimizationTool/keywordpricesearchClick',
        data: {keywords: keywords},
        success: function (result) {
            if (result.data == null) {
                window.clearInterval(clock);
                return;
            }
            var str = "";
            var flag = true;
            var strr = "";
            $.each(result.data, function (index, item) {
                strr += item.keywords + "\n";
                str += "<tr>";
                str += "<td colspan=\"2\">" + item.keywords + "</td>";
                str += item.pricebaidupc == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricebaidupc == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.pricebaidupc + "元/天</td>";
                str += item.pricesogoupc == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricesogoupc == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.pricesogoupc + "元/天</td>";
                str += item.pricesopc == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricesopc == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.pricesopc + "元/天</td>";
                str += item.pricebaiduwap == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricebaiduwap == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.pricebaiduwap + "元/天</td>";
                str += item.pricesm == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricesm == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.pricesm + "元/天</td>";
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
            }
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


