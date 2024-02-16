package com.scarit.maker.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.scarit.maker.generator.file.FileGenerator;
import com.scarit.maker.model.DataModel;
import lombok.Data;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable<Integer> {

    /**
     * 1.在代码开头增加作者@Author 注释（增加代码）
     */
    @Option(names = {"-a", "--author"}, description = "作者", interactive = true, arity = "0..1", echo = true, required = true)
    private String author = "ADI";
    /**
     * 2.修改程序输出的信息提示
     */
    @Option(names = {"-o", "--output"}, description = "结果输出", interactive = true, arity = "0..1",echo = true,required = true)
    private String output = "结果为:";

    /**
     * 3.将循环读取输入改为单独读取（可选代码）
     */
    @Option(names = {"-l", "--loop"}, description = "是否加入循环", interactive = true, arity = "0..1",echo = true,required = true)
    private boolean loop = true;


    @Override
    public Integer call() throws Exception {
        DataModel dataMedel = new DataModel();
        BeanUtil.copyProperties(this, dataMedel);
        System.out.println(dataMedel);
        FileGenerator.doMainGen(dataMedel);
        return 0;
    }
}
