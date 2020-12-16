package io.kimmking.rpcfx.server;

import io.kimmking.rpcfx.api.RpcfxException;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RpcfxInvoker {

    private final RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver){
        this.resolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest request){
        RpcfxResponse response = new RpcfxResponse();
        String serviceClassName = request.getServiceClass();
        try {
            Class<?> serviceClass  = Class.forName(serviceClassName);
            // 改成泛型和反射
            Object service = resolver.resolve(serviceClass);//this.applicationContext.getBean(serviceClass);
            Method method = service.getClass().getMethod(request.getMethod(), request.getTypes());
            Object result = method.invoke(service, request.getParams());
            // 两次json序列化 合并成一个
            response.setResult(result);
            response.setSuccess(true);
            return response;
        } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
            // 2.封装一个统一的RpcfxException
            e.printStackTrace();
            response.setSuccess(false);
            response.setRpcfxException(new RpcfxException(e));
            return response;
        }
    }

}
