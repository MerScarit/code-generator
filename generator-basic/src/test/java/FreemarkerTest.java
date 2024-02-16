import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * @author ADI
 * @description: freemarker测试类
 * @date 2024-02-11
 */
//public class FreemarkerTest {
//    @Test
//    public void test() throws IOException, TemplateException {
//        
//        //创建freemarker配置对象，并指定版本号
//        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
//
//        //指定模板文件所在路径
//        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
//
//        //设置模板文件指定的字符集
//        configuration.setDefaultEncoding("utf-8");
//
//        //解决freemarker输出奇怪的数据格式
//        configuration.setNumberFormat("0.######");
//        
//        //创建模板对象，加载指定模版
//        Template template = configuration.getTemplate("myweb.html.ftl");
//
//
//        //创建数据模型
//        Map<String, Object> dataModel = new HashMap<>();
//        dataModel.put("currentYear", 2023);
//
//        List<Map<String, Object>> menuItems = new ArrayList<>();
//
//        Map<String, Object> item1 = new HashMap<>();
//        item1.put("url", "https://baidu.com");
//        item1.put("label", "百度一下");
//
//        HashMap<String, Object> item2 = new HashMap<>();
//        item2.put("url", "https://weixin.qq.com");
//        item2.put("label", "微信官网");
//
//
//        menuItems.add(item1);
//        menuItems.add(item2);
//        dataModel.put("menuItems", menuItems);
//
//
//        //指定生成文件名字
//        Writer writer = new FileWriter("myweb.html");
//
//        //生成文件
//        template.process(dataModel, writer);
//        //生成文件后关闭流
//        writer.close();
//
//    }
//}
