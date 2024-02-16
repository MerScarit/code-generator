package com.scarit.maker.meta.enums;

/**
 * 生成文件类型枚举
 */
public enum FileGenerateTypeEnum {

    DYNAMIC("动态文件", "dynamic"),

    STATIC("静态文件", "static");

   

    private String text;

    private String value;

    FileGenerateTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
