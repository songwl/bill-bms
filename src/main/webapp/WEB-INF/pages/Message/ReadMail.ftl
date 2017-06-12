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
                        <a class="btn btn-block btn-primary compose-mail" href="#" onclick="$('.page-content').empty().load(CTX+'/Message/WriteMail');">写信</a>
                        <div class="space-25"></div>
                        <h5>文件夹</h5>
                        <ul class="folder-list m-b-md" style="padding: 0">
                            <li>
                                <a href="/Mail/Index">
                                    <i class="fa fa-inbox "></i> 收件箱 <span class="label label-warning pull-right"
                                                                           id="MailNum">0</span>
                                </a>
                            </li>
                            <li>
                                <a href="#" onclick="$('.page-content').empty().load(CTX+'/Message/SendBox');">
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
                        <span class="font-noraml">主题： </span>${sendBox.title}
                    </h3>
                    <h4>
                        <span class="font-noraml">类型： </span>
                        <#if sendBox.affairstate==1>一般</#if>
                        <#if sendBox.affairstate==2>紧急</#if>
                        <#if sendBox.affairstate==3>重要</#if>
                    </h4>
                    <h5>
                        <span class="pull-right font-noraml">${sendBox.sendtime}</span>
                        <span class="font-noraml">收件人:</span>${sendBox.inuserid}
                    </h5>
                </div>
            </div>
            <div class="mail-box">


                <div class="mail-body">
                ${sendBox.content}
                </div>

                <div class="mail-body text-right tooltip-demo">

                        <#if sendBox.dealtstate==0><a class="btn btn-sm btn-white" id="confirm"><i class="glyphicon glyphicon-check"></i><span>确认处理</span></a></#if>
                        <#if sendBox.dealtstate==1><a class="btn btn-sm btn-white" onclick="$('.page-content').empty().load(CTX+'/Message/SendBox');" id=""><i class="fa fa-reply"></i><span>已经处理</span></a></#if>
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


<script type="text/javascript">
    function MailNum() {
        $.ajax({
            url:CTX+ "/Procedure/MailNum",
            success: function (data) {
                $("#MailNum").text(data);
            }
        })
    }
    //setInterval('MailNum()', 500);
    function ReMailNum() {
        $.ajax({
            url: CTX+"/Message/SendMailAllNum",
            success: function (data) {
                $("#ReMailNum").text(data.message);
            }
        })
    }
    //setInterval('ReMailNum()', 500);

    $(function () {
        MailNum();
        ReMailNum();
        $("#confirm").click(function () {
            //if (confirm('是否已经处理完毕?')) {
                $.ajax({
                    url: CTX+'/Message/GoOperationSingle',
                    data: {id: ${sendBox.id}, type: 1},
                    success: function (data) {
                        if (data.message == 1) {
                            //window.location.reload();
                            $('.page-content').empty().load(CTX+'/Message/SendBox');
                        }else
                        {
                            alert("处理失败!");
                        }
                    }
                });
            //}


        })
        $("#DeleteMail").click(function () {
            $.ajax({
                url: CTX+'/Message/GoOperationSingle',
                data: {id: ${sendBox.id}, type: 3},
                success: function (data) {
                    if (data.message == 1) {
                        //alert("删除成功！");
                        $('.page-content').empty().load(CTX+'/Message/SendBox');
                    }else
                    {
                        alert("删除失败");
                    }
                }
            });
        })


    })
</script>
</@base.html>