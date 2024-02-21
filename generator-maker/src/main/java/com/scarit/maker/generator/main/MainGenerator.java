package com.scarit.maker.generator.main;


import freemarker.template.TemplateException;

import java.io.IOException;

public class MainGenerator extends GenerateTemplate{




    //重写生成精简版代码包，这里不生成
    @Override
    protected void buildDist(String outputPath, String shellOutputFilePath, String jarPath, String outputSourcePath) {
        //super.buildDist(outputPath, shellOutputFilePath, jarPath, outputSourcePath);
        System.out.println("不生成精简代码包");
    }


    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        MainGenerator mainGenerator = new MainGenerator();
        mainGenerator.doGenerate();
    }
   
    
}
