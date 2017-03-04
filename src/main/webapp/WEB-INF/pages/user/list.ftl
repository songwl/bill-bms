<#import "/base/base.html" as base>
<#import "/base/func.html" as func>

<@base.html "用户列表">
    <a href="${rc.contextPath}/user/new">创建</a>
    <table>
        <thead>
            <tr>
                <td>用户id</td>
                <td>用户名称</td>
                <td>用户QQ</td>
            </tr>
        </thead>
        <tbody>
            <#list userList as user>
                  <tr>
                      <td>${user.id}</td>
                      <td>${user.userName}</td>
                      <td>${user.qq}</td>
                  </tr>
            </#list>
        </tbody>
    </table>

</@base.html>