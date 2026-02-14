package cn.zrkcoder.cloud.framework.common.util.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.time.Duration;
import java.util.concurrent.Executors;

/**
 * Cache 工具类
 *
 * @author zrk on 2026/2/9
 */
public class CacheUtils {

    /**
     * 异步刷新的 loadingCache 最大缓存数量
     */
    private static final Integer CACHE_MAX_SIZE = 10000;

    /**
     * 构建异步刷新的 LoadingCache 对象
     * 注意：如果你的缓存和 ThreadLocal 有关系，要么自己处理 ThreadLocal 的传递，要么使用 {@link #buildCache(Duration, CacheLoader)} 方法
     * 或者简单理解：
     * 和“人”相关的，使用 {@link #buildCache(Duration, CacheLoader)} 方法
     * 和“全局”、“系统”相关的，使用当前缓存方法
     * <K, V> 泛型方法的类型声明: 告诉 Java 编译器，K,V为占位符
     *
     * @param duration 过期时间
     * @param loader CacheLoader 对象
     * @return LoadingCache 对象
     *
     *
     */
    public static <K, V> LoadingCache<K, V> buildAsyncReloadingCache(Duration duration, CacheLoader<K, V> loader) {
        return CacheBuilder.newBuilder()
                .maximumSize(CACHE_MAX_SIZE)
                // 核心特性1: 智能刷新触发：不是 “过期删除”，而是 “刷新触发”
                /*
                 * 工作原理:
                 * 1. 缓存写入后开始计时
                 * 2. 超过duration时间后，标记为"需要刷新"
                 * 3. 下次访问该key时才真正触发刷新操作
                 * 4. 刷新期间其他线程仍可获取旧值，保证高可用
                 */
                .refreshAfterWrite(duration)
                // 核心特性2: 异步执行
                /*
                 * 工作原理:
                 * 1. 将同步的CacheLoader包装为异步版本
                 * 2. 缓存加载 / 刷新逻辑操作在独立线程池执行
                 * 3. 业务线程不会被阻塞
                 * 4. 实现真正的"读写分离"
                 */
                .build(CacheLoader.asyncReloading(loader, Executors.newCachedThreadPool())); // TODO 可能要思考下，未来要不要做成可配置
    }

    /**
     * 构建同步刷新的 LoadingCache 对象
     *
     * @param duration 过期时间
     * @param loader CacheLoader 对象
     * @return LoadingCache 对象
     */
    public static <K, V> LoadingCache<K, V> buildCache(Duration duration, CacheLoader<K, V> loader) {
        return CacheBuilder.newBuilder()
                .maximumSize(CACHE_MAX_SIZE)
                // 只阻塞当前数据加载线程，其他线程返回旧值
                .refreshAfterWrite(duration)
                .build(loader);
    }
}
