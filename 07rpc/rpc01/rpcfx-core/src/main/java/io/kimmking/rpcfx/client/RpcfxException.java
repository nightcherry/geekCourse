package io.kimmking.rpcfx.client;

import io.netty.handler.codec.http.HttpResponseStatus;

public class RpcfxException extends Exception{
    private HttpResponseStatus status;

    public RpcfxException(HttpResponseStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
