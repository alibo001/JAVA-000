package alibo.gateway.router;

import java.util.List;
import java.util.Random;

/**
 *  随机返回一个代理地址
 **/
public class HttpEndPointRouterImpl implements HttpEndpointRouter {
    private final Random random = new Random();
    @Override
    public String route(List<String> endpoints) {
        int i = random.nextInt(endpoints.size());
        return endpoints.get(i);
    }
}
