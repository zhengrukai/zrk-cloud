package cn.zrkcoder.cloud.module.system.api.logger;

import cn.zrkcoder.cloud.framework.common.pojo.CommonResult;
import cn.zrkcoder.cloud.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.zrkcoder.cloud.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zrk on 2026/2/21
 */
@FeignClient(name = ApiConstants.NAME) // TODO 降级 fallbackFactory =
@Tag(name = "RPC 服务 - 登录日志")
public interface LoginLogApi {

    String PREFIX = ApiConstants.PREFIX + "/login-log";

    @PostMapping(PREFIX + "/create")
    @Operation(summary = "创建登录日志")
    CommonResult<Boolean> createLoginLog(@Valid @RequestBody LoginLogCreateReqDTO reqDTO);

}
