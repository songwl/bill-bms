<#import "/base/base.ftl" as base>
<#import "/base/func.ftl" as func>

<@base.html "用户注册">
<link href="${ctx}/static/css/user/Register.css" rel="stylesheet">
<script src="${ctx}/static/js/user/verificationNumbers.js"></script>
<script src="${ctx}/static/js/user/Particleground.js"></script>
<script src="${ctx}/static/js/user/AjaxName.js"></script>
<style>
    body {
        height: 100%;
        background: #16a085;
        overflow: hidden;
    }

    canvas {
        z-index: -1;
        position: absolute;
    }
</style>
<script>
    $(document).ready(function() {
        //粒子背景特效
        $('body').particleground({
            dotColor: '#5cbdaa',
            lineColor: '#5cbdaa'
        });
    });

            function refresh() {
                var url = "${ctx}/user/check.jpg?number="+Math.random();
                $("#img").attr("src",url);
            }

</script>
<form action="${rc.contextPath}/user/register" method="post" id="f1">
    <dl class="admin_login">
        <dt>
            <strong>会员注册</strong>
            <em></em>
        </dt>

        <dd class="user_icon">
            <input type="text" name="userName" placeholder="用户名" class="login_txtbx" id="logid"/>
        </dd>
        <span style="font-size:14px;" class="pdlogid"></span>

        <dd class="pwd_icon">
            <input type="password" name="password" placeholder="密码" class="login_txtbx" id="pwd"/>
        </dd>
        <span style="font-size:14px;" class="pdpwd"></span>

        <dd class="pwd_icon">
            <input type="password" name="rpwd" placeholder="确认密码" class="login_txtbx" id="rpwd" />
        </dd>
        <span style="font-size:14px;" class="pdrpwd"></span>
        <div>
        <input type="text" style="width: 100px;" name="code" id="code"/>
        <img id="img" src="${ctx}/user/check.jpg" onclick="refresh()">
        <span style="cursor: pointer" onclick="refresh()">看不清？换一张</span>
        <#if loginFailureMessage??><div style="color:red;">${loginFailureMessage!""}</div></#if>
        </div>
        <dd>
            <input type="button" value="立即注册" class="submit_btn" />
        </dd>
        <dd>
            <p></p>
            <p></p>
        </dd>
    </dl>



</form>
</@base.html>