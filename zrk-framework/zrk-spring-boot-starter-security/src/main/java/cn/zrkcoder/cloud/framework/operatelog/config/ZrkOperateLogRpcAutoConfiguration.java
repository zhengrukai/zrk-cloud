package cn.zrkcoder.cloud.framework.operatelog.config;

import cn.zrkcoder.cloud.framework.common.biz.system.logger.OperateLogCommonApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * OperateLog 使用到 Feign 的配置项
 *
 * @author zrk on 2026/2/12
 */
@AutoConfiguration
@EnableFeignClients(clients = {OperateLogCommonApi.class}) // 主要是引入相关的 API 服务
public class ZrkOperateLogRpcAutoConfiguration {
}
