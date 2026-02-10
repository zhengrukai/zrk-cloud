package cn.zrkcoder.cloud.framework.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * 自定义 Cache 配置项
 *
 * @author zrk on 2026/2/10
 */
@ConfigurationProperties("zrk.cache")
@Data
@Validated
public class ZrkCacheProperties {

    /**
     * {@link #redisScanBatchSize} 默认值
     */
    private static final Integer REDIS_SCAN_BATCH_SIZE_DEFAULT = 30;

    /**
     * redis scan 一次返回数量
     */
    private Integer redisScanBatchSize = REDIS_SCAN_BATCH_SIZE_DEFAULT;
}
