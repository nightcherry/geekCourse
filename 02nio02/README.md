# netty-gateway

实现了OKHttp和Netty的路由转发  
不同实现通过设置proxy.handler的jvm系统变量切换  
* okhttp: 使用okhttp实现
* netty: 使用netty实现
* 默认: 使用httpclient实现
