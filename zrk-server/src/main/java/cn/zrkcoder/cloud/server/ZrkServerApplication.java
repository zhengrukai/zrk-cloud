package cn.zrkcoder.cloud.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zrk on 2026/2/21
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${zrk.info.base-package}
@SpringBootApplication(scanBasePackages = {"${zrk.info.base-package}.server", "${zrk.info.base-package}.module"},
        excludeName = {
                // RPC 相关
//            "org.springframework.cloud.openfeign.FeignAutoConfiguration",
//            "cn.zrkcoder.cloud.module.system.framework.rpc.config.RpcConfiguration"
        })
public class ZrkServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ZrkServerApplication.class, args);
//        new SpringApplicationBuilder(ZrlServerApplication.class)
//                .applicationStartup(new BufferingApplicationStartup(20480))
//                .run(args);

    }

}
