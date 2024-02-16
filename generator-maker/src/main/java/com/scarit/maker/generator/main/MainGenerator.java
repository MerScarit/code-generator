package com.scarit.maker.generator.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.scarit.maker.generator.JarGenerator;
import com.scarit.maker.generator.ScriptGenerator;
import com.scarit.maker.generator.file.DynamicFileGenterator;
import com.scarit.maker.meta.Meta;
import com.scarit.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class MainGenerator {

    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        Meta meta = MetaManager.getMetaObject();
        
        //输出根路径
        String projectPath = System.getProperty("user.dir");
        String outputPath = projectPath + File.separator + "generated" + File.separator + meta.getName();
        //不存在该outputPath目录则创建目录
        if (FileUtil.exist(outputPath)) {
            FileUtil.mkdir(outputPath);
        }
        
        //读取resources路径
        ClassPathResource classPathResource = new ClassPathResource("");
        String inputResourcesPath = classPathResource.getAbsolutePath();
        
        //将源代码模板文件复制进项目
        String inputSourcePath = meta.getFileConfig().getSourceRootPath();
        String outputSourcePath = outputPath + File.separator + ".source/";
        FileUtil.copy(Paths.get(inputSourcePath), Paths.get(outputSourcePath));
        
        //Java包基础路径,basePackage在json中包路径分隔是通过“.”,需要转化为“/”
        String outputBasePackage = meta.getBasePackage();
        String outputBasePackagePath = StrUtil.join("/", StrUtil.split(outputBasePackage,"."));
        String ouputBaseJavaPackagePath = outputPath + File.separator + "src/main/java/" + outputBasePackagePath;

        String inputFilePath;
        String outputFilePath;

        //model.DataModel
        inputFilePath = inputResourcesPath + File.separator + "templates/java/model/DataModel.java.ftl";
        outputFilePath = ouputBaseJavaPackagePath + "/model/DataModel.java";
        DynamicFileGenterator.doGenerate(inputFilePath, outputFilePath, meta);
        
        //cli.command.GenterateCommand
        inputFilePath = inputResourcesPath + File.separator + "templates/java/cli/command/GenerateCommand.java.ftl";
        outputFilePath = ouputBaseJavaPackagePath + "/cli/command/GenerateCommand.java";
        DynamicFileGenterator.doGenerate(inputFilePath, outputFilePath, meta);

        //cli.command.ConfigCommand
        inputFilePath = inputResourcesPath + File.separator + "templates/java/cli/command/ConfigCommand.java.ftl";
        outputFilePath = ouputBaseJavaPackagePath + "/cli/command/ConfigCommand.java";
        DynamicFileGenterator.doGenerate(inputFilePath, outputFilePath, meta);

        //cli.command.ListCommand
        inputFilePath = inputResourcesPath + File.separator + "templates/java/cli/command/ListCommand.java.ftl";
        outputFilePath = ouputBaseJavaPackagePath + "/cli/command/ListCommand.java";
        DynamicFileGenterator.doGenerate(inputFilePath, outputFilePath, meta);

        //cli.CommandExecutor
        inputFilePath = inputResourcesPath + File.separator + "templates/java/cli/CommandExecutor.java.ftl";
        outputFilePath = ouputBaseJavaPackagePath + "/cli/CommandExecutor.java";
        DynamicFileGenterator.doGenerate(inputFilePath, outputFilePath, meta);

        //Main
        inputFilePath = inputResourcesPath + File.separator + "templates/java/Main.java.ftl";
        outputFilePath = ouputBaseJavaPackagePath + "/Main.java";
        DynamicFileGenterator.doGenerate(inputFilePath, outputFilePath, meta);

        //generator.MainGenerator
        inputFilePath = inputResourcesPath + File.separator + "templates/java/generator/MainGenerator.java.ftl";
        outputFilePath = ouputBaseJavaPackagePath + "/generator/MainGenerator.java";
        DynamicFileGenterator.doGenerate(inputFilePath, outputFilePath, meta);

        //generator.DynamicGenerator
        inputFilePath = inputResourcesPath + File.separator + "templates/java/generator/DynamicGenerator.java.ftl";
        outputFilePath = ouputBaseJavaPackagePath + "/generator/DynamicGenerator.java";
        DynamicFileGenterator.doGenerate(inputFilePath, outputFilePath, meta);

        //generator.StaticGenerator
        inputFilePath = inputResourcesPath + File.separator + "templates/java/generator/StaticGenerator.java.ftl";
        outputFilePath = ouputBaseJavaPackagePath + "/generator/StaticGenerator.java";
        DynamicFileGenterator.doGenerate(inputFilePath, outputFilePath, meta);

        //pom
        inputFilePath = inputResourcesPath + File.separator + "templates/pom.xml.ftl";
        outputFilePath = outputPath + "/pom.xml";
        DynamicFileGenterator.doGenerate(inputFilePath, outputFilePath, meta);

        //自动在命令行工具执行打包命令
        JarGenerator.doGenerator(outputPath);

        //封装生成脚本文件
        String shellOutputFilePath = outputPath + File.separator + "generator";
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
        String jarPath = "target/" + jarName;
        ScriptGenerator.doGenerate(shellOutputFilePath, jarPath);

   
        //生成精简版本的代码生成器
        String distOutputPath = outputPath+ "-dist";
        //拷贝jar包
        String targetJarPath = outputPath + File.separator + jarPath;
        String distJarPath = distOutputPath + File.separator + "target";
        FileUtil.mkdir(distJarPath);
        FileUtil.copy(targetJarPath, distJarPath, true);
        //拷贝脚本文件
        FileUtil.copy(shellOutputFilePath, distOutputPath, true);
        FileUtil.copy(shellOutputFilePath + ".bat", distOutputPath, true);
        //拷贝源模板文件
        FileUtil.copy(outputSourcePath, distOutputPath, true);
        
      
    }
    
}
