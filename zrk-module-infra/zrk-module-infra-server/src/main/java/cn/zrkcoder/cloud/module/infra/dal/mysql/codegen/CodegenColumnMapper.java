package cn.zrkcoder.cloud.module.infra.dal.mysql.codegen;

import cn.zrkcoder.cloud.framework.mybatis.core.mapper.BaseMapperX;
import cn.zrkcoder.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.zrkcoder.cloud.module.infra.dal.dataobject.codegen.CodegenColumnDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author zrk on 2026/2/17
 */
@Mapper
public interface CodegenColumnMapper extends BaseMapperX<CodegenColumnDO> {

    default List<CodegenColumnDO> selectListByTableId(Long tableId) {
        return selectList(new LambdaQueryWrapperX<CodegenColumnDO>()
                .eq(CodegenColumnDO::getTableId, tableId)
                .orderByAsc(CodegenColumnDO::getOrdinalPosition));
    }

    default void deleteListByTableId(Long tableId) {
        delete(CodegenColumnDO::getTableId, tableId);
    }

    default void deleteListByTableId(Collection<Long> tableIds) {
        delete(new LambdaQueryWrapperX<CodegenColumnDO>()
                .in(CodegenColumnDO::getTableId, tableIds));
    }

}
