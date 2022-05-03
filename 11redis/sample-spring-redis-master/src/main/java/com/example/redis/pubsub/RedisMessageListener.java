package com.example.redis.pubsub;

import com.example.redis.model.Transaction;
import com.example.redis.repository.TransactionRepository;
import com.example.redis.web.TransactionController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class RedisMessageListener implements MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(RedisMessageListener.class);
    @Autowired
    TransactionRepository repo;

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        String msg = message.toString();
        LOG.info("Message received: " + msg);
        Long sell = null;
        Long current = null;
        GenericJackson2JsonRedisSerializer s = new GenericJackson2JsonRedisSerializer();

            Transaction tr = s.deserialize(message.getBody(), Transaction.class);
            repo.save(tr);

    }

}
