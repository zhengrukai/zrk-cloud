package cn.zrkcoder.cloud.framework.banner.config;

import cn.zrkcoder.cloud.framework.banner.core.BannerApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Banner 的自动配置类
 *
 * @author zrk on 2026/2/11
 */
@AutoConfiguration
public class ZrkBannerAutoConfiguration {

    @Bean
    public BannerApplicationRunner bannerApplicationRunner() {
        return new BannerApplicationRunner();
    }

}
