package cn.zrkcoder.cloud.framework.desensitize.core.handler;

import cn.zrkcoder.cloud.framework.desensitize.core.DesensitizeTest;
import cn.zrkcoder.cloud.framework.desensitize.core.annotation.Address;
import cn.zrkcoder.cloud.framework.desensitize.core.base.handler.DesensitizationHandler;

/**
 * {@link Address} 的脱敏处理器
 * 用于 {@link DesensitizeTest} 测试使用
 *
 * @author zrk on 2026/2/11
 */
public class AddressHandler implements DesensitizationHandler<Address> {

    @Override
    public String desensitize(String origin, Address annotation) {
        return origin + annotation.replacer();
    }

}
