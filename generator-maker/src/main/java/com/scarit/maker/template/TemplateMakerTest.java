package com.scarit.maker.template;

import com.scarit.maker.meta.Meta;
import com.scarit.maker.template.enums.FileFilterRangeEnum;
import com.scarit.maker.template.enums.FileFilterRuleEnum;
import com.scarit.maker.template.model.FileFilterConfig;
import com.scarit.maker.template.model.TemplateMakerFileConfig;
import com.scarit.maker.template.model.TemplateMakerModelConfig;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class TemplateMakerTest {

    @Test
    public void makeTemplateTestBug01() {

        // 1.输入项目基本信息
        Meta meta = new Meta();
        String name = "springboot-init";
        String description = "springboot初始化模板";
        meta.setName(name);
        meta.setDescription(description);
        // 2.指定原始项目路径
        String projectPath = System.getProperty("user.dir");
        String originRootPath = new File(projectPath).getParent() + File.separator + "generator-demo-projects/springboot-init";

        // 文件参数配置
        String fileInputPath1 = "src/main/java/com/yupi/springbootinit/common";
        // 模型参数配置
        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();
        // 模型配置
        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig1 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig1.setFieldName("mysql");
        modelInfoConfig1.setType("String");
        modelInfoConfig1.setReplaceText("jdbc:mysql://localhost:3306/my_db");

        List<TemplateMakerModelConfig.ModelInfoConfig> modelInfoConfigList = Arrays.asList(modelInfoConfig1);
        templateMakerModelConfig.setModels(modelInfoConfigList);


        // 文件过滤
        TemplateMakerFileConfig templateMakerFileConfig = new TemplateMakerFileConfig();

        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig1 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig1.setPath(fileInputPath1);
        templateMakerFileConfig.setFiles(Arrays.asList(fileInfoConfig1));

        long id = TemplateMaker.makeTemplate(meta,originRootPath,templateMakerFileConfig, templateMakerModelConfig,1L);
        System.out.println(id);
    }

    @Test
    public void makeTemplateTestBug02() {

        // 1.输入项目基本信息
        Meta meta = new Meta();
        String name = "springboot-init";
        String description = "springboot初始化模板";
        meta.setName(name);
        meta.setDescription(description);
        // 2.指定原始项目路径
        String projectPath = System.getProperty("user.dir");
        String originRootPath = new File(projectPath).getParent() + File.separator + "generator-demo-projects/springboot-init";

        // 文件参数配置
        String fileInputPath1 = "./";
        // 模型参数配置
        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();
        // 模型配置
        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig1 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig1.setFieldName("className");
        modelInfoConfig1.setType("String");
        modelInfoConfig1.setReplaceText("BaseResponse");

        List<TemplateMakerModelConfig.ModelInfoConfig> modelInfoConfigList = Arrays.asList(modelInfoConfig1);
        templateMakerModelConfig.setModels(modelInfoConfigList);


        // 文件过滤
        TemplateMakerFileConfig templateMakerFileConfig = new TemplateMakerFileConfig();

        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig1 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig1.setPath(fileInputPath1);
        templateMakerFileConfig.setFiles(Arrays.asList(fileInfoConfig1));

        long id = TemplateMaker.makeTemplate(meta,originRootPath,templateMakerFileConfig, templateMakerModelConfig,1L);
        System.out.println(id);
    }
}