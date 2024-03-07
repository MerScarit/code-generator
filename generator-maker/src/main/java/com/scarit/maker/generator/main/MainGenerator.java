package com.scarit.maker.generator.main;

import com.scarit.maker.generator.ZipGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;

public class MainGenerator extends GenerateTemplate{




    //重写生成精简版代码包，这里不生成
//    @Override
//    protected String buildDist(String outputPath, String shellOutputFilePath, String jarPath, String outputSourcePath) {
//        //super.buildDist(outputPath, shellOutputFilePath, jarPath, outputSourcePath);
//        System.out.println("不生成精简代码包");
//        return "";
//    }


    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {

        GenerateTemplate generateTemplate = new ZipGenerator();
        generateTemplate.doGenerate();
    }
   
    
}
