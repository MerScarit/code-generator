package com.scarit.model;

import lombok.Data;

/**
 * @description: 数据模型模板
 */

@Data
public class DataModel {

    /**
     *  是否生成循环
     */
    private boolean loop =false;
    /**
     *  作者注释
     */
    private String author ="adi";
    /**
     *  输出信息
     */
    private String outputText ="输出总和 = ";
  
}
