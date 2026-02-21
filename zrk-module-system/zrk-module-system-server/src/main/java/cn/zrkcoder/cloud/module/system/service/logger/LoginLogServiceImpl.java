package cn.zrkcoder.cloud.module.system.service.logger;

import cn.zrkcoder.cloud.framework.common.pojo.PageResult;
import cn.zrkcoder.cloud.framework.common.util.object.BeanUtils;
import cn.zrkcoder.cloud.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.zrkcoder.cloud.module.system.controller.admin.logger.vo.loginlog.LoginLogPageReqVO;
import cn.zrkcoder.cloud.module.system.dal.dataobject.logger.LoginLogDO;
import cn.zrkcoder.cloud.module.system.dal.mysql.logger.LoginLogMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 登录日志 Service 实现
 *
 * @author zrk on 2026/2/21
 */
@Service
@Validated
public class LoginLogServiceImpl implements LoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    public LoginLogDO getLoginLog(Long id) {
        return loginLogMapper.selectById(id);
    }

    @Override
    public PageResult<LoginLogDO> getLoginLogPage(LoginLogPageReqVO pageReqVO) {
        return loginLogMapper.selectPage(pageReqVO);
    }

    @Override
    public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
        LoginLogDO loginLog = BeanUtils.toBean(reqDTO, LoginLogDO.class);
        loginLogMapper.insert(loginLog);
    }

}
