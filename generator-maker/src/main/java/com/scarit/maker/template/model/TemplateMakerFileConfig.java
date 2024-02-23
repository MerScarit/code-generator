package com.scarit.maker.template.model;

import com.scarit.maker.meta.Meta;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 封装所有和文件相关的配置
 */
@Data
public class TemplateMakerFileConfig {

    private List<FileInfoConfig> files;


    @Data
    @NoArgsConstructor
    public static class FileInfoConfig {

        /**
         * 文件路径
         */
        private String path;

        /**
         * 文件过滤配置
         */
        private List<FileFilterConfig> filterConfigList;

    }
}
