package cn.zrkcoder.cloud.erp.framework.rpc.config;

import cn.zrkcoder.cloud.module.system.api.user.AdminUserApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author zrk on 2026/2/24
 */
@Configuration(value = "erpRpcConfiguration", proxyBeanMethods = false)
@EnableFeignClients(clients = AdminUserApi.class)
public class RpcConfiguration {
}
