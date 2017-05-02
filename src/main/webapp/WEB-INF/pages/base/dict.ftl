<#macro showOptions dictKey dictType="EMNU" selectValue="" haveBlank="N">
    <@showDictContent dictType=dictType dictKey=dictKey/>
    <#if haveBlank=="Y"><option value="0">--请选择--</option></#if>
    <#list dictList as dict>
        <option value="${dict.value}" <#if dict.value==selectValue>selected</#if>>${dict.label}</option>
    </#list>
</#macro>

<#macro showLabel dictKey dictType="EMNU" selectValue="">
    <@showDictContent dictType=dictType dictKey=dictKey selectValue=selectValue/>
    <#list dictList as dict>
    	<#if dict.value==selectValue>${dict.label}</#if>
    </#list>
</#macro>

<#macro showRadio dictKey dictType="EMNU" name="" selectValue="" required="">
    <@showDictContent dictType=dictType dictKey=dictKey/>
    <div class="col-lg-10">
    	<#list dictList as dict>
	    	<label class="checkbox-inline">
	            <input type="radio" name="${name}" value="${dict.value}" <#if dict.value==selectValue>checked</#if> <#if required=="Y">required</#if> /> ${dict.label}
	        </label>
	    </#list>
    </div>
</#macro>

<#macro showCheckbox dictKey dictType="EMNU" name="" selectValue="" required="">
    <@showDictContent dictType=dictType dictKey=dictKey/>
    <#assign selectValues = selectValue?split(",") />
    <div class="col-lg-10">
    	<#list dictList as dict>
	    	<label class="checkbox-inline">
	            <input type="checkbox" name="${name}" value="${dict.value}" <#if selectValues?seq_contains(dict.value)>checked</#if> <#if required=="Y">required</#if> /> ${dict.label}
	        </label>
	    </#list>
    </div>
</#macro>

<#macro showUser dictKey><@compress single_line=true>
	<#if dictKey!="">
    <@showDictContent dictType="user" dictKey=dictKey/>
    <#list dictList as dict>
    	${dict.label}
    </#list>
    </#if></@compress>
</#macro>

<#macro showPermUserByUserName dictKey><@compress single_line=true>
	<#if dictKey!="">
	<#assign keyCode=dictKey+"__userName">
    <@showDictContent dictType="USER" dictKey=keyCode />
    <#list dictList as dict>
    	${dict.label}
    </#list>
    </#if></@compress>
</#macro>

<#macro showTemplate dictKey><@compress single_line=true>
	<#if dictKey!="">
    <@showDictContent dictType="tmp" dictKey=dictKey/>
    <#list dictList as dict>
    	<#if dict_index!=0>,</#if>${dict.label}
    </#list>
    </#if></@compress>
</#macro>
<#macro showVenue dictKey><@compress single_line=true>
	<#if dictKey!="">
    <@showDictContent dictType="venue" dictKey=dictKey/>
    <#list dictList as dict>
    	<#if dict_index!=0>,</#if>${dict.label}
    </#list>
    </#if></@compress>
</#macro>