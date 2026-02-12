package cn.zrkcoder.cloud.framework.mq.redis.core.pubsub;

import cn.zrkcoder.cloud.framework.mq.redis.core.message.AbstractRedisMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Redis Channel Message 抽象类
 *
 * @author zrk on 2026/2/12
 */
public abstract class AbstractRedisChannelMessage extends AbstractRedisMessage {

    /**
     * 获得 Redis Channel，默认使用类名
     *
     * @return Channel
     */
    @JsonIgnore // 避免序列化。原因是，Redis 发布 Channel 消息的时候，已经会指定。
    public String getChannel() {
        return getClass().getSimpleName();
    }

}
