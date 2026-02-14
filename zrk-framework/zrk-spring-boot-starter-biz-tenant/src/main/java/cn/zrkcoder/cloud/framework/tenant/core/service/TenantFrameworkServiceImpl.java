package cn.zrkcoder.cloud.framework.tenant.core.service;

import cn.zrkcoder.cloud.framework.common.biz.system.tenant.TenantCommonApi;
import cn.zrkcoder.cloud.framework.common.pojo.CommonResult;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.Duration;
import java.util.List;

import static cn.zrkcoder.cloud.framework.common.util.cache.CacheUtils.buildAsyncReloadingCache;

/**
 * @author zrk on 2026/2/14
 */
@RequiredArgsConstructor
public class TenantFrameworkServiceImpl implements TenantFrameworkService{

    private final TenantCommonApi tenantApi;

    /**
     * 针对 {@link #getTenantIds()} 的缓存
     */
    private final LoadingCache<Object, List<Long>> getTenantIdsCache = buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<Object, List<Long>>() {

                @Override
                public List<Long> load(Object key) {
                    return tenantApi.getTenantIdList().getCheckedData();
                }

            });

    /**
     * 针对 {@link #validTenant(Long)} 的缓存
     */
    private final LoadingCache<Long, CommonResult<Boolean>> validTenantCache = buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<Long, CommonResult<Boolean>>() {

                @Override
                public CommonResult<Boolean> load(Long id) {
                    return tenantApi.validTenant(id);
                }

            });

    @Override
    @SneakyThrows
    public List<Long> getTenantIds() {
        return getTenantIdsCache.get(Boolean.TRUE);
    }

    @Override
    @SneakyThrows
    public void validTenant(Long id) {
        validTenantCache.get(id).checkError();
    }

}
