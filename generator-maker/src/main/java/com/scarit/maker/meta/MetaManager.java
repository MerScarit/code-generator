package com.scarit.maker.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

public class MetaManager {
    
    public static volatile Meta meta;

    //私有无参构造器，防止外部实例化
    private MetaManager() {
        
    }

    /**
     * 使用双检锁，保证程序运行过程中只会有一个Meta对象
     */
    public static Meta getMetaObject() {
        if (meta == null) {
            synchronized (MetaManager.class) {
                if (meta == null) {
                    meta = metaInit();
                }
            }
        }
        return meta;
    }
    

    /**
     * meta数据初始化
     * @return Meta
     */
    private static Meta metaInit() {
        String metaJson = ResourceUtil.readUtf8Str("Meta.Json");
        Meta newMeta = JSONUtil.toBean(metaJson, Meta.class);
        Meta.FileConfig fileConfig = newMeta.getFileConfig();
        //todo 校验和处理默认值
        return newMeta;
    }
}
