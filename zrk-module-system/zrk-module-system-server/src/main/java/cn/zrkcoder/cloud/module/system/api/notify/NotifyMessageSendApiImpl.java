package cn.zrkcoder.cloud.module.system.api.notify;

import cn.zrkcoder.cloud.framework.common.pojo.CommonResult;
import cn.zrkcoder.cloud.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import cn.zrkcoder.cloud.module.system.service.notify.NotifySendService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import static cn.zrkcoder.cloud.framework.common.pojo.CommonResult.success;

/**
 * @author zrk on 2026/2/21
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class NotifyMessageSendApiImpl implements NotifyMessageSendApi {

    @Resource
    private NotifySendService notifySendService;

    @Override
    public CommonResult<Long> sendSingleMessageToAdmin(NotifySendSingleToUserReqDTO reqDTO) {
        return success(notifySendService.sendSingleNotifyToAdmin(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams()));
    }

    @Override
    public CommonResult<Long> sendSingleMessageToMember(NotifySendSingleToUserReqDTO reqDTO) {
        return success(notifySendService.sendSingleNotifyToMember(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams()));
    }

}
