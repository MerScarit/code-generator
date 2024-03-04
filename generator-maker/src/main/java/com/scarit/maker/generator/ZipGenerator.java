package com.scarit.maker.generator;

import com.scarit.maker.generator.main.GenerateTemplate;


/**
 * 生成代码产物包压缩文件
 */
public class ZipGenerator extends GenerateTemplate {


    @Override
    protected String buildDist(String outputPath, String sourceCopyDestPath, String jarPath, String shellOutputFilePath) {
        String distPath = super.buildDist(outputPath, sourceCopyDestPath, jarPath, shellOutputFilePath);
        return super.buildZip(distPath);
    }
}

