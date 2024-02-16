package com.scarit.maker.generator;

import java.io.*;

public class JarGenerator {

    public static void doGenerate(String projectDir) throws IOException, InterruptedException {
        
        //执行打包之前先清理之前的包
        //不同操作系统语句会有所不同
        String winMavenCommand = "mvn.cmd clean package -DskipTests=true";
        String otherMavenCommand = "mvn clean package -DskipTests=true";
        String mvnCommand = winMavenCommand;

        ProcessBuilder processBuilder = new ProcessBuilder(mvnCommand.split(" "));
        processBuilder.directory(new File(projectDir));

        Process process = processBuilder.start();

        //读取命令的输出
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        
        //等待命令执行完成
        int exitCode = process.waitFor();
        System.out.println("退出码为：" + exitCode);
    }

 
}
