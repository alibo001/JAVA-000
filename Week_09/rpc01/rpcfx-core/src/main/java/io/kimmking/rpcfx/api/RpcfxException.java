package io.kimmking.rpcfx.api;

/**
 * author: alibobo
 * description: 全局异常
 **/
public class RpcfxException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public RpcfxException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcfxException() {
        super();
    }

    public RpcfxException(String message) {
        super(message);
    }

    public RpcfxException(Throwable cause) {
        super(cause);
    }
}
