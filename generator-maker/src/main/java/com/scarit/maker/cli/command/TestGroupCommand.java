package com.scarit.maker.cli.command;

import com.scarit.maker.model.DataModel;
import lombok.Data;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "test", mixinStandardHelpOptions = true)
@Data
public class TestGroupCommand implements Runnable {

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

    static DataModel.MainTemplate mainTemplate = new DataModel.MainTemplate();

    @Override
    public void run()  {
        System.out.println(needGit);
        System.out.println(loop);
        if (true) {
            System.out.println("输出核心模块配置");
            CommandLine commandLine = new CommandLine(MainTemplateCommand.class);
            commandLine.execute("-a", "-o");
            System.out.println(mainTemplate);
        }

    }

    @Command(name = "mainTemplate", description = "核心模板")
    @Data
    public static class MainTemplateCommand implements Runnable {
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
            mainTemplate.author = author;
            mainTemplate.output = output;
        }
    }


}
