<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>
<@base.html "公告发布">
<link href="${ctx}/static/css/Message/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/custom.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/Index.css" rel="stylesheet">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-9 animated fadeInRight">
            <div class="mail-box-header">
                <h2>
                    提交反馈
                </h2>
            </div>
            <div class="mail-box">

                <form class="form-horizontal" method="post" id="f1" name="f1" action="/Message/SendMail">
                    <div class="mail-body">
                     <div class="form-group">
                         <label class="col-sm-2 control-label">问题类型：</label>
                         <div class="col-sm-10">
                             <select style="" id="affairState" name="affairState" class="form-control">
                                 <option value="1">优化问题</option>
                                 <option value="2">报价问题</option>
                                 <option value="3">其他问题</option>
                                 <#--<#list bumenlist as bumen>
                                     <option value="${bumen.id}">${bumen.roleName}</option>
                                 </#list>-->

                             </select>
                         </div>


                     </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">主题：</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" value="" id="Title" name="Title">
                            </div>
                        </div>


                    </div>

                    <div class="mail-text h-200">

                        <div class="summernote">
                            <textarea class="col-sm-10" style="height:150px; margin-left:120px;resize:none;"
                                      id="content" name="content"></textarea>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="mail-body text-right tooltip-demo">
                        <a class="btn btn-sm btn-primary" data-toggle="tooltip" data-placement="top" title="Send"
                           id="Send"><i class="fa fa-reply"></i> 发送</a>
                        <a class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top"
                           title="Discard email" href="#" onclick="$('.page-content').empty().load('/Message/SendFeedback');"><i class="fa fa-times"></i> 放弃</a>
                    <#--<a class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top"
                       title="Move to draft folder"><i class="fa fa-pencil"></i> 存为草稿</a>-->
                    </div>
                    <div class="clearfix"></div>
                </form>
            </div>

        </div>
    </div>
</div>


<script>

    function MailNum() {
        $.ajax({
            url: "/Procedure/MailNum",
            success: function (data) {
                $("#MailNum").text(data);
            }
        })
    }
    //setInterval('MailNum()', 500);
    function ReMailNum() {
        $.ajax({
            url: "/Message/SendMailAllNum",
            success: function (data) {
                $("#ReMailNum").text(data.message);
            }
        })
    }
    //setInterval('ReMailNum()', 500);

    $(document).ready(function () {

        $("#Send").click(function () {
            if ($("#affairState option:selected").val() != "-1" && $("#Title").val() != "" && $("#content").val() != "") {
                var Title=$("[name='Title']").val();
                var content=$("[name='content']").val();
                var affairState = $("#affairState option:selected").val();
                $.ajax({
                    type: 'post',
                    url: CTX + '/Message/SubmitFeedback',
                    data: {Title: Title,content: content,affairState:affairState},
                    success: function (data) {
                        if(data.message=="1")
                        {
                            $('.page-content').empty().load('/Message/FeedbackSearch');
                        }
                        else
                        {
                            alert("发送失败");
                        }
                        return;
                    }
                });
            }
            else {
                alert("请将信息填写完整！");
            }
        })

    })


</script>

</@base.html>