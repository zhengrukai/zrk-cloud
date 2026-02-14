package cn.zrkcoder.cloud.framework.tenant.config;

import cn.zrkcoder.cloud.framework.common.biz.system.tenant.TenantCommonApi;
import cn.zrkcoder.cloud.framework.tenant.core.rpc.TenantRequestInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author zrk on 2026/2/14
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "zrk.tenant", value = "enable", matchIfMissing = true) // 允许使用 zrk.tenant.enable=false 禁用多租户
@EnableFeignClients(clients = TenantCommonApi.class) // 主要是引入相关的 API 服务
public class ZrkTenantRpcAutoConfiguration {

    @Bean
    public TenantRequestInterceptor tenantRequestInterceptor() {
        return new TenantRequestInterceptor();
    }

}
