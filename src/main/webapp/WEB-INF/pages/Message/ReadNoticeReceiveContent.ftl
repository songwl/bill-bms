<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>
<#import "/base/" as dict>

<@base.html "写信息">
<link href="${ctx}/static/css/Message/font-awesome.min.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/custom.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/Index.css" rel="stylesheet">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>公告系统</span> > <span>公告内容</span>
    </div>

    <div class="cls">
    </div>
</div>
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
                    查看公告
                </h2>
                <div class="mail-tools tooltip-demo m-t-md">
                    <h3>
                        <span class="font-noraml">主题： </span>${noticepublish.title}
                    </h3>
                </div>
            </div>
            <div class="mail-box">


                <div class="mail-body">
                ${noticepublish.content}
                </div>

                <div class="mail-body text-right tooltip-demo">

                    <a class="btn btn-sm btn-white"<#-- href="javascript:history.go(-1)"--> onclick="$('.page-content').empty().load(CTX+'/Message/NoticeReceiveSearch');" id=""><i class="fa fa-reply"></i><span>返回</span></a>

                </div>
                <div class="clearfix"></div>


            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    function MailNum() {
        $.ajax({
            url: CTX+"/Procedure/MailNum",
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
        /*MailNum();
        ReMailNum();*/
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