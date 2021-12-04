package com.xgsama.rpc.transport.netty.client;

import com.xgsama.rpc.entity.RpcRequest;
import com.xgsama.rpc.entity.RpcResponse;
import com.xgsama.rpc.factory.SingletonFactory;
import com.xgsama.rpc.serializer.CommonSerializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * NettyClientHandler
 *
 * @author : xgSama
 * @date : 2021/12/4 14:20:55
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse<?>> {

    private final UnprocessedRequests unprocessedRequests;

    public NettyClientHandler() {
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        try {
            log.info(String.format("客户端接收到消息: %s", msg));
            unprocessedRequests.complete(msg);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                log.info("发送心跳包 [{}]", ctx.channel().remoteAddress());
                Channel channel = ChannelProvider.get((InetSocketAddress) ctx.channel().remoteAddress(),
                        Objects.requireNonNull(CommonSerializer.getByCode(CommonSerializer.DEFAULT_SERIALIZER)));
                RpcRequest rpcRequest = new RpcRequest();
                rpcRequest.setHeartBeat(true);

                channel.writeAndFlush(rpcRequest)
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
