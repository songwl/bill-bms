<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "写信息">
<link href="${ctx}/static/css/Message/font-awesome.min.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/custom.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/Index.css" rel="stylesheet">

<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>个人中心</span> > <span>API参数设置</span>
    </div>

    <div class="cls">
    </div>
</div>
<div id="contentBox">
    <div class="sq-panel">
        <div class="sq-panel-heading">
            <div class="sq-panel-title">API参数设置</div>
        </div>
        <div class="sq-panel-body">

            <div class="alert-warn margin-bottom-20">
                请对API信息严格保密，泄露API参数如同泄露密码一样危险，一旦泄露，请及时重置！
            </div>
            <div class="form-horizontal sq-form sq-tab-container tab-group-undefined"
                 id="saveNotifyForm">
                <a href="#a_null" class="hide protection-enabled"></a>
                <div class="form-group">
                    <div class="sq-control-label">
                        会员ID：
                    </div>
                    <div class="col-xs-10 sq-form-control">
                    ${user.userName}
                    </div>
                </div>
                <div class="form-group">
                    <div class="sq-control-label">
                        API 密钥：
                    </div>
                    <div class="col-xs-10 sq-form-control">
                        <span id="keypt">${keypt}</span>
                        <a href="#a_null" class="sq-btn btn-primary" id="initApi">重 置</a>
                    </div>
                </div>
                <div class="form-group">
                    <div class="sq-control-label">
                        <span class="necessary-mark">*</span>
                        倍率：
                    </div>
                    <div class="col-xs-10 sq-form-control validate-control">
                        <input type="text" name="notifyUrl" id="notifyUrl"
                               class="sq-input-reset sq-long-input necessary"
                               value="#{rote}" placeholder="请输入倍率">
                        <a href="#a_null" class="sq-btn btn-strong" id="saveNotify">保 存</a>
                    </div>
                </div>
                <div class="form-group">

                    <div class="sq-control-label">
                        请求地址：
                    </div>
                    <div class="col-xs-10 sq-form-control validate-control">
                        tj.yousouyun.com/bill/QueryOffer/GetPrice
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<style type="text/css">
    .sq-panel {
        margin: 20px;
        padding: 0 20px;
        background-color: #fff;
        border: 1px solid #eee;
    }

    .sq-panel-heading {
        border-bottom: 1px solid #eee;
        padding: 20px 0 10px 0;
    }

    .sq-panel-title {
        display: inline-block;
        border-left: 3px solid #009FD9;
        margin: 0;
        text-indent: 0.5em;
        font-size: 20px;
    }

    .sq-panel-body {
        padding: 20px 0;
    }

    .alert-warn, .alert-success {
        padding: 7px 22px 5px 37px;
        border: 1px solid #ff8800;
        border-radius: 2px;
        color: #ff8800;
        font-size: 12px;
        line-height: 2em;
    }

    .sq-control-label {
        width: 135px;
        padding-right: 0;
        text-align: right;
        color: #808080;
        float: left;
    }

    .sq-form-control {
        display: inline-block;
        white-space: nowrap;
        color: #555;
        background-color: #fff;
        background-image: none;
        outline: none;
        float: left;
    }

    .form-group {
        margin-bottom: 12px;
        line-height: 30px;
    }

    .btn-primary {
        display: inline-block;
        position: relative;
        height: 28px;
        padding: 0 16px;
        background-color: #009fd9;
        border: none;
        line-height: 27px;
        color: #fff;
    }

    #saveNotify {
        display: inline-block;
        height: 28px;
        line-height: 27px;
        border: none;
        padding: 0 16px;
        background-color: #ff8800;
        outline: none;
        color: #fff;
    }
</style>
<script type="text/javascript">
    $(function () {
        ParameterSet.init();
    });
    var ParameterSet = {
        init: function () {
            $("#initApi").on("click", ParameterSet.RestKeyt);
            $("#saveNotify").on("click", ParameterSet.saveNotify);
        },
        RestKeyt: function () {
            var apiSign = "126263041AF3269DBC99093576C857B7";
            var xAction = "selectPrice";
            var xParam = "{'UserId':'yunkewang','Value':{'keyword':'淫,赌,A2级防火复合板,mpp电力管,pp风阀厂家,ipn8710防腐钢管'}}";
            $.ajax({
                type: "post",
                url: "http://localhost:8080/bill//QueryOffer/GetPrice",
                data: {xAction: xAction, xParam: xParam, apiSign: apiSign},
                success: function (data) {
                    console.info(data);
                }
            });

           /* $.ajax({
                type: "post",
                url: CTX + "/optimizationTool/RestKeyt",
                success: function (data) {
                    $("#keypt").text("").text(data);
                }
            });*/
        },
        saveNotify: function () {
            var patt = /^(\d+(\.\d{1,2})?)$/g;
            if (!patt.test($("#notifyUrl").val())) {
                layer.msg("请输入正确的两位小数倍率")
                return;
            }
            $.ajax({
                type: "post",
                url: CTX + "/optimizationTool/UpdateRote",
                data: {rote: $("#notifyUrl").val()},
                success: function (data) {
                    if (data == "1") {
                        layer.msg("成功!");
                    }
                    else if (data == "-1") {
                        layer.msg("请去设置正确的两位小数倍率!");
                    }
                    else if (data == "-2") {
                        layer.msg("你没有权限，请联系管理员!");
                    }
                    else {
                        layer.msg("失败");
                    }
                }
            });
        }
    }
</script>
</@base.html>