package ${basePackage}.cli;

import ${basePackage}.cli.command.JsonGenerateCommand;

import ${basePackage}.cli.command.ConfigCommand;
import ${basePackage}.cli.command.GenerateCommand;
import ${basePackage}.cli.command.ListCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
* 命令执行器
*/
@Command(name = "generator", mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {

    private final CommandLine commandLine;

    {
        commandLine = new CommandLine(this).addSubcommand(new ConfigCommand())
                .addSubcommand(new ListCommand())
                .addSubcommand(new GenerateCommand())
                .addSubcommand(new JsonGenerateCommand());
    }

    @Override
    public void run() {
        System.out.println("输入“--help”获取指令提示");
    }

    public Integer doExecute(String[] args) {
        return commandLine.execute(args);
    }
}
