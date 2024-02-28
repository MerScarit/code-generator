package com.scarit.maker;


import com.scarit.maker.generator.main.GenerateTemplate;
import com.scarit.maker.generator.main.MainGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @author ADI
 * @description: TODO
 * @date 2024-02-09
 */

public class Main {
    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {

        MainGenerator mainGenerator = new MainGenerator();
        mainGenerator.doGenerate();
    }
}
