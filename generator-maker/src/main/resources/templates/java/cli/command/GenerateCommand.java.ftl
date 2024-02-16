package ${basePackage}.cli.command;
import java.util.concurrent.Callable;
import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.generator.MainGenerator;
import ${basePackage}.model.DataModel;
import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable<Integer> {
<#list modelConfig.models as modelInfo>
    
    /**
     * 1.在代码开头增加作者@Author 注释（增加代码）
     */
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
