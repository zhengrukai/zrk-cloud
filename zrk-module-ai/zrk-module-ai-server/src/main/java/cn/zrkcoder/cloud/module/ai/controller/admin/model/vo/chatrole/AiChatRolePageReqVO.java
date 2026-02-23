package cn.zrkcoder.cloud.module.ai.controller.admin.model.vo.chatrole;

import cn.zrkcoder.cloud.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zrk on 2026/2/23
 */
@Schema(description = "管理后台 - AI 聊天角色分页 Request VO")
@Data
public class AiChatRolePageReqVO extends PageParam {

    @Schema(description = "角色名称", example = "李四")
    private String name;

    @Schema(description = "角色类别", example = "创作")
    private String category;

    @Schema(description = "是否公开", example = "1")
    private Boolean publicStatus;

}
