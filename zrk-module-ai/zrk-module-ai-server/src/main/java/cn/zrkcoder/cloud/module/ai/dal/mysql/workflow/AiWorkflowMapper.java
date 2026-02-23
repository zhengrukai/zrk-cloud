package cn.zrkcoder.cloud.module.ai.dal.mysql.workflow;

import cn.zrkcoder.cloud.framework.common.pojo.PageResult;
import cn.zrkcoder.cloud.framework.mybatis.core.mapper.BaseMapperX;
import cn.zrkcoder.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.zrkcoder.cloud.module.ai.controller.admin.workflow.vo.AiWorkflowPageReqVO;
import cn.zrkcoder.cloud.module.ai.dal.dataobject.workflow.AiWorkflowDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zrk on 2026/2/23
 */
@Mapper
public interface AiWorkflowMapper extends BaseMapperX<AiWorkflowDO> {

    default AiWorkflowDO selectByCode(String code) {
        return selectOne(AiWorkflowDO::getCode, code);
    }

    default PageResult<AiWorkflowDO> selectPage(AiWorkflowPageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<AiWorkflowDO>()
                .eqIfPresent(AiWorkflowDO::getStatus, pageReqVO.getStatus())
                .likeIfPresent(AiWorkflowDO::getName, pageReqVO.getName())
                .likeIfPresent(AiWorkflowDO::getCode, pageReqVO.getCode())
                .betweenIfPresent(AiWorkflowDO::getCreateTime, pageReqVO.getCreateTime()));
    }

}
