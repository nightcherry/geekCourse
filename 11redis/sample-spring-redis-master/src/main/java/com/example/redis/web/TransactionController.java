package com.example.redis.web;

import com.example.redis.model.Transaction;
import com.example.redis.model.TransactionResult;
import com.example.redis.pubsub.RedisPublisher;
import com.example.redis.repository.TransactionRepository;
import com.example.redis.util.LockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.params.SetParams;

import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);

    public static final String STOCK_KEY = "STOCK";
    public static final String STOCK2_KEY = "STOCK2";

    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate template;

    @Autowired
    LockUtil lock;

    @Autowired
    RedisPublisher publisher;

    @Autowired
    TransactionRepository repo;

    @GetMapping("/buy_with_lock/{number}")
    public TransactionResult buyWithLock(@PathVariable("number") long number) {
        boolean isSucc = lock.lock();
        if(isSucc){
            try {
                Integer stock = null;
                try {
                    stock = (Integer) template.opsForValue().get(STOCK_KEY);
                } catch (Exception e) {
                    LOG.error("get stock fail!", e);
                    return TransactionResult.fail("get stock fail!");
                }
                if (stock != null) {
                    long current = stock.intValue() - number;
                    try {
                        template.opsForValue().set(STOCK_KEY, current);
                        publisher.send(number, current);
                    }catch(Exception e){
                        LOG.error("set stock fail!", e);
                        return TransactionResult.fail("set stock fail!");
                    }
                    return TransactionResult.succ(current);
                }else{
                    return TransactionResult.fail("no stock data!");
                }
            }finally {
                lock.unlock();
            }
        }else{
            return TransactionResult.fail("get redis lock fail!");
        }
    }

    @GetMapping("/buy_with_decr/{number}")
    public TransactionResult buyWithDecr(@PathVariable("number") long number) {
        try {
            long current = template.opsForValue().decrement(STOCK2_KEY, number);
            publisher.send(number, current);
            return TransactionResult.succ(current);
        } catch (Exception e) {
            LOG.error("stock decrement fail!", e);
            return TransactionResult.fail("stock decrement fail!");
        }
    }

    @GetMapping("/record/{timestamp}")
    public Transaction getRecord(@PathVariable("timestamp") long timestamp) {
        Optional<Transaction> optional = repo.findById(timestamp);
        Transaction tr = optional.get();
        return tr;
    }
}
