package com.scarit.cli.command;

import cn.hutool.core.util.ReflectUtil;
import com.scarit.model.MainTemplateConfig;
import picocli.CommandLine.Command;

import java.lang.reflect.Field;


@Command(name = "config", description = "查询参数信息", mixinStandardHelpOptions = true)
public class ConfigCommand implements Runnable{


    @Override
    public void run() {
        System.out.println("----输出用户输入参数信息----");
        Field[] fields = ReflectUtil.getFields(MainTemplateConfig.class);

        for (Field field : fields) {
            System.out.println("字段名字:" + field.getName());
            System.out.println("字段类型:" + field.getType());
            System.out.println("字段名字:" + field.getName());
            System.out.println("-------");
            
        }
    }
}
