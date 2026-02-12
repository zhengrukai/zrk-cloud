package cn.zrkcoder.cloud.framework.security.core.handler;

import cn.zrkcoder.cloud.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.zrkcoder.cloud.framework.common.pojo.CommonResult;
import cn.zrkcoder.cloud.framework.common.util.servlet.ServletUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import static cn.zrkcoder.cloud.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;

/**
 * 访问一个需要认证的 URL 资源，但是此时自己尚未认证（登录）的情况下，
 * 返回 {@link GlobalErrorCodeConstants#UNAUTHORIZED} 错误码，从而使前端重定向到登录页
 * <p>
 * 补充：Spring Security 通过 {@link ExceptionTranslationFilter#sendStartAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, AuthenticationException)} 方法，调用当前类
 *
 * @author zrk on 2026/2/12
 */
@Slf4j
@SuppressWarnings("JavadocReference") // 忽略文档引用报错
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    /**
     * 当用户未认证时触发该方法，用于处理未登录情况下的请求。
     *
     * @param request  HttpServletRequest对象，表示客户端的HTTP请求
     * @param response HttpServletResponse对象，用于向客户端发送响应
     * @param e        AuthenticationException异常，表示认证失败的原因
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        log.debug("[commence][访问 URL({}) 时，没有登录]", request.getRequestURI(), e);
        // 返回 401
        ServletUtils.writeJSON(response, CommonResult.error(UNAUTHORIZED));
    }

}
