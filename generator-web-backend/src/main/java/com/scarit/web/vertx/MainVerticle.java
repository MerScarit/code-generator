package com.scarit.web.vertx;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scarit.web.common.ResultUtils;
import com.scarit.web.controller.GeneratorController;
import com.scarit.web.manager.CacheManager;
import com.scarit.web.model.dto.generator.GeneratorQueryRequest;
import com.scarit.web.model.vo.GeneratorVO;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;

import javax.annotation.Resource;

public class MainVerticle extends AbstractVerticle {


    @Resource
    CacheManager cacheManager;

    public MainVerticle(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void start() throws Exception {



        // Create the HTTP server
        vertx.createHttpServer()
                // Handle every request using the router
                .requestHandler(req ->{
                    HttpMethod method = req.method();
                    String path = req.path();
                    if (HttpMethod.POST.equals(method) && "/generator/page".equals(path)) {
                        req.handler(buffer -> {
                            String requestBody = buffer.toString();

                            GeneratorQueryRequest generatorQueryRequest = JSONUtil.toBean(requestBody, GeneratorQueryRequest.class);
                            String pageCacheKey = GeneratorController.getPageCacheKey(generatorQueryRequest);

                            HttpServerResponse response = req.response();
                            response.putHeader("Content-type", "application/json");

                            Object cacheValue = cacheManager.get(pageCacheKey);
                            if (cacheValue != null) {
                                response.end(JSONUtil.toJsonStr(ResultUtils.success((Page<GeneratorVO>) cacheValue)));
                                return;
                            }

                            response.end("");
                        });
                    }
                })
                // Start listening
                .listen(8888)
                // Print the port
                .onSuccess(server ->
                        System.out.println(
                                "HTTP server started on port " + server.actualPort()
                        )
                );
    }
}