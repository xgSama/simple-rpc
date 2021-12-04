package com.xgsama.rpc.loadbalance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * LoadBalancer
 *
 * @author : xgSama
 * @date : 2021/12/3 22:26:14
 */
public interface LoadBalancer {

    Instance select(List<Instance> instances);
}
