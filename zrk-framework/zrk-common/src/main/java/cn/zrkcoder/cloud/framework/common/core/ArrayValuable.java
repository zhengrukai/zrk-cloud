package cn.zrkcoder.cloud.framework.common.core;

/**
 * 可生成 T 数组的接口
 * 
 * @author zrk on 2026/2/8
 */
public interface ArrayValuable<T> {

    /**
     * @return 数组
     */
    T[] array();
}
