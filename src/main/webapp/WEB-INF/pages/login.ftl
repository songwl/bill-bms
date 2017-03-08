<#import "/base/base.ftl" as base>
<#import "/base/func.ftl" as func>

<@base.html "登录-酒店及会议室信息发布平台" "login-body">
	<div class="container">
	    <form class="form-signin" action="${ctx}/login" method="post">
	        <div class="form-signin-heading text-center">
	            <h1 class="sign-title">平台登录</h1>
	            <img src="${ctx}/images/login_logo.png" alt=""/>
	        </div>
	        <div class="login-wrap">
	            <input type="text" class="form-control" placeholder="用户名" name="loginName" autofocus>
	            <input type="password" class="form-control" placeholder="密码" name="password">
	
	            <button class="btn btn-lg btn-login btn-block" type="submit">
	                <i class="fa fa-check"></i>
	            </button>
				<#if loginFailureMessage!=null>
				<div class="alert alert-danger">${loginFailureMessage}</div>
				</#if>
	            <!-- <div class="registration">
	                Not a member yet?
	                <a class="" href="registration.html">
	                    Signup
	                </a>
	            </div> -->
	            <!-- <label class="checkbox">
	                <input type="checkbox" value="remember-me"> 记住我
	                <span class="pull-right">
	                    <a data-toggle="modal" href="#myModal"> 忘记密码?</a>
	
	                </span>
	            </label> -->
	
	        </div>
	
	        <!-- Modal -->
	        <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
	            <div class="modal-dialog">
	                <div class="modal-content">
	                    <div class="modal-header">
	                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                        <h4 class="modal-title">Forgot Password ?</h4>
	                    </div>
	                    <div class="modal-body">
	                        <p>Enter your e-mail address below to reset your password.</p>
	                        <input type="text" name="email" placeholder="Email" autocomplete="off" class="form-control placeholder-no-fix">
	
	                    </div>
	                    <div class="modal-footer">
	                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
	                        <button class="btn btn-primary" type="button">Submit</button>
	                    </div>
	                </div>
	            </div>
	        </div>
	        <!-- modal -->
	
	    </form>
	
	</div>
</@base.html>

