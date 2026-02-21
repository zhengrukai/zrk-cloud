package cn.zrkcoder.cloud.module.system.dal.mysql.mail;

import cn.hutool.core.util.StrUtil;
import cn.zrkcoder.cloud.framework.common.pojo.PageResult;
import cn.zrkcoder.cloud.framework.mybatis.core.mapper.BaseMapperX;
import cn.zrkcoder.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.zrkcoder.cloud.framework.mybatis.core.util.MyBatisUtils;
import cn.zrkcoder.cloud.module.system.controller.admin.mail.vo.log.MailLogPageReqVO;
import cn.zrkcoder.cloud.module.system.dal.dataobject.mail.MailLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zrk on 2026/2/21
 */
@Mapper
public interface MailLogMapper extends BaseMapperX<MailLogDO> {

    default PageResult<MailLogDO> selectPage(MailLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MailLogDO>()
                .eqIfPresent(MailLogDO::getUserId, reqVO.getUserId())
                .eqIfPresent(MailLogDO::getUserType, reqVO.getUserType())
                .eqIfPresent(MailLogDO::getAccountId, reqVO.getAccountId())
                .eqIfPresent(MailLogDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(MailLogDO::getSendStatus, reqVO.getSendStatus())
                .betweenIfPresent(MailLogDO::getSendTime, reqVO.getSendTime())
                .apply(StrUtil.isNotBlank(reqVO.getToMail()),
                        MyBatisUtils.findInSet("to_mails", reqVO.getToMail()))
                .orderByDesc(MailLogDO::getId));
    }

}
