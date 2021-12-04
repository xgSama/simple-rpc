package com.xgsama.rpc.registry;

import java.net.InetSocketAddress;

/**
 * ServiceRegistry
 *
 * @author : xgSama
 * @date : 2021/12/4 12:25:42
 */
public interface ServiceRegistry {

    /**
     * 将一个服务注册进注册表
     *
     * @param serviceName       服务名称
     * @param inetSocketAddress 提供服务的地址
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);
}
