package cn.zrkcoder.cloud.framework.apilog.core.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.zrkcoder.cloud.framework.apilog.core.annotation.ApiAccessLog;
import cn.zrkcoder.cloud.framework.apilog.core.enums.OperateTypeEnum;
import cn.zrkcoder.cloud.framework.common.biz.infra.logger.ApiAccessLogCommonApi;
import cn.zrkcoder.cloud.framework.common.biz.infra.logger.dto.ApiAccessLogCreateReqDTO;
import cn.zrkcoder.cloud.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.zrkcoder.cloud.framework.common.pojo.CommonResult;
import cn.zrkcoder.cloud.framework.common.util.json.JsonUtils;
import cn.zrkcoder.cloud.framework.common.util.monitor.TracerUtils;
import cn.zrkcoder.cloud.framework.common.util.servlet.ServletUtils;
import cn.zrkcoder.cloud.framework.web.config.WebProperties;
import cn.zrkcoder.cloud.framework.web.core.filter.ApiRequestFilter;
import cn.zrkcoder.cloud.framework.web.core.util.WebFrameworkUtils;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Map;

import static cn.zrkcoder.cloud.framework.apilog.core.interceptor.ApiAccessLogInterceptor.ATTRIBUTE_HANDLER_METHOD;
import static cn.zrkcoder.cloud.framework.common.util.json.JsonUtils.toJsonString;
/**
 * API 访问日志 Filter
 * 目的：记录 API 访问日志到数据库中
 *
 * @author zrk on 2026/2/11
 */
@Slf4j
public class ApiAccessLogFilter extends ApiRequestFilter {

    private static final String[] SANITIZE_KEYS = new String[]{"password", "token", "accessToken", "refreshToken"};

    private final String applicationName;

    private final ApiAccessLogCommonApi apiAccessLogApi;

    public ApiAccessLogFilter(WebProperties webProperties, String applicationName, ApiAccessLogCommonApi apiAccessLogApi) {
        super(webProperties);
        this.applicationName = applicationName;
        this.apiAccessLogApi = apiAccessLogApi;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 获得开始时间
        LocalDateTime beginTime = LocalDateTime.now();
        // 提前获得参数，避免 XssFilter 过滤处理
        Map<String, String> queryString = ServletUtils.getParamMap(request);
        String requestBody = ServletUtils.isJsonRequest(request) ? ServletUtils.getBody(request) : null;

        try {
            // 继续过滤器
            filterChain.doFilter(request, response);
            // 正常执行，记录日志
            createApiAccessLog(request, beginTime, queryString, requestBody, null);
        } catch (Exception ex) {
            // 异常执行，记录日志
            createApiAccessLog(request, beginTime, queryString, requestBody, ex);
            throw ex;
        }
    }

    // 创建 API 访问日志
    private void createApiAccessLog(HttpServletRequest request, LocalDateTime beginTime,
                                    Map<String, String> queryString, String requestBody, Exception ex) {
        ApiAccessLogCreateReqDTO accessLog = new ApiAccessLogCreateReqDTO();
        try {
            boolean enable = buildApiAccessLog(accessLog, request, beginTime, queryString, requestBody, ex);
            if (!enable) {
                return;
            }
            apiAccessLogApi.createApiAccessLogAsync(accessLog);
        } catch (Throwable th) {
            log.error("[createApiAccessLog][url({}) log({}) 发生异常]", request.getRequestURI(), toJsonString(accessLog), th);
        }
    }

    // 构建 API 访问日志
    private boolean buildApiAccessLog(ApiAccessLogCreateReqDTO accessLog, HttpServletRequest request, LocalDateTime beginTime,
                                      Map<String, String> queryString, String requestBody, Exception ex) {
        // 判断：是否要记录操作日志
        HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(ATTRIBUTE_HANDLER_METHOD);
        ApiAccessLog accessLogAnnotation = null;
        if (handlerMethod != null) {
            accessLogAnnotation = handlerMethod.getMethodAnnotation(ApiAccessLog.class);
            if (accessLogAnnotation != null && BooleanUtil.isFalse(accessLogAnnotation.enable())) {
                return false;
            }
        }

        // 处理用户信息
        accessLog.setUserId(WebFrameworkUtils.getLoginUserId(request))
                .setUserType(WebFrameworkUtils.getLoginUserType(request));
        // 设置访问结果
        CommonResult<?> result = WebFrameworkUtils.getCommonResult(request);
        if (result != null) {
            accessLog.setResultCode(result.getCode()).setResultMsg(result.getMsg());
        } else if (ex != null) {
            accessLog.setResultCode(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode())
                    .setResultMsg(ExceptionUtil.getRootCauseMessage(ex));
        } else {
            accessLog.setResultCode(GlobalErrorCodeConstants.SUCCESS.getCode()).setResultMsg("");
        }
        // 设置请求字段
        accessLog.setTraceId(TracerUtils.getTraceId()).setApplicationName(applicationName)
                .setRequestUrl(request.getRequestURI()).setRequestMethod(request.getMethod())
                .setUserAgent(ServletUtils.getUserAgent(request)).setUserIp(ServletUtils.getClientIP(request));
        String[] sanitizeKeys = accessLogAnnotation != null ? accessLogAnnotation.sanitizeKeys() : null;
        Boolean requestEnable = accessLogAnnotation != null ? accessLogAnnotation.requestEnable() : Boolean.TRUE;
        if (!BooleanUtil.isFalse(requestEnable)) { // 默认记录，所以判断 !false
            Map<String, Object> requestParams = MapUtil.<String, Object>builder()
                    .put("query", sanitizeMap(queryString, sanitizeKeys))
                    .put("body", sanitizeJson(requestBody, sanitizeKeys)).build();
            accessLog.setRequestParams(toJsonString(requestParams));
        }
        Boolean responseEnable = accessLogAnnotation != null ? accessLogAnnotation.responseEnable() : Boolean.FALSE;
        if (BooleanUtil.isTrue(responseEnable)) { // 默认不记录，默认强制要求 true
            accessLog.setResponseBody(sanitizeJson(result, sanitizeKeys));
        }
        // 持续时间
        accessLog.setBeginTime(beginTime).setEndTime(LocalDateTime.now())
                .setDuration((int) LocalDateTimeUtil.between(accessLog.getBeginTime(), accessLog.getEndTime(), ChronoUnit.MILLIS));

        // 操作模块
        if (handlerMethod != null) {
            Tag tagAnnotation = handlerMethod.getBeanType().getAnnotation(Tag.class);
            Operation operationAnnotation = handlerMethod.getMethodAnnotation(Operation.class);
            String operateModule = accessLogAnnotation != null && StrUtil.isNotBlank(accessLogAnnotation.operateModule()) ?
                    accessLogAnnotation.operateModule() :
                    tagAnnotation != null ? StrUtil.nullToDefault(tagAnnotation.name(), tagAnnotation.description()) : null;
            String operateName = accessLogAnnotation != null && StrUtil.isNotBlank(accessLogAnnotation.operateName()) ?
                    accessLogAnnotation.operateName() :
                    operationAnnotation != null ? operationAnnotation.summary() : null;
            OperateTypeEnum operateType = accessLogAnnotation != null && accessLogAnnotation.operateType().length > 0 ?
                    accessLogAnnotation.operateType()[0] : parseOperateLogType(request);
            accessLog.setOperateModule(operateModule).setOperateName(operateName).setOperateType(operateType.getType());
        }
        return true;
    }

    // ========== 请求和响应的脱敏逻辑，移除类似 password、token 等敏感字段 ==========

    /**
     * 对给定的Map进行清理并转换为JSON字符串。
     *
     * @param map 需要被处理的Map对象，键为String类型，值可以是任意类型
     * @param sanitizeKeys 可选参数，指定需要从Map中移除的键数组
     * @return 清理后的Map对应的JSON字符串；如果输入Map为空，则返回null
     */
    private static String sanitizeMap(Map<String, ?> map, String[] sanitizeKeys) {
        if (CollUtil.isEmpty(map)) {
            return null;
        }
        // 如果提供了需要清理的键数组（sanitizeKeys），则从Map中移除这些键。
        if (sanitizeKeys != null) {
            MapUtil.removeAny(map, sanitizeKeys);
        }
        // 从Map中移除预定义的敏感键（SANITIZE_KEYS）。
        MapUtil.removeAny(map, SANITIZE_KEYS);
        return JsonUtils.toJsonString(map);
    }

    /**
     * 对JSON字符串进行脱敏处理
     *
     * @param jsonString 原始JSON字符串
     * @param sanitizeKeys 需要脱敏的字段键名数组，指定哪些字段需要被脱敏。
     * @return 脱敏后的JSON字符串，若脱敏过程中发生异常则返回原始JSON字符串。
     */
    private static String sanitizeJson(String jsonString, String[] sanitizeKeys) {
        if (StrUtil.isEmpty(jsonString)) {
            return null;
        }
        try {
            JsonNode rootNode = JsonUtils.parseTree(jsonString);
            sanitizeJson(rootNode, sanitizeKeys);
            return JsonUtils.toJsonString(rootNode);
        } catch (Exception e) {
            // 脱敏失败的情况下，直接忽略异常，避免影响用户请求
            log.error("[sanitizeJson][脱敏({}) 发生异常]", jsonString, e);
            return jsonString;
        }
    }

    /**
     * 对 CommonResult 对象中的 JSON 数据进行脱敏处理
     * 仅处理 "data" 字段，避免对 "code" 和 "msg" 字段造成影响。
     *
     * @param commonResult 待脱敏的 CommonResult 对象，可能包含敏感数据
     * @param sanitizeKeys 需要脱敏的字段键名数组
     * @return 脱敏后的 JSON 字符串；如果输入为 null 或脱敏失败，则返回原始 JSON 字符串或 null
     */
    private static String sanitizeJson(CommonResult<?> commonResult, String[] sanitizeKeys) {
        if (commonResult == null) {
            return null;
        }
        String jsonString = toJsonString(commonResult);
        try {
            JsonNode rootNode = JsonUtils.parseTree(jsonString);
            sanitizeJson(rootNode.get("data"), sanitizeKeys); // 只处理 data 字段，不处理 code、msg 字段，避免错误被脱敏掉
            return JsonUtils.toJsonString(rootNode);
        } catch (Exception e) {
            // 脱敏失败的情况下，直接忽略异常，避免影响用户请求
            log.error("[sanitizeJson][脱敏({}) 发生异常]", jsonString, e);
            return jsonString;
        }
    }

    /**
     * 对JSON节点进行清理，移除指定键或默认敏感键对应的字段。
     *
     * @param node 当前处理的JSON节点，可以是对象、数组或值类型
     * @param sanitizeKeys 需要额外清理的键名数组，这些键及其值将被从JSON中移除
     */
    private static void sanitizeJson(JsonNode node, String[] sanitizeKeys) {
        // 情况一：数组，遍历处理
        if (node.isArray()) {
            for (JsonNode childNode : node) {
                sanitizeJson(childNode, sanitizeKeys);
            }
            return;
        }
        // 情况二：非 Object，只是某个值，直接返回
        if (!node.isObject()) {
            return;
        }
        //  情况三：Object，遍历处理
        Iterator<Map.Entry<String, JsonNode>> iterator = node.properties().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = iterator.next();
            if (ArrayUtil.contains(sanitizeKeys, entry.getKey())
                    || ArrayUtil.contains(SANITIZE_KEYS, entry.getKey())) {
                iterator.remove();
                continue;
            }
            sanitizeJson(entry.getValue(), sanitizeKeys);
        }
    }

    // ========== 解析 @ApiAccessLog、@Swagger 注解  ==========

    /**
     * 解析HTTP请求的方法类型，并将其映射为对应的操作类型枚举。
     */
    private static OperateTypeEnum parseOperateLogType(HttpServletRequest request) {
        RequestMethod requestMethod = RequestMethod.resolve(request.getMethod());
        if (requestMethod == null) {
            return OperateTypeEnum.OTHER;
        }
        switch (requestMethod) {
            case GET:
                return OperateTypeEnum.GET;
            case POST:
                return OperateTypeEnum.CREATE;
            case PUT:
                return OperateTypeEnum.UPDATE;
            case DELETE:
                return OperateTypeEnum.DELETE;
            default:
                return OperateTypeEnum.OTHER;
        }
    }

}
