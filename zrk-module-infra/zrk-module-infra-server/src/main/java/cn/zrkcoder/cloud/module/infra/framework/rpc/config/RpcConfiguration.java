package cn.zrkcoder.cloud.module.infra.framework.rpc.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author zrk on 2026/2/18
 */
@Configuration(value = "infraRpcConfiguration", proxyBeanMethods = false)
@EnableFeignClients()
public class RpcConfiguration {
}
