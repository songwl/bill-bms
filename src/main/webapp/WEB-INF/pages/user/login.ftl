<#import "/base/base.ftl" as base>
<#import "/base/func.ftl" as func>
<@base.html "用户登录">
<link href="${ctx}/static/css/user/login.css" rel="stylesheet">
<link href="${ctx}/static/css/user/index.css" rel="stylesheet">
<link href="${ctx}/static/css/user/style.css" rel="stylesheet">
<link href="${ctx}/static/css/user/account_common.css" rel="stylesheet">
<link href="${ctx}/static/css/user/zzsc.css" rel="stylesheet">
<link href="${ctx}/static/css/user/loginFoot.css" rel="stylesheet">
<link href="${ctx}/static/css/user/web_style.css" rel="stylesheet">
<link href="${ctx}/static/css/user/login-foot.css" type="text/css" rel="stylesheet">
<script src="${ctx}/static/js/user/swiper.jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/user/particles.js"></script>
<script type="text/javascript" src="${ctx}/static/js/user/tu-map.js"></script>
<div id="header" data-spm="1">
    <div class="header-layout y-row" style="min-width: 1000px;
      max-width: 1200px;
      margin-left: auto;
      margin-right: auto;">
        <h1 class="logo" id="logo">
            <#if bmsModel.userLogoimgUrl??>
                <img   title="" height="40" width="140" src="${ctx}/static/img/upload/${bmsModel.userLogoimgUrl}">
            </#if>

        </h1>
        <h2 class="logo-title">登录</h2>
        <ul class="header-nav">
            <#if userHyperlinkList??>
                <#list userHyperlinkList as item>
                    <li class="nav-first">
                        <a href="${item.hyperlink}" target="_blank">${item.title}</a></li>

                </#list>
            </#if>
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
<div class="content" style="width:70%;margin:0 auto;margin-top:80px;">
    <div id="slideBox">
        <ul id="show_pic" style="left: 0px;">

            <#if userImgurlList??>
                <#list userImgurlList as item>
                    <li><img   title="" height="300" width="400" src="${ctx}/static/img/upload/${item.imgUrl}"></li>
                </#list>
            </#if>
            <#if bmsModel.userImgurlList==null>
                <li><img   title="" height="300" width="400" src="${ctx}/static/img/gundong1.jpg"></li>
                <li><img   title="" height="300" width="400" src="${ctx}/static/img/gundong2.jpg"></li>
                <li><img   title="" height="300" width="400" src="${ctx}/static/img/gundong3.jpg"></li>

            </#if>

        </ul>
        <div id="slideText"></div>
        <ul id="iconBall">
            <li class="active">1</li>
            <li class="">2</li>
            <li class="">3</li>
       <#-- &lt;#&ndash; <li class="">4</li>&ndash;&gt;-->
        </ul>
        <ul id="textBall">
            <li class="active"><a href="javascript:void(0)"></a></li>
            <li class=""><a href="javascript:void(0)"></a></li>
            <li class=""><a href="javascript:void(0)"></a></li>
        </ul>
    </div>


    <script src="${ctx}/static/js/user/zzsc.js"></script>


    <div id="login-module">
        <div id="login-wrap" class=" login-static  nc-outer-box">
            <div style="background:#EBEBEB;height:40px;line-height:40px;text-indent:20px;font-weight:800;">
            ${bmsModel.userCompanyName}
                会员登录
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

                            <label for="fm-login-id">用户名 :</label></div>
                        </dt>
                        <dd id="fm-login-id-wrap" class="fm-field">
                            <div class="fm-field-wrap ">
                                <div id="account-check-loading" class="loading-mask">
                                    <div class="loading-icon"></div>
                                    <div class="loading-mask-body"></div>
                                </div>
                                <input id="fm-login-id" class="fm-text" name="userName"
                                       tabindex="1" placeholder="登录名" value="" autocorrect="off" autocapitalize="off" style="height: 35px;"></div>
                        </dd>
                    </dl>
                    <dl>
                        <dt class="fm-label">
                        <div class="fm-label-wrap clr">
                            <label for="fm-login-password">密码:</label></div>
                        </dt>
                        <dd id="fm-login-password-wrap" class="fm-field">
                            <div class="fm-field-wrap">
                                <input id="fm-login-password" class="fm-text" name="password" tabindex="2" placeholder="登录密码"
                                       autocorrect="off" autocapitalize="off" type="password" style="height: 35px;"></div>
                        </dd>

                    </dl>
                    <dl style="margin-top:10px;display: inline-block;">
                        <input type="text" style="width: 50px;height: 30px; " name="code" id="code"/>
                        <img id="img" src="${ctx}/user/check.jpg" onclick="refresh()">
                        <span style="cursor: pointer" onclick="refresh()">看不清？换一张</span>
                    </dl>
                </div>
                <div id="login-submit" style="margin:0 auto; width: 250px; margin-top :25px;">
                    <#if loginFailureMessage??><div style="color:red;">${loginFailureMessage!""}</div></#if>
                    <input id="fm-login-submit" value="登录" class="fm-button1 fm-submit" tabindex="4" name="submit-btn" type="submit" style="width: 236px;margin:0 auto;></div>
                <div id="login-other">
                    <div class="register">
               <#-- <a href="${rc.contextPath}/user/register">立即注册</a>-->
                    </div>
                <#--<div class="test">
                     接口测试</div>-->
                </div>
            </form>
        </div>
    </div>
</div>


<div class="pro-jionus page-bg-primary">
    <div id="jionbg"></div>
    <div class="jion-text ">
        <div class="container text-center  page-content-md">
            <h3>${userFootMessage.footfont1}</h3>
            <h3>${userFootMessage.footfont2}</h3>
        </div>
    </div>
</div>

<div class="footer-wrap">
    <div class="footer-power text-center page-content-md">
        <div class="container">${userFootMessage.copyrightinfo1}<p>${userFootMessage.copyrightinfo2} <br>
             </p></div>
    </div>
</div>

<script type="text/javascript">
    function refresh() {
        var url = "${ctx}/user/check.jpg?number="+Math.random();
        $("#img").attr("src",url);
    }

</script>
</@base.html>
