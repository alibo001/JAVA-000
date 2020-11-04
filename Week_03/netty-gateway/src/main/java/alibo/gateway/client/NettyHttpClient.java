package alibo.gateway.client;

import alibo.gateway.handler.ResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.net.URI;

/**
 *  Netty客户端
 */
public class NettyHttpClient {
    private final ChannelHandlerContext ctx;
    private final FullHttpRequest request;

    public NettyHttpClient(ChannelHandlerContext ctx, FullHttpRequest request) {
        this.ctx = ctx;
        this.request = request;
    }

    public void connect(URI uri) throws Exception {
       EventLoopGroup workerGroup = new NioEventLoopGroup();
       try {
           Bootstrap b = new Bootstrap();
           b.group(workerGroup);
           b.channel(NioSocketChannel.class);
           b.option(ChannelOption.SO_KEEPALIVE, true);
           b.handler(new ChannelInitializer<SocketChannel>() {
               @Override
               public void initChannel(SocketChannel ch) throws Exception {
                   // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                   ch.pipeline().addLast(new HttpResponseDecoder());
                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                   ch.pipeline().addLast(new HttpRequestEncoder());
                   ch.pipeline().addLast(new ResponseHandler(ctx,request));
               }
           });

           // Start the client.
           ChannelFuture f = b.connect(uri.getHost(), uri.getPort()).sync();
           f.channel().closeFuture().sync();
       } finally {
           workerGroup.shutdownGracefully();
       }

   }

}