<#macro page pageInfo="" paramPage="page" ajax="">
<#if pageInfo!0 gt 0>
	<div class="panel-body">
		<form action="${pageInfo.url}" page <#if ajax!="">ajax="${ajax}"</#if>>
		<div class="span6">
			<div class="dataTables_info" id="hidden-table-info_info">第${pageInfo.page}页/共${pageInfo.totalCount}条</div>
		</div>
		<div class="span6">
			<div class="dataTables_paginate paging_bootstrap pagination">
				<#if pageInfo.totalPage lt 11>
					<#assign begin=1/>
					<#assign end=pageInfo.totalPage/>
					<#if end lt 1><#assign end=1/></#if>
					<#elseif pageInfo.totalPage-pageInfo.page lt 5>
						<#assign end = pageInfo.totalPage/>
						<#assign begin = pageInfo.totalPage-9/>
						<#elseif pageInfo.page lt 5>
							<#assign begin = 1/>
							<#assign end = 10/>
							<#else>
								<#assign begin=pageInfo.page-4/>
								<#assign end = pageInfo.page+5/>
				</#if>
				<ul>
					<#if pageInfo.hasPrev()>
					<li class="prev"><a href="javascript:void(0);" title="Prev" page="${pageInfo.page-1}">&laquo; 上一页</a></li>
					</#if>
	
					<#list begin..end as pp>
					<li <#if pp==pageInfo.page>class ='active'</#if>><a href="javascript:void(0);" <#if pp==pageInfo.page>class ='actived'<#else> page="${pp}"</#if>>${pp}</a></li>
					</#list>
	
					<#if pageInfo.hasNext()>
					<li class="next"><a href="javascript:void(0);" title="Next" page="${pageInfo.page+1}">&#187; 下一页</a></li>
					</#if>
				</ul>
			</div>
		</div>
		</form>
	</div>


	<!-- <div class="row" style="margin:10px 0px;">
	<form action="${pageInfo.url}" page>
       	<div class="col-md-3 col-sm-12">
       		<div style="padding:5px 0px;">第${pageInfo.page}页/共${pageInfo.totalCount}条</div>
       	</div>
       	<div class="col-md-9 col-sm-12">
       		<div class="text-right">
			<#if pageInfo.totalPage lt 11>
				<#assign begin=1/>
				<#assign end=pageInfo.totalPage/>
				<#if end lt 1><#assign end=1/></#if>
				<#elseif pageInfo.totalPage-pageInfo.page lt 5>
					<#assign end = pageInfo.totalPage/>
					<#assign begin = pageInfo.totalPage-9/>
					<#elseif pageInfo.page lt 5>
						<#assign begin = 1/>
						<#assign end = 10/>
						<#else>
							<#assign begin=pageInfo.page-4/>
							<#assign end = pageInfo.page+5/>
			</#if>
			<ul class="pagination" style="visibility: visible;margin:0px;float:right;">
				<#if pageInfo.hasPrev()>
				<li class="prev"><a href="#" title="Prev" page="${pageInfo.page-1}">&laquo;</a></li>
				</#if>

				<#list begin..end as pp>
				<li <#if pp==pageInfo.page>class ='active'</#if>><a href="javascript:void(0);" <#if pp==pageInfo.page>class ='actived'<#else> page="${pp}"</#if>>${pp}</a></li>
				</#list>

				<#if pageInfo.hasNext()>
				<li class="next"><a href="#" title="Next" page="${pageInfo.page+1}">&#187;</a></li>
				</#if>
			</ul>
			<div class="input-group" style="width: 120px;float:right; margin-right: 10px;">
		    	<div class="input-group-addon" style="padding:6px 8px">跳至</div>
		      	<input type="text" class="form-control pageInput" style="padding:2px 4px"/>
		      	<a class="input-group-addon btn btn-default pageBtn" page>&#187;</a>
		    </div>
			</div>
       	</div>
	</form>
   	</div> 
   	
	<script type="text/javascript">
		$(function(){
			$("form[page] a[page]").each(function(){
				if($(this).data("bind")==undefined){
					$(this).click(function(){
						if($(this).hasClass("pageBtn")){
							var $form = $(this).parents("form[page]");
							var page = $form.find("input.pageInput").val();
							if(page==""){
								page=1;
							}
							$(this).attr("page",page);
							toPage($(this));
						}else{
							toPage($(this));
						}
						
					});
					$(this).data("bind",true)
				}
				
			})
			
			function toPage($this){
				var $form = $this.parents("form");
	
				var page = $this.attr("page");
				var url = $form.attr("action");
				url+=page;
				<#if ajax!0 gt 0>
				$("${ajax}").load(url);
				<#else>
				window.location.href=url;
				</#if>
			}
		})
	</script>-->
</#if>
</#macro>