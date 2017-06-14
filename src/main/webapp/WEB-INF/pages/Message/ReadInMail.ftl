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
        <div class="col-sm-3">
            <div class="ibox float-e-margins">
                <div class="ibox-content mailbox-content">
                    <div class="file-manager">
                        <a class="btn btn-block btn-primary compose-mail" href="#"
                           onclick="$('.page-content').empty().load('/Message/WriteMail');">写信</a>
                        <div class="space-25"></div>
                        <h5>文件夹</h5>
                        <ul class="folder-list m-b-md" style="padding: 0">
                            <li>
                                <a href="#" onclick="$('.page-content').empty().load('/Message/InBox');">
                                    <i class="fa fa-inbox "></i> 收件箱 <span class="label label-warning pull-right"
                                                                           id="MailNum">0</span>
                                </a>
                            </li>
                            <li>
                                <a href="#" onclick="$('.page-content').empty().load('/Message/SendBox');">
                                    <i class="fa fa-envelope-o"></i> 发件箱<span class="label label-warning pull-right"
                                                                              id="ReMailNum">0</span>
                                </a>
                            </li>
                            <li>
                                <a href="#"> <i class="fa fa-certificate"></i> 重要</a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa fa-file-text-o"></i> 草稿 <span
                                        class="label label-danger pull-right">0</span>
                                </a>
                            </li>
                            <li>
                                <a href="#"> <i class="fa fa-trash-o"></i> 垃圾箱</a>
                            </li>
                        </ul>
                        <h5>分类</h5>
                        <ul class="category-list" style="padding: 0">
                            <li>
                                <a href="#"> <i class="fa fa-circle text-navy"></i> 工作</a>
                            </li>
                            <li>
                                <a href="#"> <i class="fa fa-circle text-danger"></i> 文档</a>
                            </li>
                            <li>
                                <a href="#"> <i class="fa fa-circle text-primary"></i> 社交</a>
                            </li>
                            <li>
                                <a href="#"> <i class="fa fa-circle text-info"></i> 广告</a>
                            </li>
                            <li>
                                <a href="#"> <i class="fa fa-circle text-warning"></i> 客户端</a>
                            </li>
                        </ul>

                        <h5 class="tag-title">标签</h5>
                        <ul class="tag-list" style="padding: 0">

                        </ul>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-9 animated fadeInRight">
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
                    查看邮件
                </h2>
                <div class="mail-tools tooltip-demo m-t-md">
                    <h3>
                        <span class="font-noraml">主题： </span>${inBox.title}
                    </h3>
                    <h4>
                        <span class="font-noraml">类型： </span>
                        <#if inBox.affairstate==1>一般</#if>
                        <#if inBox.affairstate==2>紧急</#if>
                        <#if inBox.affairstate==3>重要</#if>
                    </h4>
                    <h5>
                        <span class="font-noraml">发件人:</span>${sendUserName}
                    </h5>
                </div>
            </div>
            <div class="mail-box">


                <#if inBox.senduserid == loginUser>
                    <div class="mail-body">
                        <div style="float: right;max-width: 80%">
                            <span style="float: right">${inBox.intime?string("yyyy-MM-dd HH:mm:ss")}</span> </br>
                            <span style="float: right">${inBox.content}</span> </br>
                            <div style="clear: both"></div>
                        </div>
                        <div style="clear:both;"></div>
                    </div>
                <#else >
                    <div class="mail-body">
                        <div style="max-width: 80%">
                            <span style="">${inBox.intime?string("yyyy-MM-dd HH:mm:ss")}</span> </br>
                            <span style="">${inBox.content}</span>
                        </div>
                    </div>
                </#if>
                <#list messageReplyList as item>
                    <#if item.sendid == loginUser>
                        <div class="mail-body">
                            <div style="float: right;max-width: 80%">
                                <span style="float: right">${item.replytime?string("yyyy-MM-dd HH:mm:ss")}</span> </br>
                                <span style="float: right">${item.replycontent}</span>
                                <div style="clear: both"></div>
                            </div>
                            <div style="clear:both;"></div>
                        </div>
                    <#else >
                        <div class="mail-body">
                            <div style="max-width: 80%">
                                <span style="">${item.replytime?string("yyyy-MM-dd HH:mm:ss")}</span> </br>
                                <span style="">${item.replycontent}</span>
                            </div>
                        </div>
                    </#if>
                </#list>

                <div class="mail-body" id="Reply" style="height: 200px;display: none">
                            <textarea id="replyText" class="col-sm-10"
                                      style="height:150px; margin-left:120px;resize:none;"
                            ></textarea>
                </div>
                <div class="mail-body text-right tooltip-demo">
                    <#if flag>
                        <a class="btn btn-sm btn-white"
                           onclick="$('#Reply').show();$('#replybtn').hide();$('#replySubmit').show();" id="replybtn"><i
                                class="fa	fa-comments"></i><span>回复</span></a>
                        <a class="btn btn-sm btn-white" onclick="" id="replySubmit" style="display: none"><i
                                class="fa	fa-comments"></i><span>回复</span></a>
                    <#else >
                        <button title="" data-placement="top" data-toggle="tooltip" data-original-title="回复"
                                class="btn btn-sm btn-white" disabled="disabled"><i class="fa fa-trash-o"></i> 回复
                        </button>
                    </#if>
                    <a class="btn btn-sm btn-white"
                       onclick="$('.page-content').empty().load('/Message/InBox');" id=""><i
                            class="fa fa-reply"></i><span>返回</span></a>
                <#--<a class="btn btn-sm btn-white"><i class="fa fa-arrow-right"></i> 下一封</a>-->
                    <button title="" data-placement="top" data-toggle="tooltip" data-original-title="删除邮件"
                            class="btn btn-sm btn-white" id="DeleteMail"><i class="fa fa-trash-o"></i> 删除
                    </button>
                </div>
                <div class="clearfix"></div>


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
       onclick="$('.page-content').empty().load('/Message/ReadInMail?MailId=' + ${inBox.id});"
       style="display: inline-block;font-size: 14px;border-top:1px solid #eee;width: 40px;height: 40px;line-height: 40px;text-align: center;color: #ddd;">
        <i class="glyphicon glyphicon-repeat"></i></a>
</div>


<script type="text/javascript">
    //setInterval('MailNum()', 500);
    function MailNum() {
        $.ajax({
            url: CTX + "/Message/ReMailNum",
            success: function (data) {
                $("#ReMailNum").text(data.message);//未读发件箱
            }
        })
    }
    //setInterval('MailNum()', 500);
    function ReMailNum() {
        $.ajax({
            url: CTX + "/Message/InReMailNum",
            success: function (data) {
                $("#MailNum").text(data.message);//未读收件箱
            }
        })
    }
    $(function () {
        ReMailNum();
        MailNum();
        $("#confirm").click(function () {
            //if (confirm('是否已经处理完毕?')) {
            $.ajax({
                url: '/Message/GoInOperationSingle',
                data: {id: ${inBox.id}, type: 1},
                success: function (data) {
                    if (data.message == 1) {
                        $('.page-content').empty().load('/Message/ReadInMail');
                        //window.location.reload();
                    } else {
                        alert("处理失败!");
                    }
                }
            });
            //}


        });
        $("#DeleteMail").click(function () {
            $.ajax({
                url: CTX + '/Message/GoInOperationSingle',
                data: {id: ${inBox.id}, type: 3},
                success: function (data) {
                    if (data.message == 1) {
                        //alert("删除成功！");
                        $('.page-content').empty().load('/Message/InBox');
                    } else {
                        alert("删除失败");
                    }
                }
            });
        });

        $("#replySubmit").click(function () {
            $('#Reply').hide();
            $('#replySubmit').hide();
            $('#replybtn').show();
            $.ajax({
                url: CTX + '/Message/InMailreplySubmit',
                type: "post",
                data: {id: ${inBox.id}, ReplyContent: $("#replyText").val(), mailType: 0},
                success: function (data) {
                    if (data.message == 1) {
                        $('.page-content').empty().load('/Message/ReadInMail?MailId=' + ${inBox.id});
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
    });
</script>
</@base.html>