## Assignment Week 11 - Redis
基于redis的库存系统，假设允许超卖
### 分布式锁
API: [http://localhost:8090/buy_with_lock/{number}](http://localhost:8090/buy_with_lock/{number})  
方法一采用分布式锁，在上锁后get当前库存数值，在扣减后set回redis, 结束后unlock

### decr
API: [http://localhost:8090/buy_with_decr/{number}](http://localhost:8090/buy_with_decr/{number})  
使用redis的decrby直接扣减

### Pub/Sub
本服务模拟Pub/Sub，将库存扣减发送到Pub后，再Sub订阅进行了记录  
可通过  
API: [http://localhost:8090/record/{timestamp}](http://localhost:8090/record/{timestamp})  
进行查询

