package cn.zrkcoder.cloud.module.system.api.social.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 小程序订单通知确认收获
 *
 * @author zrk on 2026/2/21
 */
@Data
public class SocialWxaOrderNotifyConfirmReceiveReqDTO {

    /**
     * 原支付交易对应的微信订单号
     */
    @NotEmpty(message = "原支付交易对应的微信订单号不能为空")
    private String transactionId;

    /**
     * 快递签收时间
     */
    @NotNull(message = "快递签收时间不能为空")
    private LocalDateTime receivedTime;

}
