package com.scarit.web.model.dto.generator;

import lombok.Data;

import java.io.Serializable;

/**
 * 代码生成器缓存参数
 */
@Data
public class GeneratorCacheRequest implements Serializable {


    /**
     * 要缓存的生成器的id
     */
    private Long id;


    private static final long serialVersionUID = 1L;

}
