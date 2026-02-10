package cn.zrkcoder.cloud.framework.redis.config;

import cn.hutool.core.util.StrUtil;
import cn.zrkcoder.cloud.framework.redis.core.TimeoutRedisCacheManager;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.BatchStrategies;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static cn.zrkcoder.cloud.framework.redis.config.ZrkRedisAutoConfiguration.buildRedisSerializer;

/**
 * Cache 配置类，基于 Redis 实现
 *
 * @author zrk on 2026/2/10
 */
@AutoConfiguration
@EnableConfigurationProperties({CacheProperties.class, ZrkCacheProperties.class})
@EnableCaching
public class ZrkCacheAutoConfiguration {

    /**
     * RedisCacheConfiguration Bean
     * <p>
     * 参考 org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration 的 createConfiguration 方法
     */
    @Bean
    @Primary
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        // 设置使用 : 单冒号，而不是双 :: 冒号，避免 Redis Desktop Manager 多余空格
        // 详细可见 https://blog.csdn.net/chuixue24/article/details/103928965 博客
        // 再次修复单冒号，而不是双 :: 冒号问题，Issues 详情：https://gitee.com/zhijiantianya/yudao-cloud/issues/I86VY2
        config = config.computePrefixWith(cacheName -> {
            String keyPrefix = cacheProperties.getRedis().getKeyPrefix();
            if (StringUtils.hasText(keyPrefix)) {
                keyPrefix = keyPrefix.lastIndexOf(StrUtil.COLON) == -1 ? keyPrefix + StrUtil.COLON : keyPrefix;
                return keyPrefix + cacheName + StrUtil.COLON;
            }
            return cacheName + StrUtil.COLON;
        });
        // 设置使用 JSON 序列化方式
        config = config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(buildRedisSerializer()));

        // 设置 CacheProperties.Redis 的属性
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }

    /**
     * 创建并配置 RedisCacheManager 实例。
     * <p>
     * 该方法用于初始化一个 RedisCacheManager，它基于 RedisTemplate 和 RedisCacheConfiguration
     * 来管理缓存。通过 RedisCacheWriter 实现非锁定的缓存写入操作，并结合自定义的超时缓存管理器
     * 来支持租户级别的缓存控制。
     *
     * @param redisTemplate Redis 模板实例，用于与 Redis 进行交互。
     * @param redisCacheConfiguration Redis 缓存配置，定义了缓存的行为和策略。
     * @param zrkCacheProperties 自定义缓存属性配置，包含 Redis 扫描批次大小等参数。
     * @return 配置完成的 RedisCacheManager 实例。
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate<String, Object> redisTemplate,
                                               RedisCacheConfiguration redisCacheConfiguration,
                                               ZrkCacheProperties zrkCacheProperties) {
        // 创建 RedisCacheWriter 对象
        RedisConnectionFactory connectionFactory = Objects.requireNonNull(redisTemplate.getConnectionFactory());
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory,
                BatchStrategies.scan(zrkCacheProperties.getRedisScanBatchSize()));
        // 创建 TenantRedisCacheManager 对象
        return new TimeoutRedisCacheManager(cacheWriter, redisCacheConfiguration);
    }
}
