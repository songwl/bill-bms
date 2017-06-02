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
                str += item.baidu == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.baidu == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.baidu + "元/月</td>";
                str += item.sougou == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.sougou == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.sougou + "元/月</td>";
                str += item.sanliuling == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.sanliuling == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.sanliuling + "元/月</td>";
                str += item.sjbaidu == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.sjbaidu == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.sjbaidu + "元/月</td>";
                str += item.shenma == null ? "<td colspan=\"2\"><img src='/static/img/loading.gif'/></td>" : item.shenma == 0 ? "<td colspan=\"2\">暂无结果</td>" : "<td colspan=\"2\">" + item.shenma + "元/月</td>";
                str += "</tr>";
                if (item.baidu == null) {
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


