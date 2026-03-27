package cn.zrkcoder.cloud.module.system.job.demo;

import cn.zrkcoder.cloud.framework.tenant.core.job.TenantJob;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * @author zrk on 2026/3/27
 */
@Component
public class DemoJob {

    @XxlJob("demoJob")
    @TenantJob
    public void execute() {
        System.out.println("美滋滋");
    }
}
