<#macro checkboxListLine data1,data2,name,class>
	<#list data1 as type>
         <div class="${class}">
		     <label class="checkbox-inline">
		     <input type="checkbox" name="${name}" value="${type}"
		     <#list data2 as jType>
                    <#if jType==type> checked </#if> 
             </#list>
             >${type.label}
             </label>
           </div>
    </#list>               		 
</#macro>