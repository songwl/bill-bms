<#import "/base/base.html" as base>
<#import "/base/func.html" as func>

<@base.html "用户编辑">
    <form action="${rc.contextPath}/user/save" method="post">
    用户名:<input type="text" name="userName" value="" /> <br/>

    密码:<input type="password" name="password" value="" /> <br/>

    QQ:<input type="text" name="qq" value="" /> <br/>
    <button type="submit">提交</button>
    </form>

</@base.html>