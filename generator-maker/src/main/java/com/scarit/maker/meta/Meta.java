package com.scarit.maker.meta;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class Meta implements Serializable{


    private String name;
    private String description;
    private String basePackage;
    private String version;
    private boolean enableGitVersionControl;
    private String author;
    private String createTime;
    private FileConfig fileConfig;
    private ModelConfig modelConfig;

    @NoArgsConstructor
    @Data
    public static class FileConfig implements Serializable {
        
        private String inputRootPath;
        private String outputRootPath;
        private String sourceRootPath;
        private String type;
        private List<FileInfo> files;

        @Data
        public static class FileInfo implements Serializable {

            private String groupKey;
            private String groupName;
            private String type;
            private String condition;
            private List<FileInfo> files;
            private String inputPath;
            private String outputPath;
            private String generateType;


        }
    }

    @NoArgsConstructor
    @Data
    public static class ModelConfig implements Serializable {
        
        private List<ModelInfo> models;

        @NoArgsConstructor
        @Data
        public static class ModelInfo implements Serializable {
            
            private String fieldName;
            private String type;
            private String description;
            private Object defaultValue;
            private String abbr;
            private String groupKey;
            private String groupName;
            private String condition;
            private List<ModelInfo> models;

            //中间参数
            //用于拼接该分组下所有参数拼成字符串
            private String allArgsStr;
        }
    }
}
