package com.scarit.maker.template.enums;


import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

@Getter
public enum FileFilterRangeEnum {

    FILE_NAME("文件名", "fileName"),

    FILE_CONTENT("文件内容", "fileContent");


    private final String text;

    private final String value;

    FileFilterRangeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据value获取枚举
     * @param value
     * @return
     */
    public static FileFilterRangeEnum getEnumByValue(String value) {
        if (ObjUtil.isEmpty(value)) {
            return null;
        }
        for (FileFilterRangeEnum anRangeEnum : FileFilterRangeEnum.values()) {
            if (anRangeEnum.getValue().equals(value)) {
                return anRangeEnum;
            }
        }
        return null;
    }
}
