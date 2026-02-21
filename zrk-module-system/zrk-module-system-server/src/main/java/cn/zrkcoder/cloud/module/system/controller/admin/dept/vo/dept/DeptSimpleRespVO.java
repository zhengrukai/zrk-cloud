package cn.zrkcoder.cloud.module.system.controller.admin.dept.vo.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zrk on 2026/2/20
 */
@Schema(description = "管理后台 - 部门精简信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptSimpleRespVO {

    @Schema(description = "部门编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "zrk")
    private String name;

    @Schema(description = "父部门 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long parentId;

}
