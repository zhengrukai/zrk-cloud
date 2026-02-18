package cn.zrkcoder.cloud.module.infra.controller.admin.codegen.vo.table;

import cn.zrkcoder.cloud.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.zrkcoder.cloud.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * @author zrk on 2026/2/17
 */
@Schema(description = "管理后台 - 表定义分页 Request VO")
@Data
public class CodegenTablePageReqVO extends PageParam {

    @Schema(description = "表名称，模糊匹配", example = "zrk")
    private String tableName;

    @Schema(description = "表描述，模糊匹配", example = "Kyle")
    private String tableComment;

    @Schema(description = "实体，模糊匹配", example = "zrk")
    private String className;

    @Schema(description = "创建时间", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
