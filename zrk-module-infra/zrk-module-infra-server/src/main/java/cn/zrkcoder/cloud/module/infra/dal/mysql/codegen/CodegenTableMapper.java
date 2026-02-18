package cn.zrkcoder.cloud.module.infra.dal.mysql.codegen;

import cn.zrkcoder.cloud.framework.common.pojo.PageResult;
import cn.zrkcoder.cloud.framework.mybatis.core.mapper.BaseMapperX;
import cn.zrkcoder.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.zrkcoder.cloud.module.infra.controller.admin.codegen.vo.table.CodegenTablePageReqVO;
import cn.zrkcoder.cloud.module.infra.dal.dataobject.codegen.CodegenTableDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zrk on 2026/2/17
 */
@Mapper
public interface CodegenTableMapper extends BaseMapperX<CodegenTableDO> {

    // 根据 数据源配置id 和 表名 查询表信息
    default CodegenTableDO selectByTableNameAndDataSourceConfigId(String tableName, Long dataSourceConfigId) {
        return selectOne(CodegenTableDO::getTableName, tableName,
                CodegenTableDO::getDataSourceConfigId, dataSourceConfigId);
    }

    /**
     * 分页查询
     * 模糊查询：表名、描述、类名
     * 时间范围查询：创建时间
     * 倒序：更新时间
     */
    default PageResult<CodegenTableDO> selectPage(CodegenTablePageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<CodegenTableDO>()
                .likeIfPresent(CodegenTableDO::getTableName, pageReqVO.getTableName())
                .likeIfPresent(CodegenTableDO::getTableComment, pageReqVO.getTableComment())
                .likeIfPresent(CodegenTableDO::getClassName, pageReqVO.getClassName())
                .betweenIfPresent(CodegenTableDO::getCreateTime, pageReqVO.getCreateTime())
                .orderByDesc(CodegenTableDO::getUpdateTime)
        );
    }

    /**
     * 根据 数据源配置id 获取表列表
     */
    default List<CodegenTableDO> selectListByDataSourceConfigId(Long dataSourceConfigId) {
        return selectList(CodegenTableDO::getDataSourceConfigId, dataSourceConfigId);
    }

    default List<CodegenTableDO> selectListByTemplateTypeAndMasterTableId(Integer templateType, Long masterTableId) {
        return selectList(CodegenTableDO::getTemplateType, templateType,
                CodegenTableDO::getMasterTableId, masterTableId);
    }

}
