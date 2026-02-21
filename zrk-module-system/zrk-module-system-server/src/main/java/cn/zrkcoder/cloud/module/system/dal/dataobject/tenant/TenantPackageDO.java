package cn.zrkcoder.cloud.module.system.dal.dataobject.tenant;

import cn.zrkcoder.cloud.framework.common.enums.CommonStatusEnum;
import cn.zrkcoder.cloud.framework.mybatis.core.dataobject.BaseDO;
import cn.zrkcoder.cloud.framework.tenant.core.aop.TenantIgnore;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.util.Set;

/**
 * 租户套餐 DO
 *
 * @author zrk on 2026/2/19
 */
@TableName(value = "system_tenant_package", autoResultMap = true)
@KeySequence("system_tenant_package_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder // 自动生成建造者模式的代码：链式编程
@AllArgsConstructor
@NoArgsConstructor
@TenantIgnore
public class TenantPackageDO extends BaseDO {

    /**
     * 套餐编号，自增
     */
    private Long id;
    /**
     * 套餐名，唯一
     */
    private String name;
    /**
     * 租户套餐状态
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 关联的菜单编号
     * 存储时：[1, 2, 3] → "[1,2,3]"（JSON 字符串）
     * 读取时："[1,2,3]" → [1, 2, 3]（Set<Long>）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Set<Long> menuIds;

}
