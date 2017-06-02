/**
 * Created by Administrator on 2017/5/25.
 */
var keywords = "";
var clock;
$(".price_explanation").click(function () {
    keywords = $.trim($("#tKeywordMulti").val());
    clickff();
    clock = setInterval(clickff, 5000);
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
            var str = "";
            var flag = true;
            $.each(result.data, function (index, item) {
                str += "<tr>";
                str += "<td colspan=\"2\">" + item.keywords + "</td>";
                str += item.pricebaidupc == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricebaidupc == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.pricebaidupc + "元/月</td>";
                str += item.pricesogoupc == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricesogoupc == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.pricesogoupc + "元/月</td>";
                str += item.pricesopc == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricesopc == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.pricesopc + "元/月</td>";
                str += item.pricebaiduwap == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricebaiduwap == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.pricebaiduwap + "元/月</td>";
                str += item.pricesm == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.pricesm == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.pricesm + "元/月</td>";
                str += "</tr>";
                if (item.pricebaidupc == null) {
                    flag = false;
                }
            });
            $("#t_body").empty().append(str);
            if (flag) {
                window.clearInterval(clock);
            }
        }

    })
}


