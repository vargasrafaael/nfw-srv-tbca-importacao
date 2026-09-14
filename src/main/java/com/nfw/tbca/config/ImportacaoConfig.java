package com.nfw.tbca.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TbcaImportacaoProperties.class)
public class ImportacaoConfig {
}
