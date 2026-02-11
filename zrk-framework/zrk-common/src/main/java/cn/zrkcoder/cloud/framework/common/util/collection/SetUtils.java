package cn.zrkcoder.cloud.framework.common.util.collection;

import cn.hutool.core.collection.CollUtil;

import java.util.Set;

/**
 * Set 工具类
 *
 * @author zrk on 2026/2/9
 */
public class SetUtils {

    /**
     * 封装 Set 集合并返回，用于快速创建
     * 去重
     */
    @SafeVarargs
    public static <T> Set<T> asSet(T... objs) {
        return CollUtil.newHashSet(objs);
    }
}
