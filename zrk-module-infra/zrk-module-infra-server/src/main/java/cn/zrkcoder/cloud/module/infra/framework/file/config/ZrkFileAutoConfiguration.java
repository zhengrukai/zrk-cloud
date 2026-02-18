package cn.zrkcoder.cloud.module.infra.framework.file.config;

import cn.zrkcoder.cloud.module.infra.framework.file.core.client.FileClientFactory;
import cn.zrkcoder.cloud.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author zrk on 2026/2/18
 */
@Configuration(proxyBeanMethods = false)
public class ZrkFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
