package cn.zrkcoder.cloud.module.erp.controller.admin.sale.vo.customer;

import cn.zrkcoder.cloud.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author zrk on 2026/2/26
 */
@Schema(description = "管理后台 - ERP 客户分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ErpCustomerPageReqVO extends PageParam {

    @Schema(description = "客户名称", example = "张三")
    private String name;

    @Schema(description = "手机号码", example = "15601691300")
    private String mobile;

    @Schema(description = "联系电话", example = "15601691300")
    private String telephone;

}
