package cn.zrkcoder.cloud.framework.monitor.core.aop;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.zrkcoder.cloud.framework.common.util.spring.SpringExpressionUtils;
import cn.zrkcoder.cloud.framework.monitor.core.annotation.BizTrace;
import cn.zrkcoder.cloud.framework.monitor.core.util.TracerFrameworkUtils;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Map;

import static java.util.Arrays.asList;

/**
 * {@link BizTrace} 切面，记录业务链路
 *
 * @author zrk on 2026/2/11
 */
@Aspect
@AllArgsConstructor
@Slf4j
public class BizTraceAspect {

    private static final String BIZ_OPERATION_NAME_PREFIX = "Biz/";

    private final Tracer tracer;

    // 环绕通知，拦截目标方法的执行
    @Around(value = "@annotation(trace)")
    public Object around(ProceedingJoinPoint joinPoint, BizTrace trace) throws Throwable {
        // 创建 span
        String operationName = getOperationName(joinPoint, trace);
        Span span = tracer.buildSpan(operationName)
                .withTag(Tags.COMPONENT.getKey(), "biz")
                .start();
        try {
            // 执行原有方法
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            TracerFrameworkUtils.onError(throwable, span);
            throw throwable;
        } finally {
            // 设置 Span 的 biz 属性
            setBizTag(span, joinPoint, trace);
            // 完成 Span
            span.finish();
        }
    }

    // 生成 Span 的操作名。
    private String getOperationName(ProceedingJoinPoint joinPoint, BizTrace trace) {
        // 自定义操作名
        if (StrUtil.isNotEmpty(trace.operationName())) {
            return BIZ_OPERATION_NAME_PREFIX + trace.operationName();
        }
        // 默认操作名，使用方法名
        return BIZ_OPERATION_NAME_PREFIX
                + joinPoint.getSignature().getDeclaringType().getSimpleName()
                + "/" + joinPoint.getSignature().getName();
    }

    // 解析并设置业务标签
    private void setBizTag(Span span, ProceedingJoinPoint joinPoint, BizTrace trace) {
        try {
            Map<String, Object> result = SpringExpressionUtils.parseExpressions(joinPoint, asList(trace.type(), trace.id()));
            span.setTag(BizTrace.TYPE_TAG, MapUtil.getStr(result, trace.type()));
            span.setTag(BizTrace.ID_TAG, MapUtil.getStr(result, trace.id()));
        } catch (Exception ex) {
            log.error("[setBizTag][解析 bizType 与 bizId 发生异常]", ex);
        }
    }

}
