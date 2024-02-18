package ${basePackage}.cli.command;
import java.util.concurrent.Callable;
import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.generator.MainGenerator;
import ${basePackage}.model.DataModel;
import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

<#macro generateOption indent modelInfo>
${indent}@Option(names = {<#if modelInfo.abbr??>"-${modelInfo.abbr}"</#if>, "--${modelInfo.fieldName}"},<#if modelInfo.description??>description =${modelInfo.description?c}</#if> , interactive = true, arity = "0..1", echo = true)
${indent}private ${modelInfo.type} ${modelInfo.fieldName}<#if modelInfo.defaultValue??> =${modelInfo.defaultValue?c}</#if>;
</#macro>

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
        <#---todo: -->
    </#if>
    @Option(names = {<#if modelInfo.abbr??>"-${modelInfo.abbr}"</#if>, "--${modelInfo.fieldName}"},<#if modelInfo.description??>description =${modelInfo.description?c}</#if> , interactive = true, arity = "0..1", echo = true)
    private ${modelInfo.type} ${modelInfo.fieldName}<#if modelInfo.defaultValue??> =${modelInfo.defaultValue?c}</#if>;
</#list>
    @Override
    public Integer call() throws Exception {
        DataModel dataMedel = new DataModel();
        BeanUtil.copyProperties(this, dataMedel);
        System.out.println(dataMedel);
        MainGenerator.doGenerator(dataMedel);
        return 0;
    }
}
