package ${basePackage}.cli.command;
import java.util.concurrent.Callable;
import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.generator.MainGenerator;
import ${basePackage}.model.DataModel;
import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

<#-- 生成选项 -->
<#macro generateOption indent modelInfo>
${indent}@Option(names = {<#if modelInfo.abbr??>"-${modelInfo.abbr}"</#if>, "--${modelInfo.fieldName}"},<#if modelInfo.description??>description =${modelInfo.description?c}</#if> , interactive = true, arity = "0..1", echo = true)
${indent}private ${modelInfo.type} ${modelInfo.fieldName}<#if modelInfo.defaultValue??> =${modelInfo.defaultValue?c}</#if>;
</#macro>

<#-- 生成命令调用 -->


@Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable<Integer> {
<#list modelConfig.models as modelInfo>
    <#-- 有分组 -->
    <#if modelInfo.groupKey??>
        /**
        * ${modelInfo.groupName}
        */
        static DataModel.${modelInfo.Type} ${modelInfo.groupKey} = new ${modelInfo.type}();

        <#-- 根据分组生成命令 -->
        @Command(name = "${modelInfo.groupKey}")
        @Data
        public ststic class ${modelInfo.Type}Command implements Runnable {
            <#list modelInfo.models as subModelInfo>
                <@generateOption indent="        " modelInfo=subModelInfo/>
            </#list>

            @Override
            public void run() {
                <#list modelInfo.models as subModelInfo>
                    ${modelInfo.groupKey}.${subModelInfo.fieldName} = ${subModelInfo.fieldName};}
                </#list>
            }
        }
    <#else>
        <@generateOption modelInfo=modelInfo indent="        "/>
    </#if>
</#list>

    <#-- 生成调用方法 --->
    @Override
    public Integer call() throws Exception {
        <#list modelConfig.models as modelInfo>
            <#if modelInfo.groupKey??>
                <#if modelInfo.condition??>
        if(${modelInfo.condition}}){
            <#-- todo:macro生成指令 -->
        }
            </#if>
        </#list>
    }
}
