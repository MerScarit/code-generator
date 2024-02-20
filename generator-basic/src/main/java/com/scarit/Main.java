package com.scarit;

import com.scarit.cli.CommandExecutor;
import com.scarit.generator.DynamicGenterator;
import com.scarit.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author ADI
 * @description: TODO
 * @date 2024-02-09
 */

public class Main { 
//    public static void main(String[] args) throws TemplateException, IOException {
//
//        String projectPath = System.getProperty("user.dir");
//        String inputPath = projectPath + File.separator + "generator-basic"
//                + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";
//
//        String outputPath = System.getProperty("user.dir") + File.separator+ "MainTemplate.java";
//        System.out.println(outputPath);
//        
//
//        MainTemplateConfig mainTemplate = new MainTemplateConfig();
//        mainTemplate.setAuthor("阿迪");
//        mainTemplate.setOutput("测试修改字段:");
//        mainTemplate.setLoop(true);
//        
//        DynamicGenterator.toDynamicGenterate(inputPath, outputPath, mainTemplate);
//    }

    public static void main(String[] args) {
        //args = new String[]{"generate", "-l", "-a", "-o"};
       // args = new String[]{"config"};
       // args = new String[]{"list"};
//        Properties properties = System.getProperties();
//        properties.forEach((key,value)->{
//            System.out.println("jvm---key:" + key + ";" + "jvm---value:" + value);
//        });
        CommandExecutor executor = new CommandExecutor();
        args = new String[]{"generate", "-l", "-a", "-o"};
        executor.doExecute(args);
   
    }
}
