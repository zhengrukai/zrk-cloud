package cn.zrkcoder.cloud.module.erp.controller.admin.purchase.vo.supplier;

import cn.zrkcoder.cloud.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author zrk on 2026/2/24
 */
@Schema(description = "管理后台 - ERP 供应商分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ErpSupplierPageReqVO extends PageParam {

    @Schema(description = "供应商名称", example = "芋道源码")
    private String name;

    @Schema(description = "手机号码", example = "15601691300")
    private String mobile;

    @Schema(description = "联系电话", example = "18818288888")
    private String telephone;

}
