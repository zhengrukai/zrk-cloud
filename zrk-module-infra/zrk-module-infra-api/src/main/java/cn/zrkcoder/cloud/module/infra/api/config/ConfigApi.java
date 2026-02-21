package cn.zrkcoder.cloud.module.infra.api.config;

import cn.zrkcoder.cloud.framework.common.pojo.CommonResult;
import cn.zrkcoder.cloud.module.infra.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zrk on 2026/2/19
 */
@FeignClient(name = ApiConstants.NAME) // TODO 降级 fallbackFactory =
@Tag(name = "RPC 服务 - 参数配置")
public interface ConfigApi {

    String PREFIX = ApiConstants.PREFIX + "/config";

    @GetMapping(PREFIX + "/get-value-by-key")
    @Operation(summary = "根据参数键查询参数值")
    CommonResult<String> getConfigValueByKey(@RequestParam("key") String key);

}
