package com.scarit.web.model.dto.generator;

import com.scarit.web.meta.Meta;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建请求
 *
 * @author ADI
 */
@Data
public class GeneratorAddRequest implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 基础包
     */
    private String basePackage;

    /**
     * 版本号
     */
    private String version;

    /**
     * 作者
     */
    private String author;

    /**
     * 标签列表（JSON数组）
     */
    private List<String> tags;

    /**
     * 图片
     */
    private String picture;

    /**
     * 文件配置（JSON字符串）
     */
    private Meta.FileConfig fileConfig;

    /**
     * 模型配置（JSON字符串）
     */
    private Meta.ModelConfig modelConfig;

    /**
     * 代码生成器产物路径
     */
    private String distPath;

    /**
     * 状态: 0/1
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}