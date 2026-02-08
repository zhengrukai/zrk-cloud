package cn.zrkcoder.cloud.framework.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 排序字段 DTO
 * 类名加了 ing 的原因是，避免和 ES SortField 重名。
 *
 * @author zrk on 2026/2/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortingField {

    /**
     * 顺序 - 升序
     */
    public static final String ORDER_ASC = "asc";
    /**
     * 顺序 - 降序
     */
    public static final String ORDER_DESC = "desc";

    /**
     * 字段
     */
    private String field;
    /**
     * 顺序
     */
    private String order;
}
