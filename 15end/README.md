#毕业总结
##JVM
JVM的关键点在于了解各种GC策略的原理，和优缺点  
虽然实际生产当中真正用到的机会不是太多，但是一旦遇到，都是疑难问题  
这个时候，高手和新手的差别就显现出来了  
所以面试当中还是比较喜欢问JVM相关的问题  
知识点:
* 传统GC模型
* G1GC
* ZGC
* 分析工具与疑难问题排查
##NIO
NIO面试中问的比较少，但是大并发环境下有的时候也会遇到性能瓶颈  
这个时候知道NIO原理就很重要  
知识点：
* IO模型(NIO,BIO,同步，异步)
* Netty
* Tomcat优化
##并发编程
并发也是面试当中考得比较多的知识点，虽然Java并发编程的门槛比较低  
但生产当中真正用得好的人也不多  
要通过并发真正最优化执行效率才是难点  
知识点：
* 锁
* 并发原子类
* 并发工具（线程池，信号量等）
##Spring 和 ORM 等框架
Spring是我们生产中日常接触的框架，对它的掌握对我们十分重要  
知识点:
* Spring配置
* AOP
* Spring Boot
* ORM
##MySQL 数据库和 SQL
数据库往往是我们系统的基石，而且往往成为系统瓶颈  
所以数据库的使用和优化，对开发人员十分重要  
知识点:
* 数据库原理
* 事务与锁
* SQL优化
##分库分表
知识点:
* 高可用，集群与主从复制
* 读写分离
##RPC 和微服务
单一的系统服务非常庞大，而且十分难以维护  
因此将其拆分为单一用途的微服务  
这也是我们工作当中天天会接触的技术
对我们系统的健壮性非常必要  
知识点:
* 微服务与分布式
* DUBBO
* Spring cloud
##分布式缓存
数据库的性能往往让其成为我们的系统瓶颈，  
同时结构化的表结构也不再满足当今灵活多变的需求，  
因此以Nosql为主的分布式缓存就很重要  
知识点:
* 本地缓存
* Redis
* Mongodb
* ElasticSearch
* Couchbase/Hbase等
##分布式消息队列
微服务代表我们的服务被拆分为了更小的粒度
因此数据如何在不同系统间传递和同步也成为了一个问题  
知识点:
* 消息的一般术语与架构
* ActiveMQ
* RocketMQ
* Kafka