package cn.zrkcoder.cloud.module.infra.framework.file.core.client;

import cn.zrkcoder.cloud.module.infra.framework.file.core.enums.FileStorageEnum;

/**
 * @author zrk on 2026/2/18
 */
public interface FileClientFactory {

    /**
     * 获得文件客户端
     *
     * @param configId 配置编号
     * @return 文件客户端
     */
    FileClient getFileClient(Long configId);

    /**
     * 创建文件客户端
     *
     * @param configId 配置编号
     * @param storage 存储器的枚举 {@link FileStorageEnum}
     * @param config 文件配置
     */
    <Config extends FileClientConfig> void createOrUpdateFileClient(Long configId, Integer storage, Config config);


}
