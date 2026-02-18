package cn.zrkcoder.cloud.module.infra.framework.codegen.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zrk on 2026/2/17
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CodegenProperties.class)
public class CodegenConfiguration {
}
