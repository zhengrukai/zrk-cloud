package cn.zrkcoder.cloud.module.system.controller.admin.dept.vo.post;

import cn.zrkcoder.cloud.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zrk on 2026/2/19
 */
@Schema(description = "管理后台 - 岗位分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class PostPageReqVO extends PageParam {

    @Schema(description = "岗位编码，模糊匹配", example = "zrk")
    private String code;

    @Schema(description = "岗位名称，模糊匹配", example = "zrk")
    private String name;

    @Schema(description = "展示状态，参见 CommonStatusEnum 枚举类", example = "1")
    private Integer status;

}
