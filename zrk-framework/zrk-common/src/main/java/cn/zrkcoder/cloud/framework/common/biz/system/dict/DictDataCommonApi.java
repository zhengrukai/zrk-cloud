package cn.zrkcoder.cloud.framework.common.biz.system.dict;

import cn.zrkcoder.cloud.framework.common.biz.system.dict.dto.DictDataRespDTO;
import cn.zrkcoder.cloud.framework.common.enums.RpcConstants;
import cn.zrkcoder.cloud.framework.common.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zrk on 2026/2/14
 */
@FeignClient(name = RpcConstants.SYSTEM_NAME, primary = false) // TODO 降级 fallbackFactory =
@Tag(name = "RPC 服务 - 字典数据")
public interface DictDataCommonApi {

    String PREFIX = RpcConstants.SYSTEM_PREFIX + "/dict-data";

    @GetMapping(PREFIX + "/list")
    @Operation(summary = "获得指定字典类型的字典数据列表")
    @Parameter(name = "dictType", description = "字典类型", example = "SEX", required = true)
    CommonResult<List<DictDataRespDTO>> getDictDataList(@RequestParam("dictType") String dictType);

}
