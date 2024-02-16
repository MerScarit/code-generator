package com.scarit.generator;

import com.scarit.model.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
/**
* 核心生成器
*/
public class MainGenerator {

    /**
    * 生成
    *
    * @param model 数据模型
    * @throws TemplateException
    * @throws IOException
    */
    public static void doGenerator(Object model) throws TemplateException, IOException, InterruptedException {
        
        String inputRootPath = ".source/acm-template-pro";
        String outputRootPath = "generated";

        String inputPath;
        String outputPath;
    inputPath = new File(inputRootPath,"src/com/scarit/acm/MainTemplate.java.ftl").getAbsolutePath();
    outputPath = new File(outputRootPath, "src/com/scarit/acm/MainTemplate.java").getAbsolutePath();
        DynamicGenerator.toDynamicGenerate(inputPath, outputPath,model);
    inputPath = new File(inputRootPath,".gitignore").getAbsolutePath();
    outputPath = new File(outputRootPath, ".gitignore").getAbsolutePath();
        StaticGenerator.copyFilesByHutool(inputPath, outputPath);
    inputPath = new File(inputRootPath,"README.md").getAbsolutePath();
    outputPath = new File(outputRootPath, "README.md").getAbsolutePath();
        StaticGenerator.copyFilesByHutool(inputPath, outputPath);
    }
}
