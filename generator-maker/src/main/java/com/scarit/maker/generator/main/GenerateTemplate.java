package com.scarit.maker.generator.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.scarit.maker.generator.JarGenerator;
import com.scarit.maker.generator.ScriptGenerator;
import com.scarit.maker.generator.file.DynamicFileGenterator;
import com.scarit.maker.meta.Meta;
import com.scarit.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public abstract class GenerateTemplate {
    public  void doGenerate() throws TemplateException, IOException, InterruptedException {

        Meta meta = MetaManager.getMetaObject();

        // 0.输出根路径
        String projectPath = System.getProperty("user.dir");
        String outputPath = projectPath + File.separator + "generated" + File.separator + meta.getName();
        //不存在该outputPath目录则创建目录
        if (FileUtil.exist(outputPath)) {
            FileUtil.mkdir(outputPath);
        }
        
        
        // 1.将源代码模板文件复制进项目
        String sourceCopyDestPath = copySource(meta, outputPath);
        
        // 2.生成代码文件
        generateCode(meta, outputPath);

        // 3.构建Jar包，自动在命令行工具执行打包命令
        String jarPath = buildJar(meta,outputPath);

        // 4.封装生成脚本文件
        String shellOutputFilePath = buildScript(outputPath, jarPath);


        // 5.生成精简版本的代码生成器
        buildDist(outputPath,sourceCopyDestPath, jarPath, shellOutputFilePath);
    }

    /**
     * 封装脚本
     *
     * @param outputPath
     * @param jarPath
     * @return
     * @throws IOException
     */
    private String buildScript(String outputPath, String jarPath) {
        String shellOutputFilePath = outputPath + File.separator + "generator";
        ScriptGenerator.doGenerate(shellOutputFilePath, jarPath);
        return shellOutputFilePath;
    }

    /**
     * 生成精简版程序
     * @param outputPath
     * @param sourceCopyDestPath
     * @param jarPath
     * @param shellOutputFilePath
     * @return 产物包路径
     */
    protected String buildDist(String outputPath, String sourceCopyDestPath, String jarPath, String shellOutputFilePath) {
        String distOutputPath = outputPath + "-dist";
        //拷贝jar包
        String targetJarPath = outputPath + File.separator + jarPath;
        String distJarPath = distOutputPath + File.separator + "target";
        FileUtil.mkdir(distJarPath);
        FileUtil.copy(targetJarPath, distJarPath, true);
        //拷贝脚本文件
        FileUtil.copy(shellOutputFilePath, distOutputPath, true);
        FileUtil.copy(shellOutputFilePath+ ".bat", distOutputPath, true);
        //拷贝源模板文件
        FileUtil.copy(sourceCopyDestPath, distOutputPath, true);
        
        return distOutputPath;
    }

    /**
     * 构建 jar 包
     * @param outputPath
     * @return 返回 jar 包的相对路径
     * @throws IOException
     * @throws InterruptedException
     */
    protected String buildJar(Meta meta, String outputPath) throws IOException, InterruptedException {
        JarGenerator.doGenerate(outputPath);
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
        String jarPath = "target/" + jarName;
        return jarPath;
    }


    /**
     * 代码生成
     * @param meta
     * @param outputPath
     * @throws IOException
     * @throws TemplateException
     */
    protected void generateCode(Meta meta, String outputPath) throws IOException, TemplateException {
        //读取resources路径
        ClassPathResource classPathResource = new ClassPathResource("");
        String inputResourcesPath = classPathResource.getAbsolutePath();

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

        //generator.MainGenerator
        inputFilePath = inputResourcesPath + File.separator + "templates/java/generator/MainGenerator.java.ftl";
        outputFilePath = ouputBaseJavaPackagePath + "/generator/MainGenerator.java";
        DynamicFileGenterator.doGenerate(inputFilePath, outputFilePath, meta);

        //cli.command.GenterateCommand
        inputFilePath = inputResourcesPath + File.separator + "templates/java/cli/command/GenerateCommand.java.ftl";
        outputFilePath = ouputBaseJavaPackagePath + "/cli/command/GenerateCommand.java";
        DynamicFileGenterator.doGenerate(inputFilePath, outputFilePath, meta);

        //cli.command.JsonGenterateCommand
        inputFilePath = inputResourcesPath + File.separator + "templates/java/cli/command/JsonGenerateCommand.java.ftl";
        outputFilePath = ouputBaseJavaPackagePath + "/cli/command/JsonGenerateCommand.java";
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

        //README.md
        inputFilePath = inputResourcesPath + File.separator + "templates/README.md.ftl";
        outputFilePath = outputPath + "/README.md";
        DynamicFileGenterator.doGenerate(inputFilePath, outputFilePath, meta);
    }

    /**
     * 复制原始文件
     *
     * @param meta
     * @param outputPath
     * @return
     */
    protected String copySource(Meta meta, String outputPath) {
        String inputSourcePath = meta.getFileConfig().getSourceRootPath();
        String outputSourcePath = outputPath + File.separator + ".source/";
        FileUtil.copy(Paths.get(inputSourcePath), Paths.get(outputSourcePath));
        return outputSourcePath;
    }

    /**
     * 生成产物包压缩文件
     * @param outputPath
     */
    protected String buildZip(String outputPath) {
        String zipPath = outputPath + ".zip";
        ZipUtil.zip(outputPath, zipPath);
        return zipPath;
    }
}
