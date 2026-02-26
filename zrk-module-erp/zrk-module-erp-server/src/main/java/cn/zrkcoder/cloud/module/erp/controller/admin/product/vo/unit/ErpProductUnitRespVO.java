package cn.zrkcoder.cloud.module.erp.controller.admin.product.vo.unit;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.zrkcoder.cloud.framework.excel.core.annotaions.DictFormat;
import cn.zrkcoder.cloud.framework.excel.core.convert.DictConvert;
import cn.zrkcoder.cloud.module.system.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zrk on 2026/2/25
 */
@Schema(description = "管理后台 - ERP 产品单位 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ErpProductUnitRespVO {

    @Schema(description = "单位编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31254")
    @ExcelProperty("单位编号")
    private Long id;

    @Schema(description = "单位名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("单位名字")
    private String name;

    @Schema(description = "单位状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "单位状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
