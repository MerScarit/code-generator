package com.scarit.web.job;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.scarit.web.manager.CosManager;
import com.scarit.web.mapper.GeneratorMapper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ClearCosJobHandler {

    @Resource
    private CosManager cosManager;

    @Resource
    GeneratorMapper generatorMapper;


    @XxlJob("clearJobHandler")
    public void clearCosJobHandler() {
        log.info("--------clearCosJobHandler start-------");
        
        // 1.可以清理每天用户制作生成器产生的文件的目录 ，目录不能有前缀
        cosManager.deleteDir("generator_make_template/");
        
        // 2.清理 COS 上用户已经删除的文件
        List<String> distPathList = generatorMapper.listDeletedGeneratorDistPath();
        List<String> keyList = distPathList.stream()
                .filter(StrUtil::isNotBlank)
                .map(distPath -> {
                    return distPath.substring(1);
                })
                .collect(Collectors.toList());
        if (CollUtil.isNotEmpty(keyList)) {
            cosManager.deleteObjects(keyList);
        }
        log.info("--------clearCosJobHandler end-------");
    }
    
}
