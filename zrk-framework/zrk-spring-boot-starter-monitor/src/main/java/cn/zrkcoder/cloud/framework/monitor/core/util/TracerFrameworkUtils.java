package cn.zrkcoder.cloud.framework.monitor.core.util;

import io.opentracing.Span;
import io.opentracing.tag.Tags;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 链路追踪 工具类
 *
 * @author zrk on 2026/2/11
 */
public class TracerFrameworkUtils {

    /**
     * 将异常记录到 Span 中，参考自 com.aliyuncs.utils.TraceUtils
     *
     * @param throwable 异常
     * @param span Span
     */
    public static void onError(Throwable throwable, Span span) {
        Tags.ERROR.set(span, Boolean.TRUE);
        if (throwable != null) {
            span.log(errorLogs(throwable));
        }
    }

    // 将异常对象转换为包含详细信息的日志 Map
    private static Map<String, Object> errorLogs(Throwable throwable) {
        Map<String, Object> errorLogs = new HashMap<>(10);
        errorLogs.put("event", Tags.ERROR.getKey()); // 固定值 "error"，表示事件类型。
        errorLogs.put("error.object", throwable); // 异常对象本身
        errorLogs.put("error.kind", throwable.getClass().getName()); // 异常的类名（如 java.lang.NullPointerException）。
        String message = throwable.getCause() != null ? throwable.getCause().getMessage() : throwable.getMessage();
        if (message != null) {
            errorLogs.put("message", message); // 异常的消息内容（优先取 cause 的消息，否则取主异常消息）
        }
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        errorLogs.put("stack", sw.toString()); // 完整的异常堆栈信息。
        return errorLogs;
    }
}
