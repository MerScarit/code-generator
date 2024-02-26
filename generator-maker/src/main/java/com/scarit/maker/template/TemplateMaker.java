package com.scarit.maker.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.scarit.maker.meta.Meta;
import com.scarit.maker.meta.enums.FileGenerateTypeEnum;
import com.scarit.maker.meta.enums.FileTypeEnum;
import com.scarit.maker.template.enums.FileFilterRangeEnum;
import com.scarit.maker.template.enums.FileFilterRuleEnum;
import com.scarit.maker.template.model.FileFilterConfig;
import com.scarit.maker.template.model.TemplateMakerFileConfig;
import com.scarit.maker.template.model.TemplateMakerModelConfig;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class TemplateMaker {


    /**
     * 制作模板
     *
     * @param newMeta
     * @param originRootPath
     * @param templateMakerFileConfig
     * @param templateMakerModelConfig
     * @param id
     * @return
     */
    public static Long makeTemplate(Meta newMeta, String originRootPath, TemplateMakerFileConfig templateMakerFileConfig, TemplateMakerModelConfig templateMakerModelConfig, Long id) {
        
        if (id == null) {
            //使用工具类雪花算法定义每次的工作文档
            id = IdUtil.getSnowflakeNextId();
        }
        // 一、基本信息

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

        // 二、生成文件模板
        List<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>();
        List<TemplateMakerFileConfig.FileInfoConfig> fileInfoConfigList = templateMakerFileConfig.getFiles();
        for (TemplateMakerFileConfig.FileInfoConfig fileInfoConfig : fileInfoConfigList) {
            String fileInputPath = fileInfoConfig.getPath();
            // 如果填入的是相对路径要改为绝对路径
            if (!fileInputPath.startsWith(sourceRootPath)) {
                fileInputPath = sourceRootPath + File.separator + fileInputPath;
            }
            // 新增FileFilter.doFilter()后不用考虑输入的是否是目录
            // 获取过滤后的文件列表
            List<File> fileList = FileFilter.doFilter(fileInputPath, fileInfoConfig.getFilterConfigList());
            // 不处理已经生成的 FTL 模板文件
            fileList = fileList.stream().filter(file -> file.getAbsolutePath().endsWith(".ftl")).collect(Collectors.toList());

            for (File file : fileList) {
                    Meta.FileConfig.FileInfo fileInfo = makeFileTemplate(templateMakerModelConfig, sourceRootPath, file);
                    newFileInfoList.add(fileInfo);
            }

        }
        // 如果是文件组
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = templateMakerFileConfig.getFileGroupConfig();
        if (fileGroupConfig != null) {
            String condition = fileGroupConfig.getCondition();
            String groupKey = fileGroupConfig.getGroupKey();
            String groupName = fileGroupConfig.getGroupName();
            // 新增文件分组配置
            Meta.FileConfig.FileInfo groupFileInfo = new Meta.FileConfig.FileInfo();
            groupFileInfo.setType(FileTypeEnum.GROUP.getValue());
            groupFileInfo.setCondition(condition);
            groupFileInfo.setGroupKey(groupKey);
            groupFileInfo.setGroupName(groupName);
            // 文件全部放在一个分组中
            groupFileInfo.setFiles(newFileInfoList);
            newFileInfoList = new ArrayList<>();
            newFileInfoList.add(groupFileInfo);
        }
        
        // 处理模型信息
        List<TemplateMakerModelConfig.ModelInfoConfig> models = templateMakerModelConfig.getModels();
        
        // 转化为配置接受的ModelInfo对象
        List<Meta.ModelConfig.ModelInfo> inputModelInfoList = models.stream().map(modelInfoConfig -> {
            Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
            BeanUtil.copyProperties(modelInfoConfig, modelInfo);
            return modelInfo;
        }).collect(Collectors.toList());

        // 本次新增的模型配置列表
        List<Meta.ModelConfig.ModelInfo> newModelInfoList = new ArrayList<>();
        
        // 如果是模型组
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        if (modelGroupConfig != null) {
            String groupKey = modelGroupConfig.getGroupKey();
            String condition = modelGroupConfig.getCondition();
            String groupName = modelGroupConfig.getGroupName();

            Meta.ModelConfig.ModelInfo groupModelInfo = new Meta.ModelConfig.ModelInfo();
            groupModelInfo.setGroupKey(groupKey);
            groupModelInfo.setGroupName(groupName);
            groupModelInfo.setCondition(condition);

            // 模型全部放到一个分组内
            groupModelInfo.setModels(inputModelInfoList);
            newModelInfoList.add(groupModelInfo);

        }
        // 不分组,添加所有的模型信息列表
        else {
            newModelInfoList.addAll(inputModelInfoList);
        }
        
        // 三、生成配置文件
        String metaOutputPath = templatePath + File.separator + "meta.json";

        // 如果已有meta文件，说明不是第一次制作，则在meta基础上进行修改
        if (FileUtil.exist(metaOutputPath)) {
            Meta oldMeta = JSONUtil.toBean(FileUtil.readUtf8String(metaOutputPath), Meta.class);
            BeanUtil.copyProperties(newMeta, oldMeta, CopyOptions.create().ignoreNullValue());
            newMeta = oldMeta;
            // 追加配置参数
            List<Meta.FileConfig.FileInfo> fileInfoList = newMeta.getFileConfig().getFiles();
            fileInfoList.addAll(newFileInfoList);
            List<Meta.ModelConfig.ModelInfo> modelInfoList = newMeta.getModelConfig().getModels();
            modelInfoList.addAll(newModelInfoList);

            // 配置去重
            newMeta.getFileConfig().setFiles(distinctFiles(fileInfoList));
            newMeta.getModelConfig().setModels(distinctModels(modelInfoList));


        } else {
            // 1.构造配置参数
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
            modelInfoList.addAll(newModelInfoList);

        }
        // 2. 输出元信息文件
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(newMeta), metaOutputPath);
        return id;
    }

    /**
     * 制作文件模版
     * @param templateMakerModelConfig
     * @param sourceRootPath
     * @param inputFile
     * @return
     */
    private static Meta.FileConfig.FileInfo makeFileTemplate(TemplateMakerModelConfig templateMakerModelConfig, String sourceRootPath, File inputFile) {
        // 要挖坑的文件的绝对路径
        String fileInputAbsolutePath = inputFile.getAbsolutePath().replaceAll("\\\\", "/");
        String fileOutputAbsolutePath = fileInputAbsolutePath + ".ftl";

        // 文件输入输出的相对路径
        String fileInputPath = fileInputAbsolutePath.replace(sourceRootPath + "/", "");
        String fileOutputPath = fileInputPath + ".ftl;";
        
        // 使用字符串替换生成模版
        String fileContent;
        // 如果已有模板，则不是第一次制作，直接读取生成好的模板
        boolean hasTemplateFile = FileUtil.exist(fileOutputAbsolutePath);
        if (hasTemplateFile) {
            fileContent = FileUtil.readUtf8String(fileOutputAbsolutePath);
        }
        // 没有模板，则读取原文件
        else {
            fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        }
        
        // 支持多个模型：对同一个文件的内容，遍历模型进行多轮替换
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        String newFileContent = fileContent;
        String replacement;
        for (TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig : templateMakerModelConfig.getModels()) {
            // 不是分组
            if (modelGroupConfig == null) {
                replacement = StrUtil.format("${{}}", modelInfoConfig.getFieldName());
            }
            // 是分组
            else {
                String groupKey = modelGroupConfig.getGroupKey();
                // 替换值需要多加一个组别层级
                replacement = StrUtil.format("${{}.{}}", groupKey, modelInfoConfig.getFieldName());
            }
            // 多次替换
            newFileContent = StrUtil.replace(newFileContent, modelInfoConfig.getReplaceText(), replacement);
        }

        // 文件配置信息
        Meta.FileConfig.FileInfo fileInfo = new Meta.FileConfig.FileInfo();
        // 输出的meta文件需要将两个两个路径置换过来
        fileInfo.setInputPath(fileOutputPath);
        fileInfo.setOutputPath(fileInputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        // 设置文件类型默认值为 DYNAMIC
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());

        // 文件内容是否产生改变
        boolean contentChanged = newFileContent.equals(fileContent);
        // 之前没有模板文件
        if (!hasTemplateFile) {
            // 且文件内容没有改变，则该文件是静态文件
            if (contentChanged) {
                // 输出路径 = 输入路径 , 并将文件配置类型定义为静态文件
                fileInfo.setInputPath(fileInputPath);
                fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
            }
            // 文件内容有变化 ，输出模版文件，文件配置为动态文件
            else {
                FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutePath);
            }
        }
        // 已经有模版文件，且文件内容发生改变，则生成文件；若已有模版文件且文件无改变，则不执行操作
        else if (!contentChanged) {
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
        
        // 1.将所有文件配置(fileInfo)分为有分组的和无分组的
        // 有分组
        Map<String, List<Meta.FileConfig.FileInfo>> groupKeyFileInfoListMap = fileInfoList.stream()
                .filter(fileInfo -> StrUtil.isNotBlank(fileInfo.getGroupKey()))
                .collect(Collectors.groupingBy(Meta.FileConfig.FileInfo::getGroupKey));
        
        // 2.对于有分组的文件配置，如果有相同的分组，同分组内的文件进行合并（merge），不同分组的则保留
        HashMap<String, Meta.FileConfig.FileInfo> groupKeyMergedFileInfoMap = new HashMap<>();
        for (Map.Entry<String, List<Meta.FileConfig.FileInfo>> entry : groupKeyFileInfoListMap.entrySet()) {
            List<Meta.FileConfig.FileInfo> tempFileInfoList = entry.getValue();
            ArrayList<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>(tempFileInfoList.stream()
                    .flatMap(fileInfo -> fileInfo.getFiles().stream())
                    .collect(
                            Collectors.toMap(Meta.FileConfig.FileInfo::getOutputPath, fileInfo -> fileInfo, (oldFileinfo, newFileInfo) -> newFileInfo)
                    ).values());
            
            // 使用新的group配置
            Meta.FileConfig.FileInfo newFileInfo = CollUtil.getLast(tempFileInfoList);
            newFileInfo.setFiles(newFileInfoList);
            String groupKey = newFileInfo.getGroupKey();
            groupKeyMergedFileInfoMap.put(groupKey, newFileInfo);
        }
        
        // 3.创建新的文件配置列表（结果列表），先将合并后的分组添加进结果列表
        List<Meta.FileConfig.FileInfo> resultMap = new ArrayList<>(groupKeyMergedFileInfoMap.values());
        

        // 4.再将无分组的文件配置列表添加到结果列表
        List<Meta.FileConfig.FileInfo> noGroupFileInfoList =
                fileInfoList.stream().
                        filter(fileInfo -> StrUtil.isBlank(fileInfo.getGroupKey()))
                        .collect(Collectors.toList());
        resultMap.addAll(new ArrayList<>(noGroupFileInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.FileConfig.FileInfo::getOutputPath, fileInfo -> fileInfo, (oldFileinfo, newFileInfo) -> newFileInfo)
                ).values()));
        
        return resultMap;
    }


    /**
     * ModelInfo去重
     * @param modelInfoList
     * @return
     */

    private static List<Meta.ModelConfig.ModelInfo> distinctModels(List<Meta.ModelConfig.ModelInfo> modelInfoList) {
        // 1.将所有模型配置(ModelInfo)分为有分组的和无分组的
        // 有分组
        Map<String, List<Meta.ModelConfig.ModelInfo>> groupKeyModelInfoListMap = modelInfoList.stream()
                .filter(ModelInfo -> StrUtil.isNotBlank(ModelInfo.getGroupKey()))
                .collect(Collectors.groupingBy(Meta.ModelConfig.ModelInfo::getGroupKey));

        // 2.对于有分组的模型配置，如果有相同的分组，同分组内的模型进行合并（merge），不同分组的则保留
        HashMap<String, Meta.ModelConfig.ModelInfo> groupKeyMergedModelInfoMap = new HashMap<>();
        for (Map.Entry<String, List<Meta.ModelConfig.ModelInfo>> entry : groupKeyModelInfoListMap.entrySet()) {
            List<Meta.ModelConfig.ModelInfo> tempModelInfoList = entry.getValue();
            ArrayList<Meta.ModelConfig.ModelInfo> newModelInfoList = new ArrayList<>(tempModelInfoList.stream()
                    .flatMap(ModelInfo -> ModelInfo.getModels().stream())
                    .collect(
                            Collectors.toMap(Meta.ModelConfig.ModelInfo::getFieldName, ModelInfo -> ModelInfo, (oldModelinfo, newModelInfo) -> newModelInfo)
                    ).values());

            // 使用新的group配置
            Meta.ModelConfig.ModelInfo newModelInfo = CollUtil.getLast(tempModelInfoList);
            newModelInfo.setModels(newModelInfoList);
            String groupKey = newModelInfo.getGroupKey();
            groupKeyMergedModelInfoMap.put(groupKey, newModelInfo);
        }

        // 3.创建新的模型配置列表（结果列表），先将合并后的分组添加进结果列表
        List<Meta.ModelConfig.ModelInfo> resultMap = new ArrayList<>(groupKeyMergedModelInfoMap.values());


        // 4.再将无分组的模型配置列表添加到结果列表
        List<Meta.ModelConfig.ModelInfo> noGroupModelInfoList =
                modelInfoList.stream().
                        filter(ModelInfo -> StrUtil.isBlank(ModelInfo.getGroupKey()))
                        .collect(Collectors.toList());
        resultMap.addAll(new ArrayList<>(noGroupModelInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.ModelConfig.ModelInfo::getFieldName, ModelInfo -> ModelInfo, (oldModelinfo, newModelInfo) -> newModelInfo)
                ).values()));

        return resultMap;
    }
}
