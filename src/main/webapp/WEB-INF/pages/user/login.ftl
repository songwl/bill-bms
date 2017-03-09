<#import "/base/base.ftl" as base>
<#import "/base/func.ftl" as func>
<@base.html "用户登录">
<link href="${ctx}/static/css/user/login.css" rel="stylesheet">
<form action="${rc.contextPath}/user/dologin" method="post">
<div class="login">
    <div class="message">翊芃网络科技有限公司</div>
    <div id="darkbannerwrap"></div>

    <input name="action" value="login" type="hidden">
    <input name="userName" placeholder="用户名" required="" type="text" id="Text1">
    <hr class="hr15">
    <input name="password" placeholder="密码" required="" type="password" id="Text2">
    <hr class="hr15">
    <div class="state"></div>
    <input value="登录" style="width:100%;" type="submit" id="dengru">

    <div class="zhuce"> <a href="${rc.contextPath}/user/register">立即注册</a></div>
    <div class="clear"></div>
    <hr class="hr20">
</div>
</form>
</@base.html>
