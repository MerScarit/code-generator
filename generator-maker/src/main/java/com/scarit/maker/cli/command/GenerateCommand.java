package com.scarit.maker.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.scarit.maker.generator.file.FileGenerator;
import com.scarit.maker.generator.main.MainGenerator;
import com.scarit.maker.model.DataModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import lombok.Data;
import lombok.SneakyThrows;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.io.IOException;
import java.util.concurrent.Callable;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.runnable;

@Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Runnable {

//    /**
//     * 1.在代码开头增加作者@Author 注释（增加代码）
//     */
//    @Option(names = {"-a", "--author"}, description = "作者", interactive = true, arity = "0..1", echo = true, required = true)
//    private String author = "ADI";
//    /**
//     * 2.修改程序输出的信息提示
//     */
//    @Option(names = {"-o", "--output"}, description = "结果输出", interactive = true, arity = "0..1",echo = true,required = true)
//    private String output = "结果为:";

    static DataModel.MainTemplate mainTemplate = new DataModel.MainTemplate();

    /**
     * 3.将循环读取输入改为单独读取（可选代码）
     */
    @Option(names = {"-l", "--loop"}, description = "是否加入循环", interactive = true, arity = "0..1",echo = true,required = true)
    private boolean loop = true;

    /**
     * 4.是否生成.gitignore文件
     */
    @Option(names = {"-g", "--needGit"}, description = "是否生成.gitignore文件", interactive = true, arity = "0..1",echo = true,required = true)
    private boolean needGit = true;

    @Override
    public void run()  {
        System.out.println(needGit);
        System.out.println(loop);
        if (true) {
            CommandLine commandLine = new CommandLine(MainTemplateCommand.class);
            commandLine.execute("-a", "-o");
        }
//        DataModel dataModel = new DataModel();
//        dataModel.mainTemplate = mainTemplate;
//        BeanUtil.copyProperties(this, dataModel);
//        MainGenerator.doGenerate(dataModel);

    }

    @Command(name = "mainTemplate", description = "核心模板")
    @Data
    static class MainTemplateCommand implements Runnable {
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

        @Override
        public void run() {
            mainTemplate.setAuthor(author);
            mainTemplate.setOutput(output);
        }
    }


}
