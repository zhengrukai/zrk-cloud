package cn.zrkcoder.cloud.framework.dict.config;

import cn.zrkcoder.cloud.framework.common.biz.system.dict.DictDataCommonApi;
import cn.zrkcoder.cloud.framework.dict.core.DictFrameworkUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author zrk on 2026/2/14
 */
@AutoConfiguration
public class ZrkDictAutoConfiguration {

    @Bean
    @SuppressWarnings("InstantiationOfUtilityClass")
    public DictFrameworkUtils dictUtils(DictDataCommonApi dictDataApi) {
        DictFrameworkUtils.init(dictDataApi);
        return new DictFrameworkUtils();
    }

}
