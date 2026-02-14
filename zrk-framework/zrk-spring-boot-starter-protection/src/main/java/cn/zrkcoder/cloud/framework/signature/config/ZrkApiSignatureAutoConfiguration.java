package cn.zrkcoder.cloud.framework.signature.config;

import cn.zrkcoder.cloud.framework.redis.config.ZrkRedisAutoConfiguration;
import cn.zrkcoder.cloud.framework.signature.core.aop.ApiSignatureAspect;
import cn.zrkcoder.cloud.framework.signature.core.redis.ApiSignatureRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * HTTP API 签名的自动配置类
 *
 * @author zrk on 2026/2/14
 */
@AutoConfiguration(after = ZrkRedisAutoConfiguration.class)
public class ZrkApiSignatureAutoConfiguration {

    @Bean
    public ApiSignatureRedisDAO signatureRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new ApiSignatureRedisDAO(stringRedisTemplate);
    }

    @Bean
    public ApiSignatureAspect signatureAspect(ApiSignatureRedisDAO signatureRedisDAO) {
        return new ApiSignatureAspect(signatureRedisDAO);
    }

}
