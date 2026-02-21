package cn.zrkcoder.cloud.module.system.controller.app.tenant.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zrk on 2026/2/21
 */
@Schema(description = "用户 App - 租户 Response VO")
@Data
public class AppTenantRespVO {

    @Schema(description = "租户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "租户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "zrk")
    private String name;

}
