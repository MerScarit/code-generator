package com.scarit.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scarit.web.model.dto.generator.GeneratorQueryRequest;
import com.scarit.web.model.entity.Generator;
import com.scarit.web.model.vo.GeneratorVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 帖子服务
 *
 * @author ADI
 */
public interface GeneratorService extends IService<Generator> {

    /**
     * 校验
     *
     * @param generator
     * @param add
     */
    void validGenerator(Generator generator, boolean add);

    /**
     * 获取查询条件
     *
     * @param generatorQueryRequest
     * @return
     */
    QueryWrapper<Generator> getQueryWrapper(GeneratorQueryRequest generatorQueryRequest);

    /**
     * 获取帖子封装
     *
     * @param generator
     * @param request
     * @return
     */
    GeneratorVO getGeneratorVO(Generator generator, HttpServletRequest request);

    /**
     * 分页获取帖子封装
     *
     * @param GeneratorPage
     * @param request
     * @return
     */
    Page<GeneratorVO> getGeneratorVOPage(Page<Generator> GeneratorPage, HttpServletRequest request);
}
