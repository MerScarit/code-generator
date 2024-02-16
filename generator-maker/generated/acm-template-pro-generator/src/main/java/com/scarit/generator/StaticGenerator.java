package com.scarit.generator;


import cn.hutool.core.io.FileUtil;

/**
 * @author ADI
 * @description: 静态文件生成
 * @date 2024-02-09
 */
public class StaticGenerator {
    public static void copyFilesByHutool(String inputPath, String outputPath) {

        //使用hutool遍历文件复制文件
        FileUtil.copy(inputPath, outputPath, false);

    }


}
