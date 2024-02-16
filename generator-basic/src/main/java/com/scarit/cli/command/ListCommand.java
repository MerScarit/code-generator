package com.scarit.cli.command;

import cn.hutool.core.io.FileUtil;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.List;

@Command(name = "list", description = "查询文件列表", mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {
    
    @Override
    public void run() {
        String projectPath = System.getProperty("user.dir");
        //项目根路径
        File parentpath = new File(projectPath).getParentFile();
        //输出路径
        String staticInputPath = new File(parentpath, "generator-demo-projects/acm-template").getAbsolutePath();
        List<File> files = FileUtil.loopFiles(parentpath);
        for (File file : files) {
            System.out.println(file);
        }
    }
}
