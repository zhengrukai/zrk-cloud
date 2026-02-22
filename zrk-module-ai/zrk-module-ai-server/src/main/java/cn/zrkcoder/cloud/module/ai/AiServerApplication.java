package cn.zrkcoder.cloud.module.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zrk on 2026/2/22
 */
@SpringBootApplication(exclude = {
        org.springframework.ai.vectorstore.qdrant.autoconfigure.QdrantVectorStoreAutoConfiguration.class,
        org.springframework.ai.vectorstore.milvus.autoconfigure.MilvusVectorStoreAutoConfiguration.class,
}) // 解决 application-${profile}.yaml 配置文件下，通过 spring.autoconfigure.exclude 无法排除的问题
public class AiServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(AiServerApplication.class, args);

    }

}
