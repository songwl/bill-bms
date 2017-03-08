<#global ctx = rc.contextPath />

<#macro headcontent>
    <#assign head_content>
        <#nested/>
    </#assign>
</#macro>
<#macro headjscontent>
    <#assign head_js_content>
        <#nested/>
    </#assign>
</#macro>
<#macro jscontent>
    <#assign js_content>
        <#nested/>
    </#assign>
</#macro>

<#macro html title bodyStyle="">
    <!DOCTYPE html>
    <html lang="zh-cn">
    <head>
	  	<meta charset="utf-8">
	  	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
	  	<meta name="keywords" content="admin, dashboard, bootstrap, template, flat, modern, theme, responsive, fluid, retina, backend, html5, css, css3">
	  	<meta name="description" content="">
	  	<meta name="author" content="ThemeBucket">

	
	  	<title>${title}</title>
	
	  	<!--common-->
        <script src="${ctx}/static/js/public/jquery.js"></script>
        <script type="text/javascript">
            var CTX = "${ctx}";
        </script>
		${head_content!""}
		
	  	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	  	<!--[if lt IE 9]>

	  	<![endif]-->
        
		<!-- common -->

		
        ${head_js_content!""}
    </head>
    <body class="${bodyStyle}">
	    <#nested/>
	    
	    <!-- Placed js at the end of the document so the pages load faster -->

			
	    ${js_content!""}
    </body>
    </html>

</#macro>