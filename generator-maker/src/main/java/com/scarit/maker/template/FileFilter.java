package com.scarit.maker.template;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.scarit.maker.template.enums.FileFilterRangeEnum;
import com.scarit.maker.template.enums.FileFilterRuleEnum;
import com.scarit.maker.template.model.FileFilterConfig;
import com.scarit.maker.template.model.TemplateMakerFileConfig;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class FileFilter {

    /**
     * 实现对文件夹、多个文件的过滤
     * @param filePath
     * @param fileFilterConfigList
     * @return
     */

    public static List<File> doFilter(String filePath, List<FileFilterConfig> fileFilterConfigList) {

        //获取该路径的所有文件
        List<File> fileList = FileUtil.loopFiles(filePath);
        return fileList.stream().filter(file ->
                doSingleFilter(fileFilterConfigList, file))
                .collect(Collectors.toList());
    }

    /**
     * 单个文件过滤
     * @param fileFilterConfigList
     * @param file
     * @return
     */
    public static boolean doSingleFilter(List<FileFilterConfig> fileFilterConfigList, File file) {

        // 实现思路是先获取文件的文件名以及文件的过滤规则，并按照规则进行校验，如果有一个过滤配置不满足，就返回 false 表示不保留该文件，反之为 true 表示通过所有校验。
        String fileName = file.getName();
        String fileContent = FileUtil.readUtf8String(file);

        // 所有过滤器教研结束的结果
        boolean result = true;

        // 过滤规则为空，则直接返回 true
        if (CollUtil.isEmpty(fileFilterConfigList)) {
            return true;
        }

        //遍历传入的文件过滤配置列表
        for (FileFilterConfig fileFilterConfig : fileFilterConfigList) {
            String range = fileFilterConfig.getRange();
            String rule = fileFilterConfig.getRule();
            String value = fileFilterConfig.getValue();

            // 获取过滤范围
            FileFilterRangeEnum fileFilterRangeEnum = FileFilterRangeEnum.getEnumByValue(range);
            // 过滤范围为空，则跳出范围过滤
            if (fileFilterRangeEnum == null) {
                continue;
            }
            // 要过滤的原内容
            String content = fileName;
            switch (fileFilterRangeEnum) {
                case FILE_NAME:
                    content = fileName;
                    break;
                case FILE_CONTENT:
                    content = fileContent;
                    break;
                default:
            }

            //获取过滤规则
            FileFilterRuleEnum fileFilterRuleEnum = FileFilterRuleEnum.getEnumByValue(rule);
            if (fileFilterRuleEnum == null) {
                continue;
            }

            // 要过滤的原内容
            switch (fileFilterRuleEnum) {
                case START_WITH:
                    result = content.startsWith(value);
                    break;
                case END_WITH:
                    result = content.endsWith(value);
                    break;
                case EQUALS:
                    result = content.equals(value);
                    break;
                case CONTAIN:
                    result = content.contains(value);
                    break;
                case REGEX:
                    result = content.matches(value);
                    break;
                default:
            }
            // 有一个规则范围不满足，直接返回false
            if (!result) {
                return false;
            }
        }

        // 全部都满足才返回true
        return true;
    }
}
