package com.scarit.maker.template.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 封装所有和文件相关的配置
 */
@Data
public class TemplateMakerFileConfig {

    private List<FileInfoConfig> files;

    private FileGroupConfig fileGroupConfig;


    @Data
    @NoArgsConstructor
    public static class FileInfoConfig {

        /**
         * 文件路径
         */
        private String path;

        /**
         * 文件生成控制参数
         */
        private String condition;

        /**
         * 文件过滤配置
         */
        private List<FileFilterConfig> filterConfigList;

    }

    @Data
    public static class FileGroupConfig {

        /**
         * 文件组状态
         */
        private String condition;

        /**
         * 文件组唯一标识
         */
        private String groupKey;

        /**
         * 文件组名字
         */
        private String groupName;

    }
}
