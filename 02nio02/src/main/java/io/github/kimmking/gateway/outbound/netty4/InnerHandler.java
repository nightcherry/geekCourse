package io.github.kimmking.gateway.outbound.netty4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.util.concurrent.DefaultPromise;

public class InnerHandler extends ChannelInboundHandlerAdapter {
    DefaultPromise promise = null;
    public InnerHandler(DefaultPromise promise){
        this.promise = promise;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {


    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            //ByteBuf buf = content.content();
            //String contentStr = buf.toString(io.netty.util.CharsetUtil.UTF_8);
            //buf.release();
            //System.out.println(contentStr);
            promise.setSuccess(msg);
        }
        //System.out.println(((HttpResponse)msg).decoderResult().);
        
        
        
    }
}