package cn.zrkcoder.cloud.framework.env.core.context;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发环境上下文
 *
 * @author zrk on 2026/2/11
 */
public class EnvContextHolder {

    /**
     * 标签的上下文
     * 使用 {@link List} 的原因，可能存在多层设置或者清理
     */
    private static final ThreadLocal<List<String>> TAG_CONTEXT = TransmittableThreadLocal.withInitial(ArrayList::new);

    /**
     * 将指定标签添加到当前线程的标签列表末尾。
     * 支持多次调用，形成标签栈结构。
     */
    public static void setTag(String tag) {
        TAG_CONTEXT.get().add(tag);
    }

    /**
     * 获取当前线程标签列表中的最后一个标签（最近设置的标签）
     */
    public static String getTag() {
        return CollUtil.getLast(TAG_CONTEXT.get());
    }

    /**
     * 移除当前线程标签列表中的最后一个标签。
     * 若列表为空，则直接返回，避免异常。
     */
    public static void removeTag() {
        List<String> tags = TAG_CONTEXT.get();
        if (CollUtil.isEmpty(tags)) {
            return;
        }
        tags.remove(tags.size() - 1);
    }
}
