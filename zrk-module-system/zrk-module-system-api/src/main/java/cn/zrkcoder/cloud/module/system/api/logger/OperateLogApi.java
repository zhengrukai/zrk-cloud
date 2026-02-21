package cn.zrkcoder.cloud.module.system.api.logger;

import cn.zrkcoder.cloud.framework.common.biz.system.logger.OperateLogCommonApi;
import cn.zrkcoder.cloud.framework.common.pojo.CommonResult;
import cn.zrkcoder.cloud.framework.common.pojo.PageResult;
import cn.zrkcoder.cloud.module.system.api.logger.dto.OperateLogPageReqDTO;
import cn.zrkcoder.cloud.module.system.api.logger.dto.OperateLogRespDTO;
import cn.zrkcoder.cloud.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zrk on 2026/2/21
 */
@FeignClient(name = ApiConstants.NAME) // TODO 降级 fallbackFactory =
@Tag(name = "RPC 服务 - 操作日志")
public interface OperateLogApi extends OperateLogCommonApi {

    String PREFIX = ApiConstants.PREFIX + "/operate-log";

    @GetMapping(PREFIX + "/page")
    @Operation(summary = "获取指定模块的指定数据的操作日志分页")
    CommonResult<PageResult<OperateLogRespDTO>> getOperateLogPage(@SpringQueryMap OperateLogPageReqDTO pageReqDTO);

}
