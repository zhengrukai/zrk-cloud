package cn.zrkcoder.cloud.module.infra.convert.file;

import cn.zrkcoder.cloud.module.infra.controller.admin.file.vo.config.FileConfigSaveReqVO;
import cn.zrkcoder.cloud.module.infra.dal.dataobject.file.FileConfigDO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 文件配置 Convert
 *
 * @author zrk on 2026/2/18
 */
@Mapper
public interface FileConfigConvert {

    FileConfigConvert INSTANCE = Mappers.getMapper(FileConfigConvert.class);

    @Mapping(target = "config", ignore = true)
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    FileConfigDO convert(FileConfigSaveReqVO bean);

}
