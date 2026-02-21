package cn.zrkcoder.cloud.module.system.framework.rpc.config;

import cn.zrkcoder.cloud.module.infra.api.config.ConfigApi;
import cn.zrkcoder.cloud.module.infra.api.file.FileApi;
import cn.zrkcoder.cloud.module.infra.api.websocket.WebSocketSenderApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author zrk on 2026/2/21
 */
@Configuration(value = "systemRpcConfiguration", proxyBeanMethods = false)
@EnableFeignClients(clients = {FileApi.class, WebSocketSenderApi.class, ConfigApi.class})
public class RpcConfiguration {
}
