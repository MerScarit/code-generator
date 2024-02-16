package ${basePackage};

import ${basePackage}.cli.CommandExecutor;

/**
 * @author ADI
 * @description: TODO
 * @date 2024-02-09
 */

public class Main {
    public static void main(String[] args) {

        CommandExecutor executor = new CommandExecutor();
        executor.doExecute(args);

    }
}
