package com.scarit.web.service.impl;

import com.scarit.web.model.entity.Generator;
import com.scarit.web.service.GeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;



@SpringBootTest
class GeneratorServiceImplTest {


    @Resource
    GeneratorService generatorService;


    @Test
    public void testInsert() {
        Generator generator = generatorService.getById(1);
        
        for (int i = 0; i < 65000; i++) {
            generator.setId(null);

            generatorService.save(generator);
        }
    }

}