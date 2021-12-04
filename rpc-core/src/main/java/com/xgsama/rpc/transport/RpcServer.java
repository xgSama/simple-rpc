package com.xgsama.rpc.transport;

import com.xgsama.rpc.serializer.CommonSerializer;

/**
 * RpcServer
 *
 * @author : xgSama
 * @date : 2021/12/4 12:24:54
 */
public interface RpcServer {
    int DEFAULT_SERIALIZER = CommonSerializer.PROTOBUF_SERIALIZER;

    void start();

    <T> void publishService(T service, String serviceName);

}
