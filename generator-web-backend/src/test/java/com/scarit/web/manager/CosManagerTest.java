package com.scarit.web.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Arrays;


@SpringBootTest
class CosManagerTest {

    @Resource
    private CosManager cosManager;


    @Test
    void deleteObject() {
        cosManager.deleteObject("/test/acm-template-pro-dist.zip");
    }

    @Test
    void deleteObjects() {
        cosManager.deleteObjects(Arrays.asList("/test/favicon.ico", "test/NeatDM.exe"));
    }

    @Test
    void deleteDir() {
        cosManager.deleteDir("/test");
    }
}