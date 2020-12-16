package io.kimmking.rpcfx.api;

public class RpcfxResponse {

    private Object result;

    private boolean success;

    private RpcfxException rpcfxException;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public RpcfxException getRpcfxException() {
        return rpcfxException;
    }

    public void setRpcfxException(RpcfxException rpcfxException) {
        this.rpcfxException = rpcfxException;
    }
}
