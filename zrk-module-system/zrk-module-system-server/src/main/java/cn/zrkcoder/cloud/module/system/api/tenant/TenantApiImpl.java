package cn.zrkcoder.cloud.module.system.api.tenant;

import cn.zrkcoder.cloud.framework.common.biz.system.tenant.TenantCommonApi;
import cn.zrkcoder.cloud.framework.common.pojo.CommonResult;
import cn.zrkcoder.cloud.framework.tenant.core.aop.TenantIgnore;
import cn.zrkcoder.cloud.module.system.service.tenant.TenantService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.zrkcoder.cloud.framework.common.pojo.CommonResult.success;

/**
 * @author zrk on 2026/2/21
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class TenantApiImpl implements TenantCommonApi {

    @Resource
    private TenantService tenantService;

    @Override
    @TenantIgnore // 防止递归。避免调用 /rpc-api/system/tenant/valid 接口时，又去触发 /rpc-api/system/tenant/valid 去校验
    public CommonResult<List<Long>> getTenantIdList() {
        return success(tenantService.getTenantIdList());
    }

    @Override
    @TenantIgnore // 获得租户列表的时候，无需传递租户编号
    public CommonResult<Boolean> validTenant(Long id) {
        tenantService.validTenant(id);
        return success(true);
    }

}
