package com.example.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.params.SetParams;

import java.time.Duration;

@Component
public class LockUtil {
    private static final Logger LOG = LoggerFactory.getLogger(LockUtil.class);
    public static final String LOCK_KEY = "LOCK";
    public static final int LOCKED = 1;

    public static final int timeout = 500;
    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate template;

    public boolean lock() {
        Long start = System.currentTimeMillis();
        for (; ; ) {
            SetParams params = new SetParams();
            // 设置timeout, 防止解锁失败
            boolean isOK = false;
            try {
                isOK = template.opsForValue().setIfAbsent(LOCK_KEY, LOCKED, Duration.ofSeconds(2));
            }catch(Exception e){
                LOG.error("get redis lock fail!", e);
            }
            // 加锁成功则返回
            if (isOK) {
                return true;
            }
            //否则循环等待，在timeout时间内仍未获取到锁，则获取失败
            long l = System.currentTimeMillis() - start;
            if (l >= timeout) {
                return false;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean unlock() {
        return template.delete(LOCK_KEY);
    }
}
