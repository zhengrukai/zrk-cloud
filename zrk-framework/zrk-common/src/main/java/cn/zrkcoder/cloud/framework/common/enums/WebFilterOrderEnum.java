package cn.zrkcoder.cloud.framework.common.enums;

/**
 * Web 过滤器顺序的枚举类，保证过滤器按照符合我们的预期
 * 虑到每个 starter 都需要用到该工具类，所以放到 common 模块下的 enum 包下
 *
 * @author zrk on 2026/2/8
 */
public interface WebFilterOrderEnum {

    // 最先执行的过滤器（数值极小，优先级最高）
    /**
     * 跨域过滤器（CORS），必须最先执行—— 因为跨域预检请求（OPTIONS）需要先通过 CORS 校验，才能进入后续过滤器；
     */
    int CORS_FILTER = Integer.MIN_VALUE;

    /**
     * 链路追踪过滤器（如 SkyWalking、Zipkin），在跨域后立即记录请求追踪信息；
     */
    int TRACE_FILTER = CORS_FILTER + 1;

    /**
     * 环境标签过滤器（如标记请求所属环境：开发 / 测试 / 生产），早于业务逻辑执行；
     */
    int ENV_TAG_FILTER = TRACE_FILTER + 1;

    /**
     * 请求体缓存过滤器 —— 核心作用是缓存 HttpServletRequest 的请求体（默认请求体只能读取一次），
     * 为后续需要读取请求体的过滤器（如 XSS、API 加密）提供支持
     */
    int REQUEST_BODY_CACHE_FILTER = Integer.MIN_VALUE + 500;

    /**
     * API 加解密过滤器 —— 解密前端传入的加密请求体（依赖请求体缓存过滤器，所以顺序在其后）；
     */
    int API_ENCRYPT_FILTER = REQUEST_BODY_CACHE_FILTER + 1;

    // 中间执行的过滤器（适配 Spring 内置过滤器顺序）
    // OrderedRequestContextFilter 默认为 -105，用于国际化上下文等等

    /**
     * 租户上下文过滤器（多租户系统）
     * 需要保证在 ApiAccessLogFilter 前执行
     * 先初始化租户信息，再记录访问日志；
     */
    int TENANT_CONTEXT_FILTER = - 104;

    /**
     * API 访问日志过滤器 —— 记录请求 URL、参数、耗时等
     * 需要保证在 RequestBodyCacheFilter 后执行（因为需要读取请求体记录日志）
     */
    int API_ACCESS_LOG_FILTER = -103;

    /**
     * XSS 防护过滤器 —— 清洗请求体中的 XSS 恶意脚本
     * 需要保证在 RequestBodyCacheFilter 后执行
     */
    int XSS_FILTER = -102;

    // Spring Security Filter 默认为 -100，可见 org.springframework.boot.autoconfigure.security.SecurityProperties 配置属性类
    /**
     * 租户权限过滤器
     * 需要保证在 Spring Security 过滤器后面
     */
    int TENANT_SECURITY_FILTER = -99;

    /**
     * 工作流（Flowable）过滤器
     * 需要保证在 Spring Security 过滤后面
     */
    int FLOWABLE_FILTER = -98;

    /**
     * 演示环境过滤器（如演示环境屏蔽敏感操作）—— 最后执行，不影响核心业务过滤器逻辑；
     */
    int DEMO_FILTER = Integer.MAX_VALUE;

}
