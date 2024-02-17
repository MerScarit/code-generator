package com.scarit.model;

import lombok.Data;

/**
 * @author ADI
 * @description: 静态模型配置
 * @date 2024-02-11
 */

@Data
public class MainTemplateConfig {

    /**
     *  1.在代码开头增加作者@Author 注释（增加代码）
     */
    private String author = "ADI";
    /**
     * 2.修改程序输出的信息提示
     */
    private String output = "结果为";

    /**
     * 3.将循环读取输入改为单独读取（可选代码）
     */
    private boolean loop = true;
}
