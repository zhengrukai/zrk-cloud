package com.fhs.trans.service;

import com.fhs.core.trans.vo.VO;

import java.util.ArrayList;
import java.util.List;

/**
 * 只有实现了这个接口的才能自动翻译
 *
 * @author zrk on 2026/2/9
 */
public interface AutoTransable<V extends VO> {

    /**
     * 根据 ids 查询
     *
     * @param ids 编号数组
     * @return 数据列表
     */
    default List<V> selectByIds(List<? extends Object> ids){
        return new ArrayList<>();
    }

    /**
     * 获取 db 中所有的数据
     *
     * @return db 中所有的数据
     */
    default List<V> select(){
        return new ArrayList<>();
    }

    /**
     * 根据 id 获取 vo
     *
     * @param primaryValue id
     * @return vo
     */
    V selectById(Object primaryValue);
}
