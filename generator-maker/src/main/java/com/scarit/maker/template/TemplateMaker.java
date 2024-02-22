package com.scarit.maker.template;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.scarit.maker.meta.Meta;
import com.scarit.maker.meta.enums.FileGenerateTypeEnum;
import com.scarit.maker.meta.enums.FileTypeEnum;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TemplateMaker {

    public static void main(String[] args) {

        // 1.输入项目基本信息
        Meta meta = new Meta();
        String name = "springboot-init";
        String description = "springboot初始化模板";
        meta.setName(name);
        meta.setDescription(description);
        // 2.指定原始项目路径
        String projectPath = System.getProperty("user.dir");
        String originRootPath = new File(projectPath).getParent() + File.separator + "generator-demo-projects/springboot-init";

        String fileInputPath1 = "src/main/java/com/yupi/springbootinit/common";
        String fileInputPath2 = "src/main/java/com/yupi/springbootinit/config";

        List<String> fileInputPathList = Arrays.asList(fileInputPath1, fileInputPath2);


        // 3.输入模型参数信息
        // 首次输入
//        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
//        modelInfo.setFieldName("outputText1111");
//        modelInfo.setType("String");
//        modelInfo.setDefaultValue("sum =");
        //自定义第二次输入
        // 3.输入模型参数信息
        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
        modelInfo.setFieldName("className");
        modelInfo.setType("String");

        String searchStr = "BaseResponse";

        //使用工具类雪花算法定义每次的工作文档
//        Long id = IdUtil.getSnowflakeNextId();
        TemplateMaker templateMaker = new TemplateMaker();
        templateMaker.makeTemplate(meta,originRootPath, fileInputPathList, searchStr, modelInfo,1L);

    }

    /**
     * 制作模板
     * @param newMeta
     * @param originRootPath
     * @param fileInputPathList
     * @param searchStr
     * @param modelInfo
     * @param id
     * @return
     */
    private static Long makeTemplate(Meta newMeta, String originRootPath, List<String> fileInputPathList, String searchStr, Meta.ModelConfig.ModelInfo modelInfo, Long id) {

        if (id == null) {
            //使用工具类雪花算法定义每次的工作文档
            id = IdUtil.getSnowflakeNextId();
        }
        // 一、基本信息
        // 1.输入项目基本信息
        String name = newMeta.getName() ;
        String description = newMeta.getDescription();

        // 2.指定原始项目路径
        String projectPath = System.getProperty("user.dir");
        // 工作空间隔离
        String tempDir = projectPath + File.separator + ".temp";
        String templatePath = tempDir + File.separator + id;
        // 工作目录是否存在，不存在责表示是第一次制作
        if (!FileUtil.exist(templatePath)) {
            FileUtil.mkdir(templatePath);
            FileUtil.copy(originRootPath, templatePath, true);
        }
        
        //工作文件输出路径
        String sourceRootPath = templatePath + File.separator + FileUtil.getLastPathEle(Paths.get(originRootPath)).toString();

        // windows系统下要对路径进行转义
        sourceRootPath = sourceRootPath.replaceAll("\\\\", "/");


//        // 3.输入模型参数信息
//        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
//        modelInfo.setFieldName("outputText1111");
//        modelInfo.setType("String");
//        modelInfo.setDefaultValue("sum =");




        // 二、生成文件模板
        List<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>();
        for (String fileInputPath : fileInputPathList) {
            String fileOutputAbsolutePath = sourceRootPath + File.separator + fileInputPath;
            // 输入的是目录
            if (FileUtil.isDirectory(new File(fileOutputAbsolutePath))) {
                List<File> fileList = FileUtil.loopFiles(fileOutputAbsolutePath);
                for (File file : fileList){
                    Meta.FileConfig.FileInfo fileInfo = makeFileTemplate(searchStr, modelInfo, sourceRootPath, file);
                    newFileInfoList.add(fileInfo);
                }
            }
            // 输入的是文件
            else {
                Meta.FileConfig.FileInfo fileInfo = makeFileTemplate(searchStr, modelInfo, sourceRootPath, new File(fileOutputAbsolutePath));
                newFileInfoList.add(fileInfo);
            }
        }

        // 三、生成配置文件
        String metaOutputPath = sourceRootPath + File.separator + "meta.json";

        // 如果已有meta文件，说明不是第一次制作，则在meta基础上进行修改
        if (FileUtil.exist(metaOutputPath)) {
            Meta oldMeta = JSONUtil.toBean(FileUtil.readUtf8String(metaOutputPath), Meta.class);
            // 追加配置参数
            List<Meta.FileConfig.FileInfo> fileInfoList = oldMeta.getFileConfig().getFiles();
            fileInfoList.addAll(newFileInfoList);
            List<Meta.ModelConfig.ModelInfo> modelInfoList = oldMeta.getModelConfig().getModels();
            modelInfoList.add(modelInfo);

            // 配置去重
            oldMeta.getFileConfig().setFiles(distinctFiles(fileInfoList));
            oldMeta.getModelConfig().setModels(distinctModels(modelInfoList));
            
            // 更新输出元信息文件
            FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(oldMeta), metaOutputPath);
        } else {

            // 1.构造配置参数
            newMeta.setName(name);
            newMeta.setDescription(description);

            Meta.FileConfig fileConfig = new Meta.FileConfig();
            newMeta.setFileConfig(fileConfig);
            fileConfig.setSourceRootPath(sourceRootPath);
            List<Meta.FileConfig.FileInfo> fileInfoList = new ArrayList<>();
            fileConfig.setFiles(fileInfoList);
            fileInfoList.addAll(newFileInfoList);


            Meta.ModelConfig modelConfig = new Meta.ModelConfig();
            newMeta.setModelConfig(modelConfig);
            List<Meta.ModelConfig.ModelInfo> modelInfoList = new ArrayList<>();
            modelConfig.setModels(modelInfoList);
            modelInfoList.add(modelInfo);

        }
        // 2. 输出元信息文件
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(newMeta), metaOutputPath);
        return id;
    }

    /**
     * 制作文件模版
     * @param searchStr
     * @param modelInfo
     * @param sourceRootPath
     * @param inputFile
     * @return
     */
    private static Meta.FileConfig.FileInfo makeFileTemplate(String searchStr, Meta.ModelConfig.ModelInfo modelInfo, String sourceRootPath, File inputFile) {
        // 要挖坑的文件的绝对路径
        String fileInputAbsolutePath = inputFile.getAbsolutePath().replaceAll("\\\\", "/");
        String fileOutputAbsolutePath = fileInputAbsolutePath + ".ftl";

        // 文件输入输出的相对路径
        String fileInputPath = fileInputAbsolutePath.replace(sourceRootPath + "/", "");
        String fileOutputPath = fileInputPath + ".ftl;";
        String fileContent;
        // 如果已有模板，则不是第一次制作，直接读取生成好的模板
        if (FileUtil.exist(fileOutputAbsolutePath)) {
            fileContent = FileUtil.readUtf8String(fileOutputAbsolutePath);
        }
        // 没有模板，则读取原文件
        else {
            fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        }

        String replacement = StrUtil.format("${{}}", modelInfo.getFieldName());
        String newFileContent = StrUtil.replace(fileContent, searchStr, replacement);

        // 文件配置信息
        Meta.FileConfig.FileInfo fileInfo = new Meta.FileConfig.FileInfo();
        fileInfo.setInputPath(fileInputPath);
        fileInfo.setOutputPath(fileOutputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());

        // 如果模版文件内容没有变化
        if (newFileContent.equals(fileContent)) {
            // 输出路径 = 输入路径 , 并将文件类型定义为静态文件
            fileInfo.setOutputPath(fileInputPath);
            fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
        }
        // 模板文件有变化
        else {
            fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
            // 输出模版文件
            FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutePath);
        }

        return fileInfo;
    }

    /**
     * FileInfo去重
     * @param fileInfoList
     * @return
     */
    private static List<Meta.FileConfig.FileInfo> distinctFiles(List<Meta.FileConfig.FileInfo> fileInfoList) {
        List<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>(
                fileInfoList.stream().collect(
                Collectors.toMap(Meta.FileConfig.FileInfo::getInputPath, fileInfo -> fileInfo, (oldFileinfo, newFileInfo) -> newFileInfo)
            ).values()
        );
        return newFileInfoList;
    }


    /**
     * ModelInfo去重
     * @param modelInfoList
     * @return
     */

    private static List<Meta.ModelConfig.ModelInfo> distinctModels(List<Meta.ModelConfig.ModelInfo> modelInfoList) {
        List<Meta.ModelConfig.ModelInfo> newModelInfoList = new ArrayList<>(
                modelInfoList.stream().collect(
                        Collectors.toMap(Meta.ModelConfig.ModelInfo::getFieldName, modelInfo -> modelInfo, (oldModelInfo, newModelInfo) -> newModelInfo)

                ).values()
        );
        return newModelInfoList;
    }
}
