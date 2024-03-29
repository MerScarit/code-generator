package com.scarit.web.model.dto.generator;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 使用代码生成器亲请求参数
 */
@Data
public class GeneratorUseRequest implements Serializable {


    /**
     * 要使用的生成器的id
     */
    private Long id;

    /**
     * 前端输入的meta参数
     */
    private Map<String, Object> dataModel;

    private static final long serialVersionUID = 1L;

}
