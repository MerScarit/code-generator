package com.scarit.maker.template;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.scarit.maker.meta.Meta;
import com.scarit.maker.template.model.TemplateMakerConfig;
import com.scarit.maker.template.model.TemplateMakerFileConfig;
import com.scarit.maker.template.model.TemplateMakerModelConfig;
import com.scarit.maker.template.model.TemplateMakerOutputConfig;
import org.junit.jupiter.api.Test;

import java.io.File;
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

        TemplateMakerOutputConfig templateMakerOutputConfig = new TemplateMakerOutputConfig();

        long id = TemplateMaker.makeTemplate(meta,originRootPath,templateMakerFileConfig, templateMakerModelConfig,templateMakerOutputConfig,1L);
        System.out.println(id);
    }

    @Test
    public void makeTemplateTestBug02() {

        // 1.输入项目基本信息
        Meta meta = new Meta();
        String name = "acm-template";
        String description = "acm代码示例模板";
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
        
        
        TemplateMakerOutputConfig templateMakerOutputConfig = new TemplateMakerOutputConfig();

        long id = TemplateMaker.makeTemplate(meta, originRootPath, templateMakerFileConfig, templateMakerModelConfig, templateMakerOutputConfig, 1L);
        System.out.println(id);
    }

    @Test
    public void makeTemplateTest03() {

        // 1.输入项目基本信息
        Meta meta = new Meta();
        String name = "acm-template";
        String description = "acm代码示例模板";
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


        TemplateMakerConfig templateMakerConfig = new TemplateMakerConfig();
        templateMakerConfig.setMeta(meta);
        templateMakerConfig.setOriginProjectPath(originRootPath);
        templateMakerConfig.setFileConfig(templateMakerFileConfig);
        templateMakerConfig.setModelConfig(templateMakerModelConfig);
        templateMakerConfig.setId(1L);


        long id = TemplateMaker.makeTemplate(templateMakerConfig);
        System.out.println(id);
    }

    @Test
    public void makeTemplateTestWithJSON() {
        String configStr = ResourceUtil.readUtf8Str("templateMaker.json");
        TemplateMakerConfig templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        Long id = TemplateMaker.makeTemplate(templateMakerConfig);
        System.out.println(id);
    }

    @Test
    public void makeSpringBootTemplate() {
        String rootPath = "examples/springboot-init/";
        String configStr = ResourceUtil.readUtf8Str(rootPath + "templateMaker.json");
        TemplateMakerConfig templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        Long id = TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "templateMakerToChangePackageName.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "templateMakerToEnablePost.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "templateMakerToEnableCors.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "templateMakerToEnableDocs.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "templateMakerToControlDocs.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "templateMakerToEnableMYSQL.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "templateMakerToEnableRedis.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        configStr = ResourceUtil.readUtf8Str(rootPath + "templateMakerToEnableEs.json");
        templateMakerConfig = JSONUtil.toBean(configStr, TemplateMakerConfig.class);
        TemplateMaker.makeTemplate(templateMakerConfig);

        System.out.println(id);
    }


}