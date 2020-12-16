package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.api.RpcfxResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DemoResolver implements RpcfxResolver {

    @Autowired
    private ApplicationContext applicationContext;

    // 根据类型获取
    @Override
    public <T> T resolve(Class<T> serviceClass) {
        return this.applicationContext.getBean(serviceClass);
    }

    @Override
    public Object resolve(String serviceName) {
        return this.applicationContext.getBean(serviceName);
    }
}
