package com.xgsama.test;

import com.xgsama.rpc.annotation.ServiceScan;
import com.xgsama.rpc.serializer.CommonSerializer;
import com.xgsama.rpc.transport.RpcServer;
import com.xgsama.rpc.transport.netty.server.NettyServer;
import lombok.extern.slf4j.Slf4j;

/**
 * NettyServerTest
 *
 * @author : xgSama
 * @date : 2021/12/4 13:04:19
 */
@Slf4j
@ServiceScan
public class NettyServerTest {
    public static void main(String[] args) {
        log.info("ready");
        RpcServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.PROTOBUF_SERIALIZER);
        log.info("ready");
        server.start();
    }
}
