package cn.zrkcoder.cloud.framework.apilog.config;

import cn.zrkcoder.cloud.framework.apilog.core.filter.ApiAccessLogFilter;
import cn.zrkcoder.cloud.framework.apilog.core.interceptor.ApiAccessLogInterceptor;
import cn.zrkcoder.cloud.framework.common.biz.infra.logger.ApiAccessLogCommonApi;
import cn.zrkcoder.cloud.framework.common.enums.WebFilterOrderEnum;
import cn.zrkcoder.cloud.framework.web.config.WebProperties;
import cn.zrkcoder.cloud.framework.web.config.ZrkWebAutoConfiguration;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 接口日志配置类
 *
 * @author zrk on 2026/2/11
 */
@AutoConfiguration(after = ZrkWebAutoConfiguration.class)
public class ZrkApiLogAutoConfiguration implements WebMvcConfigurer {

    /**
     * 创建 ApiAccessLogFilter Bean，记录 API 请求日志
     */
    @Bean
    @ConditionalOnProperty(prefix = "zrk.access-log", value = "enable", matchIfMissing = true) // 允许使用 zrk.access-log.enable=false 禁用访问日志
    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(WebProperties webProperties,
                                                                         @Value("${spring.application.name}") String applicationName,
                                                                         ApiAccessLogCommonApi apiAccessLogApi) {
        ApiAccessLogFilter filter = new ApiAccessLogFilter(webProperties, applicationName, apiAccessLogApi);
        return createFilterBean(filter, WebFilterOrderEnum.API_ACCESS_LOG_FILTER);
    }

    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiAccessLogInterceptor());
    }

}
