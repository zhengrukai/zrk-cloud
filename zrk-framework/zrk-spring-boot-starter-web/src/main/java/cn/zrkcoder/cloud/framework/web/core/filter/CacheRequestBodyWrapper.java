package cn.zrkcoder.cloud.framework.web.core.filter;

import cn.zrkcoder.cloud.framework.common.util.servlet.ServletUtils;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

/**
 * Request Body 缓存 Wrapper
 *
 * @author zrk on 2026/2/11
 */
public class CacheRequestBodyWrapper extends HttpServletRequestWrapper {

    /**
     * 缓存的内容
     */
    private final byte[] body;

    public CacheRequestBodyWrapper(HttpServletRequest request) {
        super(request);
        body = ServletUtils.getBodyBytes(request);
    }

    // 将缓存的请求体（body）封装为 ServletInputStream，供后续读取
    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        // 返回 ServletInputStream
        return new ServletInputStream() {

            @Override
            public int read() {
                return inputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            // 空实现，不支持异步读取监听。
            @Override
            public void setReadListener(ReadListener readListener) {}

            @Override
            public int available() {
                return body.length;
            }

        };
    }

    // 确保能以字符方式访问缓存的请求体数据。
    @Override
    public BufferedReader getReader() {
        // 1. 通过 this.getInputStream() 获取请求体的输入流。
        // 2. 使用 InputStreamReader 将字节流转换为字符流。
        // 3. 用 BufferedReader 包装字符流，便于按行读取请求体内容
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    /**
     * 返回缓存请求体 body 的字节长度
     * 提供 HTTP 请求体的实际大小，便于后续处理或验证数据完整性
     */
    @Override
    public int getContentLength() {
        return body.length;
    }

    /**
     * 返回 body 字节数组的长度，即缓存的请求体大小。
     * 确保在包装后的请求中，能够正确获取原始请求体的长度信息，避免因流被读取后无法再次获取长度的问题。
     */
    @Override
    public long getContentLengthLong() {
        return body.length;
    }
}
