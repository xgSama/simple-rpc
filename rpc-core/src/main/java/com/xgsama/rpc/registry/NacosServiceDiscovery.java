package com.xgsama.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.xgsama.rpc.enums.RpcError;
import com.xgsama.rpc.exception.RpcException;
import com.xgsama.rpc.loadbalance.LoadBalancer;
import com.xgsama.rpc.loadbalance.RandomLoadBalancer;
import com.xgsama.rpc.util.NacosUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * NacosServiceDiscovery
 *
 * @author : xgSama
 * @date : 2021/12/3 22:29:09
 */
@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery {

    private final LoadBalancer loadBalancer;

    public NacosServiceDiscovery(LoadBalancer loadBalancer) {
        if (loadBalancer == null) this.loadBalancer = new RandomLoadBalancer();
        else this.loadBalancer = loadBalancer;
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            if (instances.size() == 0) {
                log.error("找不到对应的服务: " + serviceName);
                throw new RpcException(RpcError.SERVICE_NOT_FOUND);
            }
            Instance instance = loadBalancer.select(instances);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            log.error("获取服务时有错误发生:", e);
        }
        return null;
    }
}
