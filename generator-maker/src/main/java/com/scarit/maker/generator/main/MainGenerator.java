package com.scarit.maker.generator.main;


public class MainGenerator extends GenerateTemplate{

    //重写生成精简版代码包，这里不生成
    @Override
    protected String buildDist(String outputPath, String sourceCopyDestPath, String jarPath, String shellOutputFilePath) {
        System.out.println("不要给我输出 dist 啦！");
        return "";
    }
   
    
}
