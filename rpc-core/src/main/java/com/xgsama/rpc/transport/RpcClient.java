package com.xgsama.rpc.transport;

import com.xgsama.rpc.entity.RpcRequest;
import com.xgsama.rpc.serializer.CommonSerializer;

/**
 * RpcClient
 *
 * @author : xgSama
 * @date : 2021/12/3 22:22:35
 */
public interface RpcClient {
    int DEFAULT_SERIALIZER = CommonSerializer.PROTOBUF_SERIALIZER;

    Object sendRequest(RpcRequest rpcRequest);

}
