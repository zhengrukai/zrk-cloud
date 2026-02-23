package cn.zrkcoder.cloud.module.ai.framework.rpc.config;

import cn.zrkcoder.cloud.module.infra.api.file.FileApi;
import cn.zrkcoder.cloud.module.system.api.user.AdminUserApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author zrk on 2026/2/23
 */
@Configuration(value = "aiRpcConfiguration", proxyBeanMethods = false)
@EnableFeignClients(clients = {FileApi.class, AdminUserApi.class})
public class RpcConfiguration {
}
