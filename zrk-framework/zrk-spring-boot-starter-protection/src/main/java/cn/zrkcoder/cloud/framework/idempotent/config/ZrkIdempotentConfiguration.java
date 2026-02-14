package cn.zrkcoder.cloud.framework.idempotent.config;

import cn.zrkcoder.cloud.framework.idempotent.core.aop.IdempotentAspect;
import cn.zrkcoder.cloud.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import cn.zrkcoder.cloud.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import cn.zrkcoder.cloud.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import cn.zrkcoder.cloud.framework.idempotent.core.keyresolver.impl.UserIdempotentKeyResolver;
import cn.zrkcoder.cloud.framework.idempotent.core.redis.IdempotentRedisDAO;
import cn.zrkcoder.cloud.framework.redis.config.ZrkRedisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @author zrk on 2026/2/14
 */
@AutoConfiguration(after = ZrkRedisAutoConfiguration.class)
public class ZrkIdempotentConfiguration {

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public UserIdempotentKeyResolver userIdempotentKeyResolver() {
        return new UserIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
