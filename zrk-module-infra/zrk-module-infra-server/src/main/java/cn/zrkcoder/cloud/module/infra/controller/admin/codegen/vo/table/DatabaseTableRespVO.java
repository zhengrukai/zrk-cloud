package cn.zrkcoder.cloud.module.infra.controller.admin.codegen.vo.table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zrk on 2026/2/17
 */
@Schema(description = "管理后台 - 数据库的表定义 Response VO")
@Data
public class DatabaseTableRespVO {

    @Schema(description = "表名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "yuanma")
    private String name;

    @Schema(description = "表描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "Kyle")
    private String comment;

}
