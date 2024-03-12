package com.scarit.maker.generator.file;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 动静态文件结合生成
 */
public class FileGenerator {
    
    public static void doGenerate(Object model) throws TemplateException, IOException {
        //配置路径
        String projectPath = System.getProperty("user.dir");
        String parentpath = new File(projectPath).getParent();
        //静态文件生成路径
        String staticInputPath = new File(parentpath, "generator-demo-projects/acm-template").getAbsolutePath();
        String staticOutputPath = projectPath ;

        //动态文件生成路径
        String dynamicInputPath = projectPath + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";
        String dynamicOutputPath = projectPath + File.separator + "acm-template/src/com/scarit/acm/MainTemplate.java ";
        //生成静态文件
        StaticFileGenerator.copyFilesByHutool(staticInputPath,staticOutputPath);
        //生成动态文件
        DynamicFileGenterator.doGenerate(dynamicInputPath,dynamicOutputPath,model);
    }


}
    
    

