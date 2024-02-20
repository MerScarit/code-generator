# ${name}

> ${description}
> 
> 作者：${author}
> 基于[代码生成器](https://github.com/MerScarit/code-generator) 制作。

可以通过命令行交互式的方式动态生成项目代码

## 使用说明
执行项目根目录下的脚本文件：

```
generator <命令> <选项参数>
```

示例命令：

```
generator generate <#list modelConfig.models as modelInfo><#if modelInfo.groupKey??><#list modelInfo.models as subModelInfo>-${subModelInfo.fieldName}</#list><#else>${modelInfo.fieldName}</#if></#list>
```

## 参数说明

<#list modelConfig.models as modelInfo>
<#if modelInfo.groupKey??>
<#list modelInfo.models as subModelInfo>
${modelInfo?index+1 + subModelInfo?index})${subModelInfo.fieldName}
<@generateDescription modelInfo=subModelInfo/>

</#list>
<#else>
${modelInfo?index+1})${modelInfo.fieldName}
<@generateDescription modelInfo=modelInfo/>

</#if>
</#list>
<#--        生成参数说明-->
<#macro generateDescription modelInfo>
类型： ${modelInfo.type}
描述： ${modelInfo.description}
默认值： ${modelInfo.defaultValue?c}
<#if modelInfo.abbr??>缩写：-${modelInfo.abbr?c}</#if>

</#macro>