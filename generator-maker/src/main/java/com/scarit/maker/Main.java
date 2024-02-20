package com.scarit.maker;

import com.scarit.maker.cli.CommandExecutor;

/**
 * @author ADI
 * @description: TODO
 * @date 2024-02-09
 */

public class Main {
    public static void main(String[] args) {
       
        CommandExecutor executor = new CommandExecutor();
        args = new String[]{ "generate","-l","-g"};
        executor.doExecute(args);
   
    }
}
