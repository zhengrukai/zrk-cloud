package cn.zrkcoder.cloud.framework.monitor.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * 应用程序监控指标（Metrics） 配置类
 *
 * @author zrk on 2026/2/11
 */
@AutoConfiguration
@ConditionalOnClass({MeterRegistryCustomizer.class})
@ConditionalOnProperty(prefix = "zrk.metrics", value = "enable", matchIfMissing = true) // 允许使用 zrk.metrics.enable=false 禁用 Metrics
public class ZrkMetricsAutoConfiguration {

    // 定义一个 MeterRegistryCustomizer Bean，用于为所有的监控指标添加公共标签（common tags）
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(
            @Value("${spring.application.name}") String applicationName) {
        // 为所有指标添加一个名为 application 的标签，其值为当前应用的名称。这有助于在监控系统中区分不同应用的指标数据。
        return registry -> registry.config().commonTags("application", applicationName);
    }
}
