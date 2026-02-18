package cn.zrkcoder.cloud.module.infra.enums.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zrk on 2026/2/18
 */
@Getter
@AllArgsConstructor
public enum ConfigTypeEnum {

    /**
     * 系统配置
     */
    SYSTEM(1),
    /**
     * 自定义配置
     */
    CUSTOM(2);

    private final Integer type;

}
