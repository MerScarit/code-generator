package com.scarit.maker.template.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class TemplateMakerModelConfig {

    private List<TemplateMakerModelConfig.ModelInfoConfig> models;

    private TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig;


    @Data
    @NoArgsConstructor
    public static class ModelInfoConfig {

        private String fieldName;
        private String type;
        private String description;
        private Object defaultValue;
        private String abbr;

        // 用于替换哪些文本
        private String replaceText;

    }

    @Data
    public static class ModelGroupConfig {

        /**
         * 模型组唯一标识
         */
        private String groupKey;

        /**
         * 模型组状态
         */
        private String condition;
        
        /**
         * 模型组名字
         */
        private String groupName;

        /**
         * 模型组类型
         */
        private String type;

        /**
         * 模型组描述
         */
        private String description;

    }
}
