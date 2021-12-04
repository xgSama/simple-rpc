package com.xgsama.test;

import com.xgsama.rpc.api.ByeService;
import com.xgsama.rpc.api.HelloObject;
import com.xgsama.rpc.api.HelloService;
import com.xgsama.rpc.serializer.CommonSerializer;
import com.xgsama.rpc.transport.RpcClient;
import com.xgsama.rpc.transport.netty.RpcClientProxy;
import com.xgsama.rpc.transport.netty.client.NettyClient;

import java.util.Scanner;

/**
 * NettyClientTest
 *
 * @author : xgSama
 * @date : 2021/12/4 14:25:29
 */
public class NettyClientTest {
    public static void main(String[] args) {

        RpcClient client = new NettyClient(CommonSerializer.PROTOBUF_SERIALIZER);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println("================服务端返回：" + res);
        ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("Netty"));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("请输入要调用的方法：");
            String s = scanner.nextLine();

            if (s.equals("hello")) {
                String messs = helloService.hello(new HelloObject(11, "messs"));
            }
            if (s.equals("bye")) {
                byeService.bye("messs");
            }

        }
    }
}
