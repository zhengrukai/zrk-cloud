package cn.zrkcoder.cloud.framework.common.validation;

import cn.zrkcoder.cloud.framework.common.core.ArrayValuable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 单个值的校验器：校验单个Object类型的值是否在枚举有效值范围内
 *
 * @author zrk on 2026/2/9
 */
public class InEnumValidator implements ConstraintValidator<InEnum, Object> {

    // 存储枚举的有效值列表（初始化时赋值）
    private List<?> values;

    /**
     * 初始化方法：校验前执行，读取注解配置的枚举，提取有效值列表
     */
    @Override
    public void initialize(InEnum annotation) {
        // 1. 获取注解中指定的枚举类的所有枚举实例
        ArrayValuable<?>[] values = annotation.value().getEnumConstants();
        if (values.length == 0) {
            this.values = Collections.emptyList();
        } else {
            // 2. 调用枚举的array()方法（ArrayValuable接口的方法），获取有效值数组并转成List
            this.values = Arrays.asList(values[0].array());
        }
    }

    /**
     * 核心校验方法：判断传入的值是否合法
     * @param value 要校验的单个值（比如1、"ENABLE"）
     * @param context 校验上下文（用来自定义错误提示）
     * @return true=校验通过，false=校验失败
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 为空时，默认不校验，即认为通过
        if (value == null) {
            return true;
        }
        // 校验通过
        if (values.contains(value)) {
            return true;
        }
        // 校验不通过，自定义提示语句
        context.disableDefaultConstraintViolation(); // 禁用默认的 message 的值
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
                .replaceAll("\\{value}", values.toString())).addConstraintViolation(); // 重新添加错误提示语句
        return false;
    }
}
