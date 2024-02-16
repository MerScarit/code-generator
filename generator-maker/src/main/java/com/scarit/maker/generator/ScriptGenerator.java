package com.scarit.maker.generator;

import cn.hutool.core.io.FileUtil;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class ScriptGenerator {

    public static void doGenerate(String outputPath,String jarPath) {
        
        // 直接写入脚本
        // linux
        StringBuilder stringBuilder = new StringBuilder();
        // #!/bin/bash
        // java -jar  target/generator-basic-1.0-SNAPSHOT-jar-with-dependencies.jar "$@"
        stringBuilder.append("#!/bin/bash").append("\n");
        stringBuilder.append(String.format("java -jar %s \"$@\"",jarPath));
        FileUtil.writeBytes(stringBuilder.toString().getBytes(StandardCharsets.UTF_8), outputPath);
        //添加可执行权限
        try {
            Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");
            Files.setPosixFilePermissions(Paths.get(outputPath), permissions);
        } catch (Exception e) {
           
        }

        // windows
        // @echo off
        // java -jar  target/generator-basic-1.0-SNAPSHOT-jar-with-dependencies.jar %*
        stringBuilder = new StringBuilder();
        stringBuilder.append("@echo off").append("\n");
        stringBuilder.append(String.format("java -jar %s %%*",jarPath));
        FileUtil.writeBytes(stringBuilder.toString().getBytes(StandardCharsets.UTF_8), outputPath + ".bat");
        
        

    }
    
}
