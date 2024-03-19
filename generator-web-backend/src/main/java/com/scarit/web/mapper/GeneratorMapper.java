package com.scarit.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scarit.web.model.entity.Generator;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 生成器数据库操作
 *
 * @author ADI
 */
public interface GeneratorMapper extends BaseMapper<Generator> {


    @Select("SELECT distPath From generator WHERE isDelete = 1")
    List<String> listDeletedGeneratorDistPath();

}




