package cn.zrkcoder.cloud.module.system.api.dict;

import cn.zrkcoder.cloud.framework.common.biz.system.dict.DictDataCommonApi;
import cn.zrkcoder.cloud.framework.common.pojo.CommonResult;
import cn.zrkcoder.cloud.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

/**
 * @author zrk on 2026/2/21
 */
@FeignClient(name = ApiConstants.NAME) // TODO 降级 fallbackFactory =
@Tag(name = "RPC 服务 - 字典数据")
public interface DictDataApi extends DictDataCommonApi {

    String PREFIX = ApiConstants.PREFIX + "/dict-data";

    @GetMapping(PREFIX + "/valid")
    @Operation(summary = "校验字典数据们是否有效")
    @Parameters({
            @Parameter(name = "dictType", description = "字典类型", example = "SEX", required = true),
            @Parameter(name = "descriptions", description = "字典数据值的数组", example = "1,2", required = true)
    })
    CommonResult<Boolean> validateDictDataList(@RequestParam("dictType") String dictType,
                                               @RequestParam("values") Collection<String> values);

}
