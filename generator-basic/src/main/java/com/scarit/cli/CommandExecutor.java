package com.scarit.cli;

import com.scarit.cli.command.ConfigCommand;
import com.scarit.cli.command.GenerateCommand;
import com.scarit.cli.command.ListCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "generator", mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {

    private final CommandLine commandLine;

    {
        commandLine = new CommandLine(this).addSubcommand(new ConfigCommand())
                .addSubcommand(new ListCommand())
                .addSubcommand(new GenerateCommand());
    }
    
    @Override
    public void run() {
        System.out.println("输入“--help”获取指令提示");
    }

    public Integer doExecute(String[] args) {
        return commandLine.execute(args);
    }
}
