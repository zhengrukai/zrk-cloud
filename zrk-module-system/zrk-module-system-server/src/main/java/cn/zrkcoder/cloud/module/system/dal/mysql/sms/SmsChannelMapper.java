package cn.zrkcoder.cloud.module.system.dal.mysql.sms;

import cn.zrkcoder.cloud.framework.common.pojo.PageResult;
import cn.zrkcoder.cloud.framework.mybatis.core.mapper.BaseMapperX;
import cn.zrkcoder.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.zrkcoder.cloud.module.system.controller.admin.sms.vo.channel.SmsChannelPageReqVO;
import cn.zrkcoder.cloud.module.system.dal.dataobject.sms.SmsChannelDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zrk on 2026/2/21
 */
@Mapper
public interface SmsChannelMapper extends BaseMapperX<SmsChannelDO> {

    default PageResult<SmsChannelDO> selectPage(SmsChannelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SmsChannelDO>()
                .likeIfPresent(SmsChannelDO::getSignature, reqVO.getSignature())
                .eqIfPresent(SmsChannelDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SmsChannelDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SmsChannelDO::getId));
    }

    default SmsChannelDO selectByCode(String code) {
        return selectOne(SmsChannelDO::getCode, code);
    }

}
