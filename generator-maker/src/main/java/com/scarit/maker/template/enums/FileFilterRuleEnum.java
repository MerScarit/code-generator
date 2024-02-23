package com.scarit.maker.template.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

@Getter
public enum FileFilterRuleEnum {

    REGEX("正则", "regex"),

    START_WITH("前缀匹配", "start_with"),

    END_WITH("后缀匹配", "end_with"),

    EQUALS("相等", "equals"),


    CONTAIN("包含", "contain");


    private final String text;

    private final String value;

    FileFilterRuleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据value获取枚举
     * @param value
     * @return
     */
    public static FileFilterRuleEnum getEnumByValue(String value) {
        if (ObjUtil.isEmpty(value)) {
            return null;
        }
        for (FileFilterRuleEnum anRuleEnum : FileFilterRuleEnum.values()) {
            if (anRuleEnum.getValue().equals(value)) {
                return anRuleEnum;
            }
        }
        return null;
    }
}
