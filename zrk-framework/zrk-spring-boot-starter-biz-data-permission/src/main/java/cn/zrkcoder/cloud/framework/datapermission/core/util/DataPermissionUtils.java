package cn.zrkcoder.cloud.framework.datapermission.core.util;

import cn.zrkcoder.cloud.framework.datapermission.core.annotation.DataPermission;
import cn.zrkcoder.cloud.framework.datapermission.core.aop.DataPermissionContextHolder;
import lombok.SneakyThrows;

import java.util.concurrent.Callable;

/**
 * 数据权限 工具类
 * <p>
 * 提供一种机制，在执行某些业务逻辑时临时忽略数据权限，避免数据权限规则对当前操作的影响。
 * 通过上下文管理（DataPermissionContextHolder）实现数据权限的动态启用与禁用
 *
 * @author zrk on 2026/2/13
 */
public class DataPermissionUtils {

    private static DataPermission DATA_PERMISSION_DISABLE;

    // 获取表示禁用数据权限的 @DataPermission 注解实例。
    @DataPermission(enable = false)
    @SneakyThrows
    private static DataPermission getDisableDataPermissionDisable() {
        if (DATA_PERMISSION_DISABLE == null) {
            DATA_PERMISSION_DISABLE = DataPermissionUtils.class
                    .getDeclaredMethod("getDisableDataPermissionDisable")
                    .getAnnotation(DataPermission.class);
        }
        return DATA_PERMISSION_DISABLE;
    }

    /**
     * 忽略数据权限，执行对应的逻辑
     *
     * @param runnable 逻辑
     */
    public static void executeIgnore(Runnable runnable) {
        addDisableDataPermission();
        try {
            // 执行 runnable
            runnable.run();
        } finally {
            removeDataPermission();
        }
    }

    /**
     * 忽略数据权限，执行对应的逻辑
     *
     * @param callable 逻辑
     * @return 执行结果
     */
    @SneakyThrows
    public static <T> T executeIgnore(Callable<T> callable) {
        addDisableDataPermission();
        try {
            // 执行 callable
            return callable.call();
        } finally {
            removeDataPermission();
        }
    }

    /**
     * 添加忽略数据权限
     */
    public static void addDisableDataPermission(){
        DataPermission dataPermission = getDisableDataPermissionDisable();
        DataPermissionContextHolder.add(dataPermission);
    }

    public static void removeDataPermission(){
        DataPermissionContextHolder.remove();
    }

}
