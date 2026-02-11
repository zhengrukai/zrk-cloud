package cn.zrkcoder.cloud.framework.desensitize.core.annotation;

import cn.zrkcoder.cloud.framework.desensitize.core.DesensitizeTest;
import cn.zrkcoder.cloud.framework.desensitize.core.base.annotation.DesensitizeBy;
import cn.zrkcoder.cloud.framework.desensitize.core.handler.AddressHandler;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * 地址
 * 用于 {@link DesensitizeTest} 测试使用
 *
 * @author zrk on 2026/2/11
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@DesensitizeBy(handler = AddressHandler.class)
public @interface Address {

    String replacer() default "*";
}
