package com.scarit.web.model.dto.generator;

import com.scarit.maker.meta.Meta;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 制作代码生成器亲请求参数
 */
@Data
public class GeneratorMakeRequest implements Serializable {
    
    /**
     * 制作代码生成器的元信息
     */
    private Meta meta;

    /**
     * 压缩文件的路径
     */
    private String zipFilePath;

    private static final long serialVersionUID = 1L;
}
