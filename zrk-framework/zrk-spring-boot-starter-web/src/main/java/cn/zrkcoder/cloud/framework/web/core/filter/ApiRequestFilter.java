package cn.zrkcoder.cloud.framework.web.core.filter;

import cn.hutool.core.util.StrUtil;
import cn.zrkcoder.cloud.framework.web.config.WebProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 过滤 /admin-api、/app-api 等 API 请求的过滤器
 *
 * @author zrk on 2026/2/11
 */
@RequiredArgsConstructor
public abstract class ApiRequestFilter extends OncePerRequestFilter {

    protected final WebProperties webProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 只过滤 API 请求的地址
        String apiUri = request.getRequestURI().substring(request.getContextPath().length());
        // 使用 StrUtil.startWithAny 判断 URI 是否以配置的 API 前缀开头
        // 若不匹配，则返回 true，表示跳过过滤；否则执行过滤逻辑。
        return !StrUtil.startWithAny(apiUri, webProperties.getAdminApi().getPrefix(), webProperties.getAppApi().getPrefix());
    }
}
