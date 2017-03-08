<#macro showNav bmsModel>
    <!-- left side start-->
    <div class="left-side sticky-left-side">
        <!--logo and iconic logo start-->
        <div class="logo">
            <a href="${ctx}/index"><img src="${ctx}/images/logo.png" alt="" width="150" height="40"></a>
        </div>
        <div class="logo-icon text-center">
            <a href="${ctx}/index"><img src="${ctx}/images/logo_icon.png" alt="" width="35" height="35"></a>
        </div>
        <!--logo and iconic logo end-->

        <div class="left-side-inner">

            <!-- visible to small devices only -->
            <div class="visible-xs hidden-sm hidden-md hidden-lg">
                <div class="media logged-user">
                    <img alt="" src="${ctx}/adminx/images/photos/user-avatar.png" class="media-object">
                    <div class="media-body">
                        <h4><a href="#">${bmsModel.account.loginUsername}</a></h4>
                        <span>"欢迎回来！"</span>
                    </div>
                </div>

                <h5 class="left-nav-title">用户信息</h5>
                <ul class="nav nav-pills nav-stacked custom-nav">
                  <li><a href="#myModal" data-toggle="modal"><i class="fa fa-cog"></i> <span>修改密码</span></a></li>
                  <li><a href="${ctx}/logout"><i class="fa fa-sign-out"></i> <span>注销</span></a></li>
                </ul>
            </div>

            <!--sidebar nav start-->
            <ul class="nav nav-pills nav-stacked custom-nav">
                <li <#if bmsModel.navId==null || bmsModel.navId==0>class="active"</#if>><a href="${ctx}/index"><i class="fa fa-home"></i> <span>主页</span></a></li>

				<#list bmsModel.bmsNavigationList as nav>
					<#if nav.type=="MENU">
						<@showMenu nav />
					<#else>
						<@showUrl nav />
					</#if>
			    </#list>
			    
                <!-- <li class="menu-list"><a href=""><i class="fa fa-laptop"></i> <span>系统管理</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="blank_page.html"> 用户管理</a></li>
                        <li><a href="boxed_view.html"> 权限管理</a></li>
                        <li><a href="leftmenu_collapsed_view.html"> 日志管理</a></li>
                    </ul>
                </li>
                <li><a href="fontawesome.html"><i class="fa fa-th-list"></i> <span>场所管理</span></a></li>
                
                <li><a href="fontawesome.html"><i class="fa fa-book"></i> <span>资源管理</span></a></li>
                
                <li><a href="fontawesome.html"><i class="fa fa-users"></i> <span>游客管理</span></a></li>
                
                <li><a href="fontawesome.html"><i class="fa fa-tasks"></i> <span>模板管理</span></a></li>
                
                <li><a href="fontawesome.html"><i class="fa fa-file-text"></i> <span>页面管理</span></a></li>
                
                <li><a href="fontawesome.html"><i class="fa fa-cogs"></i> <span>页面审核</span></a></li>
                
                <li><a href="fontawesome.html"><i class="fa fa-bookmark"></i> <span>手册发布</span></a></li>
                
                <li><a href="fontawesome.html"><i class="fa fa-bullhorn"></i> <span>新闻管理</span></a></li>
                
                <li><a href="fontawesome.html"><i class="fa fa-star"></i> <span>促销管理</span></a></li>
                
                <li><a href="login.html"><i class="fa fa-sign-in"></i> <span>退出</span></a></li> -->
            </ul>
            <!--sidebar nav end-->

        </div>
    </div>
    <!-- left side end-->
    
</#macro>

<#macro showMenu menu>
    <li class="menu-list"><a href="#"><i class="fa ${menu.icon?if_exists}"></i> <span>${menu.name}</span></a>
        <ul class="sub-menu-list">
        	<#list menu.children as nav>
	            <@showUrl nav />
	        </#list>
        </ul>
    </li>
</#macro>

<#macro showUrl url>
	<li <#if bmsModel.navId!=null && bmsModel.navId==url.nid>class="active"</#if>><a href="" url="${ctx}${url.url}?navId=${url.nid}"><i class="fa ${url.icon?if_exists}"></i> <span>${url.name}</span></a></a></li>
</#macro>


<#macro showHeader bmsModel>
		<!-- header section start-->
        <div class="header-section">
	        <!--toggle button start-->
	        <a class="toggle-btn"><i class="fa fa-bars"></i></a>
	        <!--toggle button end-->
	
	        <!--search start
	        <form class="searchform" action="index.html" method="post">
	            <input type="text" class="form-control" name="keyword" placeholder="Search here..." />
	        </form>-->
	        <!--search end-->
	
	        <!--notification menu start -->
	        <div class="menu-right">
	            <ul class="notification-menu">
	            		<!--
	                <li>
	                    <a href="#" class="btn btn-default dropdown-toggle info-number" data-toggle="dropdown">
	                        <i class="fa fa-tasks"></i>
	                        <span class="badge">8</span>
	                    </a>
	                    <div class="dropdown-menu dropdown-menu-head pull-right">
	                        <h5 class="title">You have 8 pending task</h5>
	                        <ul class="dropdown-list user-list">
	                            <li class="new">
	                                <a href="#">
	                                    <div class="task-info">
	                                        <div>Database update</div>
	                                    </div>
	                                    <div class="progress progress-striped">
	                                        <div style="width: 40%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-bar-warning">
	                                            <span class="">40%</span>
	                                        </div>
	                                    </div>
	                                </a>
	                            </li>
	                            <li class="new">
	                                <a href="#">
	                                    <div class="task-info">
	                                        <div>Dashboard done</div>
	                                    </div>
	                                    <div class="progress progress-striped">
	                                        <div style="width: 90%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="90" role="progressbar" class="progress-bar progress-bar-success">
	                                            <span class="">90%</span>
	                                        </div>
	                                    </div>
	                                </a>
	                            </li>
	                            <li>
	                                <a href="#">
	                                    <div class="task-info">
	                                        <div>Web Development</div>
	                                    </div>
	                                    <div class="progress progress-striped">
	                                        <div style="width: 66%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="66" role="progressbar" class="progress-bar progress-bar-info">
	                                            <span class="">66% </span>
	                                        </div>
	                                    </div>
	                                </a>
	                            </li>
	                            <li>
	                                <a href="#">
	                                    <div class="task-info">
	                                        <div>Mobile App</div>
	                                    </div>
	                                    <div class="progress progress-striped">
	                                        <div style="width: 33%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="33" role="progressbar" class="progress-bar progress-bar-danger">
	                                            <span class="">33% </span>
	                                        </div>
	                                    </div>
	                                </a>
	                            </li>
	                            <li>
	                                <a href="#">
	                                    <div class="task-info">
	                                        <div>Issues fixed</div>
	                                    </div>
	                                    <div class="progress progress-striped">
	                                        <div style="width: 80%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="80" role="progressbar" class="progress-bar">
	                                            <span class="">80% </span>
	                                        </div>
	                                    </div>
	                                </a>
	                            </li>
	                            <li class="new"><a href="">See All Pending Task</a></li>
	                        </ul>
	                    </div>
	                </li>
	                <li>
	                    <a href="#" class="btn btn-default dropdown-toggle info-number" data-toggle="dropdown">
	                        <i class="fa fa-envelope-o"></i>
	                        <span class="badge">5</span>
	                    </a>
	                    <div class="dropdown-menu dropdown-menu-head pull-right">
	                        <h5 class="title">You have 5 Mails </h5>
	                        <ul class="dropdown-list normal-list">
	                            <li class="new">
	                                <a href="">
	                                    <span class="thumb"><img src="images/photos/user1.png" alt="" /></span>
	                                    <span class="desc">
	                                      <span class="name">John Doe <span class="badge badge-success">new</span></span>
	                                      <span class="msg">Lorem ipsum dolor sit amet...</span>
	                                    </span>
	                                </a>
	                            </li>
	                            <li>
	                                <a href="">
	                                    <span class="thumb"><img src="images/photos/user2.png" alt="" /></span>
	                                    <span class="desc">
	                                      <span class="name">Jonathan Smith</span>
	                                      <span class="msg">Lorem ipsum dolor sit amet...</span>
	                                    </span>
	                                </a>
	                            </li>
	                            <li>
	                                <a href="">
	                                    <span class="thumb"><img src="images/photos/user3.png" alt="" /></span>
	                                    <span class="desc">
	                                      <span class="name">Jane Doe</span>
	                                      <span class="msg">Lorem ipsum dolor sit amet...</span>
	                                    </span>
	                                </a>
	                            </li>
	                            <li>
	                                <a href="">
	                                    <span class="thumb"><img src="images/photos/user4.png" alt="" /></span>
	                                    <span class="desc">
	                                      <span class="name">Mark Henry</span>
	                                      <span class="msg">Lorem ipsum dolor sit amet...</span>
	                                    </span>
	                                </a>
	                            </li>
	                            <li>
	                                <a href="">
	                                    <span class="thumb"><img src="images/photos/user5.png" alt="" /></span>
	                                    <span class="desc">
	                                      <span class="name">Jim Doe</span>
	                                      <span class="msg">Lorem ipsum dolor sit amet...</span>
	                                    </span>
	                                </a>
	                            </li>
	                            <li class="new"><a href="">Read All Mails</a></li>
	                        </ul>
	                    </div>
	                </li>
	                <li>
	                    <a href="#" class="btn btn-default dropdown-toggle info-number" data-toggle="dropdown">
	                        <i class="fa fa-bell-o"></i>
	                        <span class="badge">4</span>
	                    </a>
	                    <div class="dropdown-menu dropdown-menu-head pull-right">
	                        <h5 class="title">Notifications</h5>
	                        <ul class="dropdown-list normal-list">
	                            <li class="new">
	                                <a href="">
	                                    <span class="label label-danger"><i class="fa fa-bolt"></i></span>
	                                    <span class="name">Server #1 overloaded.  </span>
	                                    <em class="small">34 mins</em>
	                                </a>
	                            </li>
	                            <li class="new">
	                                <a href="">
	                                    <span class="label label-danger"><i class="fa fa-bolt"></i></span>
	                                    <span class="name">Server #3 overloaded.  </span>
	                                    <em class="small">1 hrs</em>
	                                </a>
	                            </li>
	                            <li class="new">
	                                <a href="">
	                                    <span class="label label-danger"><i class="fa fa-bolt"></i></span>
	                                    <span class="name">Server #5 overloaded.  </span>
	                                    <em class="small">4 hrs</em>
	                                </a>
	                            </li>
	                            <li class="new">
	                                <a href="">
	                                    <span class="label label-danger"><i class="fa fa-bolt"></i></span>
	                                    <span class="name">Server #31 overloaded.  </span>
	                                    <em class="small">4 hrs</em>
	                                </a>
	                            </li>
	                            <li class="new"><a href="">See All Notifications</a></li>
	                        </ul>
	                    </div>
	                </li>
	                -->
	                <li>
	                    <a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
	                        <img src="${ctx}/adminx/images/photos/user-avatar.png" alt="" />
	                        ${bmsModel.account.loginUsername}
	                        <span class="caret"></span>
	                    </a>
	                    <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
	                       <!--  <li><a href="${ctx}/profile"><i class="fa fa-user"></i>  个人中心</a></li> -->
	                        <li><a href="#myModal" data-toggle="modal"><i class="fa fa-cog"></i>  修改密码</a></li>
	                        <li><a href="${ctx}/logout"><i class="fa fa-sign-out"></i> 注销</a></li>
	                    </ul>
	                </li>
	
	            </ul>
	        </div>
	        <!--notification menu end -->
	
	    </div>
	    <!-- header section end-->
	    
	    <!-- 修改密码 Modal -->
	    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	        <div class="modal-dialog">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                    <h4 class="modal-title">修改密码</h4>
	                </div>
	
	                <div class="modal-body">
	                		<form class="form-horizontal" role="form">
	                        <div class="form-group">
	                            <label for="inputEmail1" class="col-lg-2 col-sm-2 control-label">原始密码</label>
	                            <div class="col-lg-10">
	                                <input type="email" class="form-control" id="inputEmail4" placeholder="Email">
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label for="inputPassword1" class="col-lg-2 col-sm-2 control-label">新密码</label>
	                            <div class="col-lg-10">
	                                <input type="password" class="form-control" id="inputPassword4" placeholder="Password">
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label for="inputPassword1" class="col-lg-2 col-sm-2 control-label">确认密码</label>
	                            <div class="col-lg-10">
	                                <input type="password" class="form-control" id="inputPassword4" placeholder="Password">
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <div class="col-lg-offset-2 col-lg-10">
	                                <button type="submit" class="btn btn-primary">确认</button>
	                            </div>
	                        </div>
	                    </form>
	
	                </div>
	
	            </div>
	        </div>
	    </div>
	    <!-- modal -->
</#macro>