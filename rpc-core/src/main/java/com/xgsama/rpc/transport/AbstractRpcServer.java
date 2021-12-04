package com.xgsama.rpc.transport;

import com.xgsama.rpc.annotation.Service;
import com.xgsama.rpc.annotation.ServiceScan;
import com.xgsama.rpc.enums.RpcError;
import com.xgsama.rpc.exception.RpcException;
import com.xgsama.rpc.provider.ServiceProvider;
import com.xgsama.rpc.registry.ServiceRegistry;
import com.xgsama.rpc.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * AbstractRpcServer
 *
 * @author : xgSama
 * @date : 2021/12/4 12:16:14
 */
@Slf4j
public abstract class AbstractRpcServer implements RpcServer {

    protected String host;
    protected int port;

    protected ServiceRegistry serviceRegistry;
    protected ServiceProvider serviceProvider;

    /**
     * 扫描出带有@sercice注解的类
     */
    public void scanServices() {
        String mainClassName = ReflectUtil.getStackTrace();
        Class<?> startClass;

        // 主启动类的 @ServiceScan 注解
        try {
            startClass = Class.forName(mainClassName);
            if (!startClass.isAnnotationPresent(ServiceScan.class)) {
                log.error("启动类缺少 @ServiceScan 注解");
                throw new RpcException(RpcError.SERVICE_SCAN_PACKAGE_NOT_FOUND);
            }
        } catch (ClassNotFoundException e) {
            log.error("出现未知错误");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }

        // 包路径
        String basePackage = startClass.getAnnotation(ServiceScan.class).value();
        if ("".equals(basePackage)) {
            basePackage = mainClassName.substring(0, mainClassName.lastIndexOf("."));
        }
        // 反射加载包下所有类，放到一个Set集合中
        Set<Class<?>> classSet = ReflectUtil.getClasses(basePackage);
        for (Class<?> clazz : classSet) {
            if (clazz.isAnnotationPresent(Service.class)) {
                String serviceName = clazz.getAnnotation(Service.class).name();
                Object obj;
                try {
                    obj = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("创建 " + clazz + " 时有错误发生");
                    continue;
                }
                if ("".equals(serviceName)) {
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> oneInterface : interfaces) {
                        publishService(obj, oneInterface.getCanonicalName());
                    }
                } else {
                    publishService(obj, serviceName);
                }
            }
        }
    }

    @Override
    public <T> void publishService(T service, String serviceName) {
        serviceProvider.addServiceProvider(service, serviceName);
        serviceRegistry.register(serviceName, new InetSocketAddress(host, port));
    }
}
