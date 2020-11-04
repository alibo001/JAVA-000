package alibo.gateway.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.net.URISyntaxException;

/**
 * 调用http接口 返回处理器
 */
public class ResponseHandler extends ChannelInboundHandlerAdapter {
    private final ChannelHandlerContext serverCtx;
    private final FullHttpRequest request;

    public ResponseHandler(ChannelHandlerContext ctx,FullHttpRequest request) {
        this.serverCtx = ctx;
        this.request = request;
    }

    /**
     * 给真实服务发送请求,如果不需要加参数, 可以直接write request
     * 如果要加参数可以把uri拿出来,重新加参数
     * post目前还不知道如何加参数
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws URISyntaxException {
        System.out.println("通道建立,发送请求");
        // 重新构建uri,可以加参数
        // URI uri = new URI(request.uri() + "?msg=嘿嘿嘿");
        // request.setUri(uri.toASCIIString());
        // 发送请求
        ctx.writeAndFlush(this.request);
    }

    /**
     * 拿到真实服务返回的response和数据,返回给浏览器
     * (这玩意读取两次,一次Response,一次Content,
     *  直接返回Response是不行的,数据在Content里面
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpResponse) {
            System.out.println("读取 response");
            // 如果需要对response的header处理,就转成response对象,不要处理的话,直接write(msg)
            // 这里对response做些处理,可以把Content-Length移除,下面处理数据就不会有长度问题
            DefaultHttpResponse response  = (DefaultHttpResponse) msg;
            response.headers().remove(HttpHeaderNames.CONTENT_LENGTH);
            serverCtx.writeAndFlush(response);
        } else if (msg instanceof HttpContent) {
            System.out.println("读取 content");
            // 1.不需要对数据处理的话, 可以直接返回content
            // serverCtx.writeAndFlush(msg);

            // 2.这里把返回的数据加{-- --}
            DefaultHttpContent content = (DefaultHttpContent) msg;
            ByteBuf start = Unpooled.wrappedBuffer("{--".getBytes());
            ByteBuf end = Unpooled.wrappedBuffer("--}".getBytes());
            ByteBuf buf = Unpooled.wrappedBuffer(start, content.content(),end);
            serverCtx.writeAndFlush(buf);
            serverCtx.close();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}