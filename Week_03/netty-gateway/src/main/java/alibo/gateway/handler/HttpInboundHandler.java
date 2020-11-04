package alibo.gateway.handler;

import alibo.gateway.client.NettyHttpClient;
import alibo.gateway.filter.HttpRequestFilter;
import alibo.gateway.filter.HttpRequestFilterImpl;
import alibo.gateway.router.HttpEndPointRouterImpl;
import alibo.gateway.router.HttpEndpointRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

import java.net.URI;
import java.util.List;

/**
 * nettyServer处理类, 添加过滤, 路由转发, 使用nettyClient访问代理请求
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    // 过滤
    private final HttpRequestFilter requestFilter;
    // 路由分发
    private final HttpEndpointRouter router;
    // 代理集合
    private final List<String> proxyServer;
    //
    private NettyHttpClient nettyHttpClient;

    public HttpInboundHandler(List<String> proxyServer) {
        this.proxyServer = proxyServer;
        this.requestFilter = new HttpRequestFilterImpl();
        this.router = new HttpEndPointRouterImpl();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
        // ctx.close();
    }

    /**
     * 获取浏览器的请求数据
     * 1.对请求过滤: 添加一个请求头
     * 2.随机获取一个代理服务
     * 3.调用netty Client访问真实服务
     */
    // @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fullRequest = (FullHttpRequest) msg;
        System.out.println("接收到的请求url为" + fullRequest.uri());
        // 老师说把原request里的请求头拿出来,放到新的request里面,
        // 这里没有new新的request, 直接修改request的请求头,复用原request
        requestFilter.filter(fullRequest, ctx);
        //使用netty访问真实服务,把代理服务端的ctx传过去,需要用这个ctx把response返回
        NettyHttpClient nettyHttpClient = new NettyHttpClient(ctx, fullRequest);
        // 先随机取一个代理服务
        String route = router.route(proxyServer);
        // 调用nettyClinet
        nettyHttpClient.connect(new URI(route));
    }

    // @Override
    // public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    //     // FullHttpRequest fullRequest = (FullHttpRequest) msg;
    //     try {
    //         MyHttpClient client = new MyHttpClient("http://localhost:8088/api/hello");
    //         HttpResponse response = client.request("");
    //         String s = EntityUtils.toString(response.getEntity());
    //         System.out.println(s);
    //         DefaultFullHttpResponse response1 = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,Unpooled.wrappedBuffer(s.getBytes()));
    //         ctx.writeAndFlush(response1);
    //         ctx.close();
    //     }finally {
    //         ReferenceCountUtil.release(msg);
    //     }
    //
    // }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
