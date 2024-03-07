package ${basePackage}.cli.command;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import ${basePackage}.generator.MainGenerator;
import ${basePackage}.model.DataModel;
import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;


@Command(name = "json-generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class JsonGenerateCommand implements Callable<Integer> {

        @Option(names = {"-f", "--file"},description ="读取文件路径" , interactive = true, arity = "0..1", echo = true)
        private String filePath;
        /**
         * 核心模版
         */
        static DataModel.MainTeplate mainTemplate = new DataModel.MainTeplate();

        @Override
        public Integer call() throws Exception {

            String jsonStr = FileUtil.readUtf8String(filePath);
            DataModel dataModel = JSONUtil.toBean(jsonStr, DataModel.class);
            MainGenerator.doGenerator(dataModel);
            return 0;
    }
}
