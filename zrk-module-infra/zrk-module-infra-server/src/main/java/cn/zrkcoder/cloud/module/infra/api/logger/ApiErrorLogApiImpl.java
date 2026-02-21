package cn.zrkcoder.cloud.module.infra.api.logger;

import cn.zrkcoder.cloud.framework.common.biz.infra.logger.ApiErrorLogCommonApi;
import cn.zrkcoder.cloud.framework.common.biz.infra.logger.dto.ApiErrorLogCreateReqDTO;
import cn.zrkcoder.cloud.framework.common.pojo.CommonResult;
import cn.zrkcoder.cloud.module.infra.service.logger.ApiErrorLogService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import static cn.zrkcoder.cloud.framework.common.pojo.CommonResult.success;

/**
 * @author zrk on 2026/2/19
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class ApiErrorLogApiImpl implements ApiErrorLogCommonApi {

    @Resource
    private ApiErrorLogService apiErrorLogService;

    @Override
    public CommonResult<Boolean> createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        apiErrorLogService.createApiErrorLog(createDTO);
        return success(true);
    }

}
