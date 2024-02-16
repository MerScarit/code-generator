package com.scarit.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.nio.charset.StandardCharsets;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DynamicGenterator {

    public static void toDynamicGenterate(String inputPath, String OutputPath,Object dataModel) throws IOException, TemplateException {
        
        
        //创建freemarker配置对象，并指定版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        //指定模板文件所在路径
        File templateDir = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);
        
        //设置模板文件指定的字符集
        configuration.setDefaultEncoding("utf-8");

        //解决freemarker输出奇怪的数据格式
        configuration.setNumberFormat("0.######");

        //创建模板对象，加载指定模版
        String templateName = new File(inputPath).getName();
//        Template template = configuration.getTemplate(templateName);


        //创建数据模型，从main方法传输，后续通过命令行输入
        
        
        //指定生成文件路径,以及解决中文乱码问题
        Template template = configuration.getTemplate(templateName, "utf-8");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(OutputPath)), StandardCharsets.UTF_8));
        //生成文件
        template.process(dataModel, out);
        //生成文件后关闭流
        out.close();

    }
}
