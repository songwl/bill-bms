<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>
<#import "/base/" as dict>

<@base.html "写信息">
<link href="${ctx}/static/css/Message/font-awesome.min.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/custom.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/Index.css" rel="stylesheet">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12 animated fadeInRight">
            <div class="mail-box-header">
            <#-- <div class="pull-right tooltip-demo">
                 <a href="mail_compose.html" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top"
                    title="回复"><i class="fa fa-reply"></i> 回复</a>
                 <a href="mail_detail.html#" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top"
                    title="打印邮件"><i class="fa fa-print"></i> </a>
                 <a href="mailbox.html" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top"
                    title="标为垃圾邮件"><i class="fa fa-trash-o"></i> </a>
             </div>-->
                <h2>
                    查看反馈
                </h2>
                <div class="mail-tools tooltip-demo m-t-md">
                    <h3>
                        <span class="font-noraml">主题： </span>${sendBox.title}
                    </h3>

                    <#if sendBox.senduserid != loginUser>
                        <h3>
                            <span class="font-noraml">反馈者： </span>${sendUserName}
                        </h3>
                    </#if>
                </div>
            </div>
            <div class="mail-box">


                <#if sendBox.senduserid == loginUser>
                    <div class="mail-body">
                        <div class="manage_right"><img src="/static/img/laugh.png" class="usertx"><br>我<br><span class="tw_time">${sendBox.sendtime?string("yyyy-MM-dd HH:mm:ss")}</span></div>
                        <div style="float: right;max-width: 80%" class="replyright">
                            <span style="float: right">${sendBox.content}</span>
                            <div style="clear: both"></div>
                        </div>
                        <div style="clear:both;"></div>
                    </div>
                <#else >
                    <div class="mail-body">
                        <div class="user_left">
                            <img src="/static/img/laugh.png" alt=""><br>客服<br><span class="tw_time">${sendBox.sendtime?string("yyyy-MM-dd HH:mm:ss")}</span></div>
                        <div style="max-width: 80%" class="replyleft">
                            <span style="">${sendBox.content}</span>
                        </div>
                    </div>
                </#if>
                <#list messageReplyList as item>
                    <#if item.sendid == loginUser>
                        <div class="mail-body">
                            <div class="manage_right"><img src="/static/img/laugh.png" class="usertx"><br>我<br><span class="tw_time">${item.replytime?string("yyyy-MM-dd HH:mm:ss")}</span></div>
                            <div style="float: right;max-width: 80%" class="replyright">
                                <span style="float: right">${item.replycontent}</span>
                                <div style="clear: both"></div>
                            </div>
                            <div style="clear:both;"></div>
                        </div>
                    <#else >
                        <div class="mail-body">
                            <div class="user_left">
                                <img src="/static/img/laugh.png" alt=""><br>客服<br><span class="tw_time">${item.replytime?string("yyyy-MM-dd HH:mm:ss")}</span></div>
                            <div style="max-width: 80%" class="replyleft">
                                <span style="">${item.replycontent}</span>
                            </div>
                        </div>
                    </#if>
                </#list>
                <div class="mail-body" id="Reply" style="height: 200px;display: none">
                            <textarea id="replyText" class="col-sm-10" style="height:150px; margin-left:120px;resize:none;"></textarea>
                </div>

                <div class="mail-body text-right tooltip-demo">
                    <#if sendBox.dealtstate==2||sendBox.dealtstate==3||sendBox.dealtstate==1>
                        <a class="btn btn-sm btn-white"
                           onclick="$('#Reply').show();$('#replybtn').hide();$('#replySubmit').show();" id="replybtn"><i
                                class="fa	fa-comments"></i><span>回复</span></a>
                        <a class="btn btn-sm btn-white" onclick="" id="replySubmit" style="display: none"><i
                                class="fa	fa-comments"></i><span>回复</span></a>
                        <a class="btn btn-sm btn-white" id="finish"><i
                                class="fa fa-stop-circle-o"></i><span>结束对话</span></a>
                    <#else>
                        <button title="" data-placement="top" data-toggle="tooltip" data-original-title="回复"
                                class="btn btn-sm btn-white" disabled="disabled"><i class="fa	fa-comments"></i> 回复
                        </button>
                        <button title="" data-placement="top" data-toggle="tooltip" data-original-title="回复"
                                class="btn btn-sm btn-white" disabled="disabled"><i class="fa fa-stop-circle-o"></i>
                            结束对话
                        </button>
                    </#if>
                    <a class="btn btn-sm btn-white"<#-- href="javascript:history.go(-1)"-->
                       onclick="$('.page-content').empty().load(CTX+'/Message/FeedbackSearch');" id=""><i
                            class="fa fa-reply"></i><span>返回</span></a>
                </div>
                <div class="clearfix"></div>

            </div>

        </div>
    </div>
</div>
</div>
<div class="fixed-btn"
     style="position: fixed;right: 0.8%;bottom: 5%;width: 40px;border: 1px solid #eee;background-color: white;font-size: 18px;z-index: 1040;">
    <a href="javascript:void(0)" class="go-top" onclick=" $('body,html').animate({scrollTop:0},1000);" title="返回顶部"
       style="display: none;width: 40px;height: 40px;line-height:40px;text-align: center;color: #64854c;"> <i
            class="fa fa-angle-up"></i></a>
    <a href="javascript:void(0)" title="刷新"
       onclick="$('.page-content').empty().load(CTX+'/Message/ReadFeedback?FeedbackId=' + ${sendBox.id});"
       style="display: inline-block;font-size: 14px;border-top:1px solid #eee;width: 40px;height: 40px;line-height: 40px;text-align: center;color: #aaaaaa;">
        <i class="glyphicon glyphicon-repeat"></i></a>
</div>
<style type="text/css">
    .replyleft
    {
        max-width: 80%;
        display: inline-block;
        background: #f8f9fd;
        border: 1px solid #e2e3e4;
        border-radius: 5px;
        padding: 10px;
        line-height: 22px;
        font-size: 14px;
        word-wrap: break-word;
        word-break: break-all;
        color: #444444;
        margin-top: 10px;
    }
    .replyright
    {    max-width: 80%;
        background: #fffef5;
        border: 1px solid #ede6ae;
        border-radius: 5px;
        padding: 10px;
        word-wrap: break-word;
        word-break: break-all;
        margin-top: 10px;
    }
    .user_left {
        background: url(/static/img/iconimg.gif) no-repeat right -1518px;
        float: left;
        margin-right: -1px;
        z-index: 2;
        position: relative;
        display: inline-block;
        text-align: right;
        font-size: 14px;
        padding-right: 13px;
    }
    .manage_right {
         background: url(/static/img/iconimg.gif) no-repeat left -1678px;
         float: right;
         margin-left: -1px;
         z-index: 2;
         position: relative;
         margin-top: 10px;
         display: inline-block;
         text-align: left;
         font-size: 14px;
         padding-left: 13px;
     }
</style>

<script type="text/javascript">
    function MailNum() {
        $.ajax({
            url: CTX + "/Procedure/MailNum",
            success: function (data) {
                $("#MailNum").text(data);
            }
        })
    }
    //setInterval('MailNum()', 500);
    function ReMailNum() {
        $.ajax({
            url: CTX + "/Message/SendMailAllNum",
            success: function (data) {
                $("#ReMailNum").text(data.message);
            }
        })
    }
    //setInterval('ReMailNum()', 500);

    $(function () {
        /*MailNum();
        ReMailNum();*/
        $("#finish").click(function () {
            if (!confirm('是否结束对话?')) {
                return;
            }
            $.ajax({
                url: CTX + '/Message/FinishFeedback',
                data: {id: ${sendBox.id}},
                success: function (data) {
                    if (data.message == 1) {
                        //window.location.reload();
                        $('.page-content').empty().load(CTX + '/Message/ReadFeedback?FeedbackId=${sendBox.id}');
                    } else {
                        alert("处理失败!");
                    }
                }
            });
        });
        $("#confirm").click(function () {
            //if (confirm('是否已经处理完毕?')) {
            $.ajax({
                url: CTX + '/Message/GoOperationSingle',
                data: {id: ${sendBox.id}, type: 1},
                success: function (data) {
                    if (data.message == 1) {
                        //window.location.reload();
                        $('.page-content').empty().load(CTX + '/Message/SendBox');
                    } else {
                        alert("处理失败!");
                    }
                }
            });
        });
        $("#DeleteMail").click(function () {
            $.ajax({
                url: CTX + '/Message/GoOperationSingle',
                data: {id: ${sendBox.id}, type: 3},
                success: function (data) {
                    if (data.message == 1) {
                        //alert("删除成功！");
                        $('.page-content').empty().load(CTX + '/Message/SendBox');
                    } else {
                        alert("删除失败");
                    }
                }
            });
        });
        $("#replySubmit").click(function () {
            if ($("#replyText").val() == "") {
                alert("回复内容不允许为空");
                return;
            }
            $('#Reply').hide();
            $('#replySubmit').hide();
            $('#replybtn').show();
            $.ajax({
                url: CTX + '/Message/replySubmit',
                type: "post",
                data: {id: ${sendBox.id}, ReplyContent: $("#replyText").val(), mailType: 1},
                success: function (data) {
                    if (data.message == 1) {
                        $('.page-content').empty().load('/Message/ReadFeedback?FeedbackId=' + ${sendBox.id});
                    } else {
                        alert("回复失败");
                    }
                }
            });
        });

        //绑定滚动条事件
        $(window).bind("scroll", function () {
            var sTop = $(window).scrollTop();
            var sTop = parseInt(sTop);
            if (sTop >= 130) {
                if (!$(".go-top").is(":visible")) {
                    try {
                        $(".go-top").slideDown();
                    } catch (e) {
                        $(".go-top").show();
                    }
                }
            }
            else {
                if ($(".go-top").is(":visible")) {
                    try {
                        $(".go-top").slideUp();
                    } catch (e) {
                        $(".go-top").hide();
                    }
                }
            }
        });
    })
</script>
</@base.html>