package com.scarit.cli.example;


import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "main", mixinStandardHelpOptions = true)
public class CRUD implements Runnable {
    @Override
    public void run() {
        System.out.println("主程序运行");
    }

    @Command(name = "add", description = "添加", mixinStandardHelpOptions = true)
    static class ADD implements Runnable {
        @Override
        public void run() {
            System.out.println("添加数据");
        }
    }

    @Command(name = "delete", description = "删除", mixinStandardHelpOptions = true)
    static class DELETE implements Runnable {
        @Override
        public void run() {
            System.out.println("删除数据");
        }
    }

    @Command(name = "query", description = "查询", mixinStandardHelpOptions = true)
    static class QUERY implements Runnable {
        @Override
        public void run() {
            System.out.println("查询数据");
        }
    }

    public static void main(String[] args) {
        //主程序
//        String[] mainArgs = new String[]{};
        //--help
        String[] mainArgs = new String[]{"--help"};
        
        new CommandLine(new CRUD())
                .addSubcommand(new ADD())
                .addSubcommand(new DELETE())
                .addSubcommand(new QUERY())
                .execute(mainArgs);
        int exitCode = 0;
        System.exit(exitCode);
    }
}
