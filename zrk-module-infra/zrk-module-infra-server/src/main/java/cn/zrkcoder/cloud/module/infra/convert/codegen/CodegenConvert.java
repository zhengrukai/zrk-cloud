package cn.zrkcoder.cloud.module.infra.convert.codegen;

import cn.zrkcoder.cloud.framework.common.util.collection.CollectionUtils;
import cn.zrkcoder.cloud.framework.common.util.object.BeanUtils;
import cn.zrkcoder.cloud.module.infra.controller.admin.codegen.vo.CodegenDetailRespVO;
import cn.zrkcoder.cloud.module.infra.controller.admin.codegen.vo.CodegenPreviewRespVO;
import cn.zrkcoder.cloud.module.infra.controller.admin.codegen.vo.column.CodegenColumnRespVO;
import cn.zrkcoder.cloud.module.infra.controller.admin.codegen.vo.table.CodegenTableRespVO;
import cn.zrkcoder.cloud.module.infra.dal.dataobject.codegen.CodegenColumnDO;
import cn.zrkcoder.cloud.module.infra.dal.dataobject.codegen.CodegenTableDO;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.apache.ibatis.type.JdbcType;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

/**
 * @author zrk on 2026/2/17
 */
@Mapper
public interface CodegenConvert {

    CodegenConvert INSTANCE = Mappers.getMapper(CodegenConvert.class);

    // ========== TableInfo 相关 ==========

    @Mappings({
            @Mapping(source = "name", target = "tableName"),
            @Mapping(source = "comment", target = "tableComment"),
    })
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    CodegenTableDO convert(TableInfo bean);

    List<CodegenColumnDO> convertList(List<TableField> list);

    @Mappings({
            @Mapping(source = "name", target = "columnName"),
            @Mapping(source = "metaInfo.jdbcType", target = "dataType", qualifiedByName = "getDataType"),
            @Mapping(source = "comment", target = "columnComment"),
            @Mapping(source = "metaInfo.nullable", target = "nullable"),
            @Mapping(source = "keyFlag", target = "primaryKey"),
            @Mapping(source = "columnType.type", target = "javaType"),
            @Mapping(source = "propertyName", target = "javaField"),
    })
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    CodegenColumnDO convert(TableField bean);

    @Named("getDataType")
    default String getDataType(JdbcType jdbcType) {
        return jdbcType.name();
    }

    // ========== 其它 ==========

    // 把表及其字段列表转换为 表详情 VO 返回
    default CodegenDetailRespVO convert(CodegenTableDO table, List<CodegenColumnDO> columns) {
        CodegenDetailRespVO respVO = new CodegenDetailRespVO();
        respVO.setTable(BeanUtils.toBean(table, CodegenTableRespVO.class));
        respVO.setColumns(BeanUtils.toBean(columns, CodegenColumnRespVO.class));
        return respVO;
    }

    // 把 文件地址 和 代码 转换为 预览 VO 列表返回
    default List<CodegenPreviewRespVO> convert(Map<String, String> codes) {
        return CollectionUtils.convertList(codes.entrySet(),
                entry -> new CodegenPreviewRespVO().setFilePath(entry.getKey()).setCode(entry.getValue()));
    }

}
