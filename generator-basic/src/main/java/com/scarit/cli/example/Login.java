package com.scarit.cli.example;

import freemarker.template.utility.StringUtil;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command()
public class Login implements Callable<Integer> {

    @Option(names = {"-u", "--user"}, description = {"用户名"})
    String user;

    @Option(names = {"-p", "--password"}, description = {"密码"},arity = "0..1",interactive = true)
    String password;

    @Option(names = {"-cp", "--checkPassword"}, description = {"确认密码"}, interactive = true)
    String checkPassword;

    @Override
    public Integer call() throws Exception {
        
        System.out.println("password:" + password);
        System.out.println("checkPassword:" + checkPassword);

        return (password.equals(checkPassword)? 0 : 1 );
    }

    public static void main(String[] args) {
//        new CommandLine(new Login()).execute("-u", "User1", "-p","3333", "-cp");
        new CommandLine(new Login()).execute("--help");
        
    }

  

}
