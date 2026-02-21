package cn.zrkcoder.cloud.module.system.api.logger;

import cn.zrkcoder.cloud.framework.common.biz.system.logger.dto.OperateLogCreateReqDTO;
import cn.zrkcoder.cloud.framework.common.pojo.CommonResult;
import cn.zrkcoder.cloud.framework.common.pojo.PageResult;
import cn.zrkcoder.cloud.framework.common.util.object.BeanUtils;
import cn.zrkcoder.cloud.module.system.api.logger.dto.OperateLogPageReqDTO;
import cn.zrkcoder.cloud.module.system.api.logger.dto.OperateLogRespDTO;
import cn.zrkcoder.cloud.module.system.dal.dataobject.logger.OperateLogDO;
import cn.zrkcoder.cloud.module.system.service.logger.OperateLogService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import static cn.zrkcoder.cloud.framework.common.pojo.CommonResult.success;

/**
 * @author zrk on 2026/2/21
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
@Primary // 由于 OperateLogCommonApi 的存在，必须声明为 @Primary Bean
public class OperateLogApiImpl implements OperateLogApi {

    @Resource
    private OperateLogService operateLogService;

    @Override
    public CommonResult<Boolean> createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        operateLogService.createOperateLog(createReqDTO);
        return success(true);
    }

    @Override
    public CommonResult<PageResult<OperateLogRespDTO>> getOperateLogPage(OperateLogPageReqDTO pageReqDTO) {
        PageResult<OperateLogDO> operateLogPage = operateLogService.getOperateLogPage(pageReqDTO);
        return success(BeanUtils.toBean(operateLogPage, OperateLogRespDTO.class));
    }

}
