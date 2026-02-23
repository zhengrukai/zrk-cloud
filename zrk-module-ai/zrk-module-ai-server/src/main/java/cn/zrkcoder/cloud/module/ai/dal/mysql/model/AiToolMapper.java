package cn.zrkcoder.cloud.module.ai.dal.mysql.model;

import cn.zrkcoder.cloud.framework.common.pojo.PageResult;
import cn.zrkcoder.cloud.framework.mybatis.core.mapper.BaseMapperX;
import cn.zrkcoder.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.zrkcoder.cloud.module.ai.controller.admin.model.vo.tool.AiToolPageReqVO;
import cn.zrkcoder.cloud.module.ai.dal.dataobject.model.AiToolDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI 工具 Mapper
 *
 * @author zrk on 2026/2/23
 */
@Mapper
public interface AiToolMapper extends BaseMapperX<AiToolDO> {

    default PageResult<AiToolDO> selectPage(AiToolPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AiToolDO>()
                .likeIfPresent(AiToolDO::getName, reqVO.getName())
                .eqIfPresent(AiToolDO::getDescription, reqVO.getDescription())
                .eqIfPresent(AiToolDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(AiToolDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AiToolDO::getId));
    }

    default List<AiToolDO> selectListByStatus(Integer status) {
        return selectList(new LambdaQueryWrapperX<AiToolDO>()
                .eq(AiToolDO::getStatus, status)
                .orderByDesc(AiToolDO::getId));
    }

}
