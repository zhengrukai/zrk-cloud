package cn.zrkcoder.cloud.framework.monitor.core.annotation;

import java.lang.annotation.*;

/**
 * 打印业务编号 / 业务类型注解
 * 使用时，需要设置 SkyWalking OAP Server 的 application.yaml 配置文件，修改 SW_SEARCHABLE_TAG_KEYS 配置项，
 * 增加 biz.type 和 biz.id 两值，然后重启 SkyWalking OAP Server 服务器。
 *
 * @author zrk on 2026/2/11
 */
@Target({ElementType.METHOD}) // 只能应用于方法级别。
@Retention(RetentionPolicy.RUNTIME) // 表示该注解在运行时仍然可用，可以通过反射机制读取
@Inherited // 表示该注解可以被子类继承（仅对类级别的注解有效，但此处作用于方法，实际无影响）。
public @interface BizTrace {

    /**
     * 业务编号 tag 名
     */
    String ID_TAG = "biz.id";
    /**
     * 业务类型 tag 名
     */
    String TYPE_TAG = "biz.type";

    /**
     * @return 操作名
     */
    String operationName() default "";

    /**
     * @return 业务编号
     */
    String id();

    /**
     * @return 业务类型
     */
    String type();

}
