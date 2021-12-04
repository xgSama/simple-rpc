package com.xgsama.rpc.loadbalance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
import java.util.Random;

/**
 * RandomLoadBalancer
 *
 * @author : xgSama
 * @date : 2021/12/3 22:30:13
 */
public class RandomLoadBalancer implements LoadBalancer {
    @Override
    public Instance select(List<Instance> instances) {
        return instances.get(new Random().nextInt(instances.size()));
    }

}
