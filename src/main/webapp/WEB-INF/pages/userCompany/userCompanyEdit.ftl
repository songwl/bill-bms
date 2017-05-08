<#import "/base/base.ftl" as base>
<#import "/base/func.ftl" as func>
<@base.html "自定义桌面">
<link href="https://cdn.bootcss.com/bootstrap-fileinput/4.3.9/css/fileinput.min.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/bootstrap-fileinput/4.3.9/js/fileinput.min.js"></script>
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/public/pace.js"></script>
<script src="${ctx}/static/js/userCompany/userCompanyUpload.js"></script>
<style>
    .fuji{padding-top:60px}
</style>
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>自定义页面</span> > <span>修改页面</span>
    </div>

    <div class="cls">
    </div>
</div>
<div class="fuji">
    <div class="row">
        <div class="col-sm-6 col-sm-offset-3">
            <form class="form-horizontal"  role="form" method="post"  action="/bill/userCompany/uploadFile"  enctype="multipart/form-data" >
                <div class="form-group">
                    <label class="col-sm-3 control-label">绑定域名</label>
                    <div class="col-sm-9">
                        <input type="text" name="website" class="col-sm-10 form-control"   placeholder="域名">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">后台名称</label>
                    <div class="col-sm-9">
                        <input type="text" name="companyname" class="col-sm-10 form-control"   placeholder="后台名称">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">超链接1</label>
                    <div class="col-sm-9">
                        <input type="text" name="hyperlink1" class="col-sm-10 form-control"   placeholder="超链接1">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">超链接2</label>
                    <div class="col-sm-9">
                        <input type="text" name="hyperlink2" class="col-sm-10 form-control"   placeholder="超链接2">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">超链接3</label>
                    <div class="col-sm-9">
                        <input type="text" name="hyperlink3" class="col-sm-10 form-control"   placeholder="超链接3">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">超链接4</label>
                    <div class="col-sm-9">
                        <input type="text" name="hyperlink4" class="col-sm-10 form-control"   placeholder="超链接4">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">LOGO图片</label>
                    <div class="col-sm-9">
                        <input type="file" name="logoImgurl" data-ref="url2" class="col-sm-10 myfile" value=""/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">滚动图1(必填)</label>
                    <div class="col-sm-9">
                        <input type="file" name="img_url1" data-ref="url" class="col-sm-10 myfile" value=""/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">滚动图2(选填)</label>
                    <div class="col-sm-9">
                        <input type="file" name="img_url2" data-ref="url2" class="col-sm-10 myfile" value=""/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">滚动图3(选填)</label>
                    <div class="col-sm-9">
                        <input type="file" name="img_url3" data-ref="url2" class="col-sm-10 myfile" value=""/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">滚动图4(选填)</label>
                    <div class="col-sm-9">
                        <input type="file" name="img_url4" data-ref="url2" class="col-sm-10 myfile" value=""/>
                    </div>
                </div>

                <button type="submit" id="submit" class="btn btn-default col-sm-2 col-sm-offset-4" style="background: #09C;color: #fff;">提交</button>

        </div>
    </div>
</div>

</@base.html>
