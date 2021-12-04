package com.xgsama.rpc.transport.netty;

import com.xgsama.rpc.entity.RpcRequest;
import com.xgsama.rpc.entity.RpcResponse;
import com.xgsama.rpc.transport.RpcClient;
import com.xgsama.rpc.transport.netty.client.NettyClient;
import com.xgsama.rpc.util.RpcMessageChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * RpcClientProxy
 *
 * @author : xgSama
 * @date : 2021/12/4 14:27:18
 */
public class RpcClientProxy implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);

    private final RpcClient client;

    public RpcClientProxy(RpcClient client) {
        this.client = client;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        logger.info("调用方法: {}#{}", method.getDeclaringClass().getName(), method.getName());
        RpcRequest rpcRequest = new RpcRequest(UUID.randomUUID().toString(), method.getDeclaringClass().getName(),
                method.getName(), args, method.getParameterTypes(), false);
        RpcResponse<?> rpcResponse = null;
        if (client instanceof NettyClient) {
            try {
                CompletableFuture<RpcResponse<?>> completableFuture = (CompletableFuture<RpcResponse<?>>) client.sendRequest(rpcRequest);
                rpcResponse = completableFuture.get();
            } catch (Exception e) {
                logger.error("方法调用请求发送失败", e);
                return null;
            }
        }
//        if (client instanceof SocketClient) {
//            rpcResponse = (RpcResponse<?>) client.sendRequest(rpcRequest);
//        }
        RpcMessageChecker.check(rpcRequest, rpcResponse);
        return rpcResponse.getData();
    }
}
