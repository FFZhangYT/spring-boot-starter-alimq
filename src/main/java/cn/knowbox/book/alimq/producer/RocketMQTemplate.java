package cn.knowbox.book.alimq.producer;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import cn.knowbox.book.alimq.event.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;

/**
 * @author jibaole
 * @version 1.0
 * @desc 生产者
 * @date 2018/7/7 下午5:19
 */
@Slf4j
@SuppressWarnings({"WeakerAccess", "unused"})
public class RocketMQTemplate {

    @Resource
    private ProducerBean producer;

    /****
     * @Description: 同步发送
     * @Param: [event]
     * @Author: jibaole
     */
    public SendResult send(MessageEvent event) {
        log.info("start to send message. [topic: {}, tag: {}]", event.getTopic(), event.getTag());
        if (StringUtils.isEmpty(event.getTopic())  || null == event.getDomain()) {
            throw new RuntimeException("topic, or body is null.");
        }
        Message message = new Message(event.getTopic(), event.getTag(), SerializationUtils.serialize(event));
        SendResult result = this.producer.send(message);
        log.info("send message success. ", result.toString());
        return result;
    }

    /****
     * @Description: 同步发送(带延迟时间)
     * @Param: [event, delay]
     * @Author: jibaole
     */
    public SendResult send(MessageEvent event, long delay) {
        log.info("start to send message. [topic: {}, tag: {}]", event.getTopic(), event.getTag());
        if (StringUtils.isEmpty(event.getTopic())  || null == event.getDomain()) {
            throw new RuntimeException("topic, or body is null.");
        }
        Message message = new Message(event.getTopic(), event.getTag(), SerializationUtils.serialize(event));
        message.setStartDeliverTime(System.currentTimeMillis() + delay);
        SendResult result = this.producer.send(message);
        log.info("send message success. ", result.toString());
        return result;
    }

    /**
     * @Description: 单向发送
     * @Param: [event]
     * @Author: jibaole
     */
    public void sendOneway(MessageEvent event) {
        log.info("start to send message. [topic: {}, tag: {}]", event.getTopic(), event.getTag());
        if (StringUtils.isEmpty(event.getTopic())  || null == event.getDomain()) {
            throw new RuntimeException("topic, or body is null.");
        }
        Message message = new Message(event.getTopic(), event.getTag(), SerializationUtils.serialize(event));
        this.producer.sendOneway(message);
        log.info("send message success. ");
    }

    /**
     * @Description: 异步发送
     * @Param: [event]
     * @Author: jibaole
     */
    public void sendAsync(MessageEvent event) {
        log.info("start to send message. [topic: {}, tag: {}]", event.getTopic(), event.getTag());
        if (StringUtils.isEmpty(event.getTopic())  || null == event.getDomain()) {
            throw new RuntimeException("topic, or body is null.");
        }
        Message message = new Message(event.getTopic(), event.getTag(), SerializationUtils.serialize(event));
        this.producer.sendAsync(message, new DefaultSendCallback());
    }


    /**
     * @Description: 异步发送(带延迟时间)
     * @Param: [event, delay]
     * @Author: jibaole
     */
    public void sendAsync(MessageEvent event, long delay) {
        log.info("start to send message. [topic: {}, tag: {}]", event.getTopic(), event.getTag());
        if (StringUtils.isEmpty(event.getTopic())  || null == event.getDomain()) {
            throw new RuntimeException("topic, or body is null.");
        }
        Message message = new Message(event.getTopic(), event.getTag(), SerializationUtils.serialize(event));
        message.setStartDeliverTime(System.currentTimeMillis() + delay);
        this.producer.sendAsync(message, new DefaultSendCallback());
    }


}
