package com.scarit.maker.model;

import lombok.Data;

/**
 * @author ADI
 * @description: 数据模型
 * @date 2024-02-11
 */

@Data
public class DataModel {

//    /**
//     *  1.在代码开头增加作者@Author 注释（增加代码）
//     */
//    private String author ="ADI";
//    /**
//     * 2.修改程序输出的信息提示
//     */
//    private String output = "结果为";

    /**
     * 3.将循环读取输入改为单独读取（可选代码）
     */
    public boolean loop = true;

    /**
     * 核心模板
     */
    public MainTemplate mainTemplate = new MainTemplate();

    /**
     * 核心模板
     */
    @Data
    public static class MainTemplate {

        /**
         *  1.在代码开头增加作者@Author 注释（增加代码）
         */
        public String author ="ADI";

        /**
         * 2.修改程序输出的信息提示
         */
        public String output = "结果为";

    }
}
