package com.xgsama.rpc.provider;

/**
 * ServiceProvider
 *
 * @author : xgSama
 * @date : 2021/12/4 12:26:40
 */
public interface ServiceProvider {

    <T> void addServiceProvider(T service, String serviceName);

    Object getServiceProvider(String serviceName);
}
