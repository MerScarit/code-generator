package com.scarit.generator;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author ADI
 * @description: 静态文件生成
 * @date 2024-02-09
 */
public class StaticGenerator {
    public static void doStatic(String inputPath,String outputPath) {
        
        copyByDFS(inputPath,outputPath);
    }

    public static void copyByHtool(String inputPath,String outputPath) {
        FileUtil.copy(inputPath, outputPath, true);   
    }

    public static void copyByDFS(String inputPath,String outputPath) {

        File src = new File(inputPath);
        File dest = new File(outputPath);

        try {
            //深度优先来做遍历文件
            listFilesByDfs(src, dest);
        } catch (Exception e) {
            System.out.println("文件生成出错了");
            e.printStackTrace();
        }
    }

    private static void listFilesByDfs(File src, File dest) throws IOException {
        //如果源文件是文件夹，则要在目标路径创建文件夹后，继续遍历源文件
        if (src.isDirectory()) {
            File destNewFile = new File(dest.getAbsolutePath()+ File.separator+src.getName());
            //如果目标路径不存在相应的文件夹，则创建文件夹
            if (!destNewFile.exists()) {
                destNewFile.mkdirs();
            }
            //将源文件转化成list，方便遍历
            File[] files = src.listFiles();
            //若源文件是空文件夹，则直接返回
            if (ArrayUtil.isEmpty(files)) {
                return;
            }
            //深度优先算法遍历所有源文件
            for (File file : files) {
                listFilesByDfs(file, destNewFile);
            }
            //如果源文件不是文件夹，则直接复制
            //这里要注意目标文件路径需要携带源文件名字，否则不会创建对应的文件出来
        } else {
            Path destPath = dest.toPath().resolve(src.getName());
            Files.copy(src.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
