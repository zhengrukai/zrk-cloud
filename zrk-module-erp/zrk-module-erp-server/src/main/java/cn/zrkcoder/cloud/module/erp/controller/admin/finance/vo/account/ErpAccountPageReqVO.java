package cn.zrkcoder.cloud.module.erp.controller.admin.finance.vo.account;

import cn.zrkcoder.cloud.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author zrk on 2026/2/25
 */
@Schema(description = "管理后台 - ERP 结算账户分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ErpAccountPageReqVO extends PageParam {

    @Schema(description = "账户编码", example = "A88")
    private String no;

    @Schema(description = "账户名称", example = "张三")
    private String name;

    @Schema(description = "备注", example = "随便")
    private String remark;

}
