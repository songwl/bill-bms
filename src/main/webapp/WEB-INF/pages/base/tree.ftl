
<#macro tree list idFieldName="id" pidFieldName="pid" nameFieldName="name" childFieldName="children"
	selectable=false multiSelect=false selectName="" selectValues="">
    <#if list?? && list?size gt 0>
    <div class="tree tree-solid-line func-tree">
    	<#list list as listItem>
	    	<@_tree listItem idFieldName pidFieldName nameFieldName childFieldName selectable multiSelect selectName selectValues />
	    </#list>
	</div>
	</#if>
</#macro>

<#macro _tree listItem idFieldName pidFieldName nameFieldName childFieldName selectable multiSelect selectName selectValues>
	<#if listItem[childFieldName]?? && listItem[childFieldName]?size gt 0>
		<@_treeFolder listItem idFieldName pidFieldName nameFieldName childFieldName selectable multiSelect selectName selectValues />
	<#else>
		<@_treeItem listItem idFieldName pidFieldName nameFieldName selectable multiSelect selectName selectValues />
	</#if>
</#macro>

<#macro _treeFolder floder idFieldName pidFieldName nameFieldName childFieldName selectable multiSelect selectName selectValues>
	<div class="tree-folder">
        <div class="tree-folder-header">
            <#if selectable>
        		<#if multiSelect>
        			<input type="checkbox" selectType="folder" value="${floder[idFieldName]}" name="${selectName}" <#if selectValues?seq_contains(floder[idFieldName])>checked='checked'</#if> />
        		</#if>
        	</#if>
            <i class="fa fa-folder"></i>
            <div class="tree-folder-name">
            	${floder[nameFieldName]}
            </div>
        </div>
        <div class="tree-folder-content" style="display:none;">
        	<#list floder[childFieldName] as floderItem>
        		<@_tree floderItem idFieldName pidFieldName nameFieldName childFieldName selectable multiSelect selectName selectValues />
        	</#list>
        </div>
    </div>
</#macro>

<#macro _treeItem item idFieldName pidFieldName nameFieldName selectable multiSelect selectName selectValues>
	<div class="tree-item">
        <i class="tree-dot"></i>
        <div class="tree-item-name">
        	<#if selectable>
        		<#if multiSelect>
        			<input type="checkbox" selectType="item" value="${item[idFieldName]}" name="${selectName}" <#if selectValues?seq_contains(item[idFieldName])>checked='checked'</#if> />
        		<#else>
        			<input type="radio" value="${item[idFieldName]}" name="${selectName}" <#if selectValues==item[idFieldName]>checked='checked'</#if> />
        		</#if>
        	</#if>
        	${item[nameFieldName]}
        </div>
    </div>
</#macro>