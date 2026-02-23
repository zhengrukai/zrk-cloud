package cn.zrkcoder.cloud.module.ai.dal.mysql.mindmap;

import cn.zrkcoder.cloud.framework.common.pojo.PageResult;
import cn.zrkcoder.cloud.framework.mybatis.core.mapper.BaseMapperX;
import cn.zrkcoder.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.zrkcoder.cloud.module.ai.controller.admin.mindmap.vo.AiMindMapPageReqVO;
import cn.zrkcoder.cloud.module.ai.dal.dataobject.mindmap.AiMindMapDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zrk on 2026/2/23
 */
@Mapper
public interface AiMindMapMapper extends BaseMapperX<AiMindMapDO> {

    default PageResult<AiMindMapDO> selectPage(AiMindMapPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AiMindMapDO>()
                .eqIfPresent(AiMindMapDO::getUserId, reqVO.getUserId())
                .eqIfPresent(AiMindMapDO::getPrompt, reqVO.getPrompt())
                .betweenIfPresent(AiMindMapDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AiMindMapDO::getId));
    }

}
