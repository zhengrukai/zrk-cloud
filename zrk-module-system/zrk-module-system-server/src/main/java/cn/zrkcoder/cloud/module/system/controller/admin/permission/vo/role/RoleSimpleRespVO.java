package cn.zrkcoder.cloud.module.system.controller.admin.permission.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zrk on 2026/2/20
 */
@Schema(description = "管理后台 - 角色精简信息 Response VO")
@Data
public class RoleSimpleRespVO {

    @Schema(description = "角色编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "zrk")
    private String name;

}
