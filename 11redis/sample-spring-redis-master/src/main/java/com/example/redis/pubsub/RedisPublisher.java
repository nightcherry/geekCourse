package com.example.redis.pubsub;

import com.example.redis.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component
public class RedisPublisher {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    ChannelTopic topic;

    public void send(long number, long current) {
        Transaction transaction = new Transaction(System.currentTimeMillis(), number, current);
        redisTemplate.convertAndSend(topic.getTopic(), transaction);
    }
}
