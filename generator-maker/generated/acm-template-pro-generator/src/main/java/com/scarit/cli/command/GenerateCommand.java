package com.scarit.cli.command;
import java.util.concurrent.Callable;
import cn.hutool.core.bean.BeanUtil;
import com.scarit.generator.MainGenerator;
import com.scarit.model.DataModel;
import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable<Integer> {
    
    /**
     * 1.在代码开头增加作者@Author 注释（增加代码）
     */
    @Option(names = {"-l", "--loop"}, description ="是否生成循环" , interactive = true, arity = "0..1", echo = true)
    private boolean loop =false;
    
    /**
     * 1.在代码开头增加作者@Author 注释（增加代码）
     */
    @Option(names = {"-a", "--author"}, description ="作者注释" , interactive = true, arity = "0..1", echo = true)
    private String author ="adi";
    
    /**
     * 1.在代码开头增加作者@Author 注释（增加代码）
     */
    @Option(names = {"-o", "--outputText"}, description ="输出信息" , interactive = true, arity = "0..1", echo = true)
    private String outputText ="输出总和 = ";
    @Override
    public Integer call() throws Exception {
        DataModel dataMedel = new DataModel();
        BeanUtil.copyProperties(this, dataMedel);
        System.out.println(dataMedel);
        MainGenerator.doGenerator(dataMedel);
        return 0;
    }
}
