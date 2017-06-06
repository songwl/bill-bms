<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "写信息">
<link href="${ctx}/static/css/Message/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/custom.css" rel="stylesheet">
<link href="${ctx}/static/css/Message/Index.css" rel="stylesheet">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-3">
            <div class="ibox float-e-margins">
                <div class="ibox-content mailbox-content">
                    <div class="file-manager">
                        <a class="btn btn-block btn-primary compose-mail" href="#">写信</a>
                        <div class="space-25"></div>
                        <h5>文件夹</h5>
                        <ul class="folder-list m-b-md" style="padding: 0">
                            <li>
                                <a href="#">
                                    <i class="fa fa-inbox "></i> 收件箱 <span class="label label-warning pull-right" id="MailNum">0</span>
                                </a>
                            </li>
                            <li>
                                <a href="/Mail/SendBox">
                                    <i class="fa fa-envelope-o"></i>发件箱<span class="label label-warning pull-right" id="ReMailNum">0</span>
                                </a>
                            </li>
                            <li>
                                <a href="#"> <i class="fa fa-certificate"></i> 重要</a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa fa-file-text-o"></i> 草稿 <span class="label label-danger pull-right">0</span>
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
        </div>
        <div class="col-sm-9 animated fadeInRight">
            <div class="mail-box-header">
                <div class="pull-right tooltip-demo">
                    <a href="#" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="存为草稿"><i class="fa fa-pencil"></i> 存为草稿</a>
                    <a href="#" class="btn btn-danger btn-sm" data-toggle="tooltip" data-placement="top" title="放弃"><i class="fa fa-times"></i> 放弃</a>
                </div>
                <h2>
                    写信
                </h2>
            </div>
            <div class="mail-box">

                <form class="form-horizontal" method="post" id="f1" name="f1" >
                    <div class="mail-body">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">部门：</label>

                            <div class="col-sm-10">
                                <select style="width:100px;" id="department" name="department">
                                    <option>--请选择--</option>
                                    @foreach (var item in ViewBag.Department)
                                    {
                                    <option>@item.DepartmentName</option>
                                    }

                                </select>
                                &nbsp; &nbsp; &nbsp;
                                <label class=" control-label">收件人：</label>
                                <select style="width:100px;" id="User" name="User">
                                    <option>--请选择--</option>
                                </select>
                                &nbsp; &nbsp; &nbsp;
                                <label class=" control-label">事务状态：</label>
                                <select style="width:100px;" id="affairState" name="affairState">
                                    <option>--请选择--</option>
                                    <option>一般</option>
                                    <option>紧急</option>
                                    <option>重要</option>
                                </select>
                                &nbsp; &nbsp; &nbsp;
                                @Html.Raw(ViewBag.Select)
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
                            <textarea class="col-sm-10" style="height:300px; margin-left:120px;resize:none;" id="content" name="content"></textarea>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="mail-body text-right tooltip-demo">
                        <a class="btn btn-sm btn-primary" data-toggle="tooltip" data-placement="top" title="Send" id="Send"><i class="fa fa-reply"></i> 发送</a>
                        <a class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="Discard email"><i class="fa fa-times"></i> 放弃</a>
                        <a class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="Move to draft folder"><i class="fa fa-pencil"></i> 存为草稿</a>
                    </div>
                    <div class="clearfix"></div>
                </form>
            </div>

        </div>
    </div>
</div>



<script>

    function MailNum()
    {
        $.ajax({
            url: "/Procedure/MailNum",
            success: function (data) {
                $("#MailNum").text(data);
            }
        })
    }
    setInterval('MailNum()', 500);
    function ReMailNum() {
        $.ajax({
            url: "/Mail/ReMailNum",
            success: function (data) {
                $("#ReMailNum").text(data);
            }
        })
    }
    setInterval('ReMailNum()', 500);

    $(document).ready(function () {

        $("#department").change(function () {
            $("#User").empty();
            if ($("#department option:selected").val() != "--请选择--")
            {
                var department = $("#department option:selected").val();
                $.ajax({
                    type: 'get',
                    url: '/Mail/getUser',
                    data: { deparment: department },
                    success: function (data)
                    {
                        str = "<option>--请选择--</option>";
                        for (var i = 0; i < data.user.length; i++)
                        {

                            str += "<option>" + data.user[i] + "</option>";
                        }

                        $("#User").append(str);
                    }


                })
            }

        })
        $("#Send").click(function () {
            if ($("#AllUser").is(":checked")) {
                if ($("#affairState option:selected").val() == "--请选择--")
                {
                    alert("请选择事务状态");
                }
                if ($("#Title").val() == "" || $("#content").val() == "") {

                    alert("请将信息填写完整！");
                }
                if ($("#Title").val() != "" && $("#content").val() != "" && $("#affairState option:selected").val() != "--请选择--")
                {

                    $("#f1").submit();
                }
            }
            else {

                if ($("#department option:selected").val() != "--请选择--" && $("#User option:selected").val() != "--请选择--" && $("#affairState option:selected").val() != "--请选择--" && $("#Title").val() != "" && $("#content").val() != "") {
                    $("#f1").submit();
                }
                else {
                    alert("请将信息填写完整！");
                }

            }


        })

    })


</script>
