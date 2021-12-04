package com.xgsama.rpc.provider;

import com.xgsama.rpc.enums.RpcError;
import com.xgsama.rpc.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ServiceProviderImpl
 *
 * @author : xgSama
 * @date : 2021/12/4 12:27:37
 */
@Slf4j
public class ServiceProviderImpl implements ServiceProvider {
    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public <T> void addServiceProvider(T service, String serviceName) {
        if (registeredService.contains(serviceName)) return;
        registeredService.add(serviceName);
        serviceMap.put(serviceName, service);
        log.info("向接口: {} 注册服务: {}", service.getClass().getInterfaces(), serviceName);
    }

    @Override
    public Object getServiceProvider(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
