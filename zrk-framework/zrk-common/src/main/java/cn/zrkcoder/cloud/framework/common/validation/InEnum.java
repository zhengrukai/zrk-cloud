package cn.zrkcoder.cloud.framework.common.validation;

import cn.zrkcoder.cloud.framework.common.core.ArrayValuable;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 自定义校验注解：校验值（单个/集合）必须在指定枚举的有效值范围内
 * 
 * @author zrk on 2026/2/9
 */
// 元注解1：指定注解能标注在哪些位置（方法、字段、参数、构造器等）
@Target({
        ElementType.METHOD,
        ElementType.FIELD, // 最常用：标注在实体类字段上
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, // 标注在方法参数上
        ElementType.TYPE_USE
})
// 元注解2：注解的保留策略（RUNTIME = 运行时保留，能被反射读取，校验必须用这个）
@Retention(RetentionPolicy.RUNTIME)
// 元注解3：生成Javadoc时包含该注解
@Documented
// 元注解4：核心！声明这是一个校验注解，并指定对应的校验器（支持多个）
@Constraint(
        validatedBy = {InEnumValidator.class, InEnumCollectionValidator.class}
)
public @interface InEnum { // 自定义注解的声明方式（@interface）

    /**
     * 必选属性：传入「实现了ArrayValuable接口的枚举类」，用来指定允许的取值范围
     * @return 实现 ArrayValuable 接口的类
     */
    Class<? extends ArrayValuable<?>> value();

    /**
     * 可选属性：校验失败时的提示文案，默认值包含{value}（后续会动态替换）
     */
    String message() default "必须在指定范围 {value}";

    /**
     * 必选属性（Jakarta Validation规范要求）：分组校验用，默认空
     */
    Class<?>[] groups() default {};

    /**
     * 必选属性（Jakarta Validation规范要求）：负载（传递额外信息），默认空
     */
    Class<? extends Payload>[] payload() default {};
}
