package cn.zrkcoder.cloud.framework.common.exception;

import cn.zrkcoder.cloud.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.zrkcoder.cloud.framework.common.exception.enums.ServiceErrorCodeRange;
import lombok.Data;

/**
 * 错误码对象
 * 全局错误码，占用 [0, 999], 参见 {@link GlobalErrorCodeConstants}
 * 业务异常错误码，占用 [1 000 000 000, +∞)，参见 {@link ServiceErrorCodeRange}
 * 
 * @author zrk on 2026/2/8
 */
@Data
public class ErrorCode {

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误提示
     */
    private final String msg;
    
    public ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
