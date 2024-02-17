package com.scarit.generator;

import com.scarit.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 动静态文件结合生成
 */
public class MainGenerator {
    
    public static void doMainGen(Object model) throws TemplateException, IOException {
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
        StaticGenerator.copyByHtool(staticInputPath,staticOutputPath);
        //生成动态文件
        DynamicGenterator.toDynamicGenterate(dynamicInputPath,dynamicOutputPath,model);
    }

    public static void main(String[] args) throws TemplateException, IOException {
        
        
        
        //动态文件数据模板
        MainTemplateConfig model = new MainTemplateConfig();
        model.setAuthor("阿迪");
        model.setOutput("测试修改字段:");
        model.setLoop(true);
        
        //生成动静结合文件
        doMainGen(model);
        
       
    }
    
   
}
    
    

