package com.xgsama.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.xgsama.rpc.enums.RpcError;
import com.xgsama.rpc.exception.RpcException;
import com.xgsama.rpc.util.NacosUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * NacosServiceRegistry
 *
 * @author : xgSama
 * @date : 2021/12/4 12:44:54
 */
@Slf4j
public class NacosServiceRegistry implements ServiceRegistry {
    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            NacosUtil.registerService(serviceName, inetSocketAddress);
        } catch (NacosException e) {
            log.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

}
