package cn.zrkcoder.cloud.module.infra.dal.mysql.db;

import cn.zrkcoder.cloud.framework.mybatis.core.mapper.BaseMapperX;
import cn.zrkcoder.cloud.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author zrk on 2026/2/17
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
