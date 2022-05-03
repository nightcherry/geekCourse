package cn.example.mq.producer;

import cn.example.mq.dto.MessageDto;
import cn.example.mq.utils.ProtoStuffSerializerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * 生产者类
 */
@Component
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue testQueue;

    @Autowired
    private Topic testTopic;

    @Autowired
    private Queue testQueueObj;

    public void sendQueueText(String msg)  {
        this.jmsMessagingTemplate.convertAndSend(this.testQueue, msg);
    }

    public void sendTopicText(String msg)  {
        this.jmsMessagingTemplate.convertAndSend(this.testTopic, msg);
    }

    public void sendQueueObj(MessageDto msg)  {
        this.jmsMessagingTemplate.convertAndSend(this.testQueueObj, ProtoStuffSerializerUtil.serialize(msg));
    }
}

