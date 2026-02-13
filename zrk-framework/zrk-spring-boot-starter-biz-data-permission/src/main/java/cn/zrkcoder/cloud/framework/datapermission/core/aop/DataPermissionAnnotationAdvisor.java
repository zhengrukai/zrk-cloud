package cn.zrkcoder.cloud.framework.datapermission.core.aop;

import cn.zrkcoder.cloud.framework.datapermission.core.annotation.DataPermission;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * {@link DataPermission} 注解的 Advisor 实现类
 *
 * @author zrk on 2026/2/13
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class DataPermissionAnnotationAdvisor extends AbstractPointcutAdvisor {

    private final Advice advice;

    // 定义了哪些类或方法需要被拦截
    private final Pointcut pointcut;

    // 通过组合切入点（Pointcut）和通知（Advice），实现对 @DataPermission 注解的拦截和处理。
    public DataPermissionAnnotationAdvisor() {
        this.advice = new DataPermissionAnnotationInterceptor();
        this.pointcut = this.buildPointcut();
    }

    // 构建切入点，决定哪些类或方法会被拦截
    protected Pointcut buildPointcut() {
        Pointcut classPointcut = new AnnotationMatchingPointcut(DataPermission.class, true);
        Pointcut methodPointcut = new AnnotationMatchingPointcut(null, DataPermission.class, true);
        // 使用 ComposablePointcut 将类级别和方法级别的切入点合并，形成联合切入点。
        return new ComposablePointcut(classPointcut).union(methodPointcut);
    }

}
