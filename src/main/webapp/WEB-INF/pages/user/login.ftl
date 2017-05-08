<#import "/base/base.ftl" as base>
<#import "/base/func.ftl" as func>
<@base.html "用户登录">
<link href="${ctx}/static/css/user/login.css" rel="stylesheet">
<link href="${ctx}/static/css/user/index.css" rel="stylesheet">
<link href="${ctx}/static/css/user/style.css" rel="stylesheet">
<link href="${ctx}/static/css/user/account_common.css" rel="stylesheet">
<link href="${ctx}/static/css/user/zzsc.css" rel="stylesheet">
<div id="header" data-spm="1">
    <div class="header-layout y-row" style="min-width: 1000px;
      max-width: 1200px;
      margin-left: auto;
      margin-right: auto;">
        <h1 class="logo" id="logo">
            <img alt="站长素材" title="" height="40" width="140" src="${ctx}/static/img/upload/${bmsModel.userLogoimgUrl}">
        </h1>
        <h2 class="logo-title">登录</h2>
        <ul class="header-nav">
            <#--<li class="nav-first">
                <a href="#" target="_blank">优搜云首页</a></li>
            <li>
                <a href="#" target="_blank">XX首页</a></li>
            <li>
                <a href="#" target="_blank">帮助与文档</a></li>
            <li>
                <a href="#" target="_blank">XXXX</a></li>-->
        </ul>
    </div>
</div>
<div class="content">
    <div id="slideBox">
        <ul id="show_pic" style="left: 0px;">
            <li><img alt="站长素材" title="" height="300" width="300" src="${ctx}/static/img/upload/${bmsModel.userLogoimgUrl}"></li>
            <li><img alt="站长素材" title="" height="300" width="300" src="${ctx}/static/img/ldhkf.jpg"></li>
        </ul>
        <div id="slideText"></div>
        <ul id="iconBall">
            <li class="active">1</li>
            <li class="">2</li>
        </ul>
        <ul id="textBall">
            <li class="active"><a href="javascript:void(0)"></a></li>
            <li class=""><a href="javascript:void(0)"></a></li>
        </ul>
    </div>
    <script src="${ctx}/static/js/user/zzsc.js"></script>


    <div id="login-module">
        <div id="login-wrap" class=" login-static  nc-outer-box">
            <div style="background:#EBEBEB;height:40px;line-height:40px;text-indent:20px;font-weight:800;">
                <#--优搜云-->会员登录
            </div>
            <form  action="${rc.contextPath}/login" method="post">
                <div id="login-loading" class="loading-mask">
                    <div class="loading-icon"></div>
                    <div class="loading-mask-body"></div>
                </div>
                <div id="login-content" class="form clr" style="width:250px;margin:0 auto;">

                    <dl>
                        <dt class="fm-label">
                        <div class="fm-label-wrap clr">

                            <label for="fm-login-id">登录名 :</label></div>
                        </dt>
                        <dd id="fm-login-id-wrap" class="fm-field">
                            <div class="fm-field-wrap ">
                                <div id="account-check-loading" class="loading-mask">
                                    <div class="loading-icon"></div>
                                    <div class="loading-mask-body"></div>
                                </div>
                                <input id="fm-login-id" class="fm-text" name="userName" tabindex="1" placeholder="6位ID" value="" autocorrect="off" autocapitalize="off"></div>
                        </dd>
                    </dl>
                    <dl>
                        <dt class="fm-label">
                        <div class="fm-label-wrap clr">

                            <label for="fm-login-password">登录密码:</label></div>
                        </dt>
                        <dd id="fm-login-password-wrap" class="fm-field">
                            <div class="fm-field-wrap">
                                <input id="fm-login-password" class="fm-text" name="password" tabindex="2" placeholder="登录密码" autocorrect="off" autocapitalize="off" type="password"></div>
                        </dd>
                    </dl>
                </div>
                <div id="login-submit" style="margin:20px 0 0 20px;">
                    <#if loginFailureMessage??><div style="color:red;">${loginFailureMessage!""}</div></#if>
                    <input id="fm-login-submit" value="登录" class="fm-button1 fm-submit" tabindex="4" name="submit-btn" type="submit" style="width: 236px;margin:0 auto;></div>
                <div id="login-other">
                    <div class="register">
                        <a href="${rc.contextPath}/user/register">免费注册</a></div>
                </div>
        </div>
    </div>
</div>
<div class="module-wrap ">

</div>
</@base.html>
