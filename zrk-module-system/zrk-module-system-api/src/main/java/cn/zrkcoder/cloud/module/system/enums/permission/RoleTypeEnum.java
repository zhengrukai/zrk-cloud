package cn.zrkcoder.cloud.module.system.enums.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zrk on 2026/2/20
 */
@Getter
@AllArgsConstructor
public enum RoleTypeEnum {

    /**
     * 内置角色
     */
    SYSTEM(1),
    /**
     * 自定义角色
     */
    CUSTOM(2);

    private final Integer type;

}
