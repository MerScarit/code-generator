package com.scarit.maker.template.model;

import com.scarit.maker.meta.Meta;
import lombok.Data;

@Data
public class TemplateMakerConfig {

    private Meta meta = new Meta();

    private String originProjectPath;

    TemplateMakerFileConfig fileConfig = new TemplateMakerFileConfig();

    TemplateMakerModelConfig modelConfig = new TemplateMakerModelConfig();

    TemplateMakerOutputConfig outputConfig = new TemplateMakerOutputConfig();

    private Long id;


}
