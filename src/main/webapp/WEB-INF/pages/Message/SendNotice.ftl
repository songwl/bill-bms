<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>
<@base.html "公告发布">
<link href="${ctx}/static/css/Message/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/custom.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/Index.css" rel="stylesheet">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<div class="wrapper wrapper-content">
    <div class="row">
        <#--<div class="col-sm-3">
            <div class="ibox float-e-margins">
                <div class="ibox-content mailbox-content">
                    <div class="file-manager">
                        <a class="btn btn-block btn-primary compose-mail" href="#" onclick="$('.page-content').empty().load('/Message/WriteMail');">写信</a>
                        <div class="space-25"></div>
                        <h5>文件夹</h5>
                        <ul class="folder-list m-b-md" style="padding: 0">
                            <li>
                                <a href="#">
                                    <i class="fa fa-inbox "></i> 收件箱 <span class="label label-warning pull-right"
                                                                           id="MailNum">0</span>
                                </a>
                            </li>
                            <li>
                                <a href="#" onclick="$('.page-content').empty().load('/Message/SendBox');">
                                    <i class="fa fa-envelope-o"></i>发件箱<span class="label label-warning pull-right"
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
                                <a href="#"><i class="fa fa-trash-o"></i> 垃圾箱</a>
                            </li>
                        </ul>
                        <h5>分类</h5>
                        <ul class="category-list" style="padding: 0">
                            <li>
                                <a href="#"><i class="fa fa-circle text-navy"></i> 工作</a>
                            </li>
                            <li>
                                <a href="#"><i class="fa fa-circle text-danger"></i> 文档</a>
                            </li>
                            <li>
                                <a href="#"> <i class="fa fa-circle text-primary"></i> 社交</a>
                            </li>
                            <li>
                                <a href="#"><i class="fa fa-circle text-info"></i> 广告</a>
                            </li>
                            <li>
                                <a href="#"> <i class="fa fa-circle text-warning"></i> 客户端</a>
                            </li>
                        </ul>

                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
        </div>-->
        <div class="col-sm-9 animated fadeInRight">
            <div class="mail-box-header">
                <h2>
                    发布公告
                </h2>
            </div>
            <div class="mail-box">

                <form class="form-horizontal" method="post" id="f1" name="f1" action="/Message/SendMail">
                    <div class="mail-body">
                       <#-- <div class="form-group">
                            <label class="col-sm-2 control-label">部门：</label>
                            <div class="col-sm-10">
                                <select style="width:100px;" id="department" name="department">
                                    <option value="-1">--请选择--</option>
                                    <#list bumenlist as bumen>
                                        <option value="${bumen.id}">${bumen.roleName}</option>
                                    </#list>

                                </select>
                            &lt;#&ndash;@Html.Raw(ViewBag.Select)&ndash;&gt;
                            </div>


                        </div>-->
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
                           title="Discard email" href="#" onclick="$('.page-content').empty().load(CTX+'/Message/SendFeedback');"><i class="fa fa-times"></i> 放弃</a>
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

    $(document).ready(function () {

        $("#Send").click(function () {
                if ($("#department option:selected").val() != "-1" && $("#Title").val() != "" && $("#content").val() != "") {
                    var Title=$("[name='Title']").val();
                    var content=$("[name='content']").val();
                    var department = $("#department option:selected").val();
                    $.ajax({
                        type: 'post',
                        url: CTX + '/Message/SendNotice',
                        data: {Title: Title,content: content,department:department},
                        success: function (data) {
                            if(data.message=="1")
                            {
                                $('.page-content').empty().load(CTX+'/Message/NoticeSearch');
                            }
                            else if(data.message=="2")
                            {
                                alert("你没有权限");
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