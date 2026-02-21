package cn.zrkcoder.cloud.module.system.api.dict;

import cn.zrkcoder.cloud.framework.common.biz.system.dict.dto.DictDataRespDTO;
import cn.zrkcoder.cloud.framework.common.pojo.CommonResult;
import cn.zrkcoder.cloud.framework.common.util.object.BeanUtils;
import cn.zrkcoder.cloud.module.system.dal.dataobject.dict.DictDataDO;
import cn.zrkcoder.cloud.module.system.service.dict.DictDataService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

import static cn.zrkcoder.cloud.framework.common.pojo.CommonResult.success;

/**
 * @author zrk on 2026/2/21
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
@Primary // 由于 DictDataCommonApi 的存在，必须声明为 @Primary Bean
public class DictDataApiImpl implements DictDataApi {

    @Resource
    private DictDataService dictDataService;

    @Override
    public CommonResult<Boolean> validateDictDataList(String dictType, Collection<String> values) {
        dictDataService.validateDictDataList(dictType, values);
        return success(true);
    }

    @Override
    public CommonResult<List<DictDataRespDTO>> getDictDataList(String dictType) {
        List<DictDataDO> list = dictDataService.getDictDataListByDictType(dictType);
        return success(BeanUtils.toBean(list, DictDataRespDTO.class));
    }

}
