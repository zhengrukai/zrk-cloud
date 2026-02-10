package cn.zrkcoder.cloud.framework.env.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 环境配置
 *
 * @author zrk on 2026/2/10
 */
@ConfigurationProperties(prefix = "zrk.env")
@Data
public class EnvProperties {

    public static final String TAG_KEY = "zrk.env.tag";

    /**
     * 环境标签
     */
    private String tag;
}
