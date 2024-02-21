package com.scarit.maker.template;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.scarit.maker.meta.Meta;
import com.scarit.maker.meta.enums.FileGenerateTypeEnum;
import com.scarit.maker.meta.enums.FileTypeEnum;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TemplateMaker {

    public static void main(String[] args) {
        //使用工具类雪花算法定义每次的工作文档
        Long id = IdUtil.getSnowflakeNextId();
        TemplateMaker templateMaker = new TemplateMaker();
        templateMaker.makeTemplate(id);

    }

    private static Long makeTemplate(Long id) {

        if (id == null) {
            //使用工具类雪花算法定义每次的工作文档
            id = IdUtil.getSnowflakeNextId();
        }
        // 一、基本信息
        // 1.输入项目基本信息

        String name = "acm-template-generator";
        String description = "acm示例代码生成器";

        // 2.指定原始项目路径
        String projectPath = System.getProperty("user.dir");
        String originRootPath = new File(projectPath).getParent() + File.separator + "generator-demo-projects/acm-template";
        //工作空间隔离
        String tempDir = projectPath + File.separator + ".temp";
        String templatePath = tempDir + File.separator + id;
        //工作目录是否存在，不存在责表示是第一次制作
        if (!FileUtil.exist(templatePath)) {
            FileUtil.mkdir(templatePath);
            FileUtil.copy(originRootPath, templatePath, true);

        }


        //工作文件输出路径
        String sourceRootPath = templatePath + File.separator + FileUtil.getLastPathEle(Paths.get(originRootPath)).toString();

        // windows系统下要对路径进行转义
        sourceRootPath = sourceRootPath.replaceAll("\\\\", "/");

        String fileInputPath = "src/com/scarit/acm/MainTemplate.java";
        String fileOutputPath = fileInputPath + ".ftl";

        // 3.输入模型参数信息
        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
        modelInfo.setFieldName("outputText1111");
        modelInfo.setType("String");
        modelInfo.setDefaultValue("sum =");

        // 二、使用字符串替换，生成模版文件
        String fileInputAbosulutePath = sourceRootPath + File.separator + fileInputPath;
        String fileOutputAbosulutePath = sourceRootPath + File.separator + fileOutputPath;
        String fileContent;
        // 如果已有模板，则不是第一次制作，直接读取生成好的模板
        if (FileUtil.exist(fileOutputAbosulutePath)) {
            fileContent = FileUtil.readUtf8String(fileOutputAbosulutePath);
        }
        // 没有模板，则读取原文件
        else {
            fileContent = FileUtil.readUtf8String(fileInputAbosulutePath);
        }

        String replacement = StrUtil.format("${{}}", modelInfo.getFieldName());
        String newFileContent = StrUtil.replace(fileContent, "Sum:", replacement);

        // 输出模版文件
        FileUtil.writeUtf8String(newFileContent, fileOutputAbosulutePath);

        // 文件配置信息
        Meta.FileConfig.FileInfo fileInfo = new Meta.FileConfig.FileInfo();
        fileInfo.setInputPath(fileInputPath);
        fileInfo.setOutputPath(fileOutputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());

        // 三、生成配置文件
        String metaOutputPath = sourceRootPath + File.separator + "meta.json";

        // 如果已有meta文件，说明不是第一次制作，则在meta基础上进行修改
        if (FileUtil.exist(metaOutputPath)) {

            Meta oldMeta = JSONUtil.toBean(FileUtil.readUtf8String(metaOutputPath), Meta.class);
            List<Meta.FileConfig.FileInfo> fileInfoList = oldMeta.getFileConfig().getFiles();
            fileInfoList.add(fileInfo);

            List<Meta.ModelConfig.ModelInfo> modelInfoList = oldMeta.getModelConfig().getModels();
            modelInfoList.add(modelInfo);

        } else {

            // 1.构造配置参数
            Meta meta = new Meta();
            meta.setName(name);
            meta.setDescription(description);

            Meta.FileConfig fileConfig = new Meta.FileConfig();
            meta.setFileConfig(fileConfig);
            fileConfig.setSourceRootPath(sourceRootPath);
            ArrayList<Meta.FileConfig.FileInfo> fileInfoList = new ArrayList<>();
            fileConfig.setFiles(fileInfoList);
            fileInfoList.add(fileInfo);

            Meta.ModelConfig modelConfig = new Meta.ModelConfig();
            meta.setModelConfig(modelConfig);
            ArrayList<Meta.ModelConfig.ModelInfo> modelInfoList = new ArrayList<>();
            modelConfig.setModels(modelInfoList);
            modelInfoList.add(modelInfo);


            // 2. 输出元信息文件
            FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(meta), metaOutputPath);
        }
        return id;
    }

    private static List<Meta.FileConfig.FileInfo> distinctFiles(List<Meta.FileConfig.FileInfo> fileInfoList) {
        List<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>(
                fileInfoList.stream().collect(
                Collectors.toMap(Meta.FileConfig.FileInfo::getInputPath, fileInfo -> fileInfo, (oldFileinfo, newFileInfo) -> newFileInfo)
        ).values());
        return newFileInfoList;
    }

}
