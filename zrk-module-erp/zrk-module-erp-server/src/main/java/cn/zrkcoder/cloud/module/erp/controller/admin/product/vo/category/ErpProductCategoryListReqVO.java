package cn.zrkcoder.cloud.module.erp.controller.admin.product.vo.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zrk on 2026/2/25
 */
@Schema(description = "管理后台 - ERP 产品分类列表 Request VO")
@Data
public class ErpProductCategoryListReqVO {

    @Schema(description = "分类名称", example = "zrk")
    private String name;

    @Schema(description = "开启状态", example = "1")
    private Integer status;

}
