package cn.zrkcoder.cloud.module.infra.convert.config;

import cn.zrkcoder.cloud.framework.common.pojo.PageResult;
import cn.zrkcoder.cloud.module.infra.controller.admin.config.vo.ConfigRespVO;
import cn.zrkcoder.cloud.module.infra.controller.admin.config.vo.ConfigSaveReqVO;
import cn.zrkcoder.cloud.module.infra.dal.dataobject.config.ConfigDO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author zrk on 2026/2/18
 */
@Mapper
public interface ConfigConvert {

    ConfigConvert INSTANCE = Mappers.getMapper(ConfigConvert.class);

    PageResult<ConfigRespVO> convertPage(PageResult<ConfigDO> page);

    List<ConfigRespVO> convertList(List<ConfigDO> list);

    @Mapping(source = "configKey", target = "key")
    ConfigRespVO convert(ConfigDO bean);

    @Mapping(source = "key", target = "configKey")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    ConfigDO convert(ConfigSaveReqVO bean);

}
