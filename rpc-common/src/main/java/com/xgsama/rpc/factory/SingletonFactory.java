package com.xgsama.rpc.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * SingletonFactory
 *
 * @author : xgSama
 * @date : 2021/12/3 21:49:12
 */
public class SingletonFactory {
    private static Map<Class<?>, Object> objectMap = new HashMap<>();

    private SingletonFactory() {
    }

    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public static <T> T getInstance(Class<T> clazz) {
        Object instance = objectMap.get(clazz);
        synchronized (clazz) {
            if (instance == null) {
                try {
                    instance = clazz.newInstance();
                    objectMap.put(clazz, instance);
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        return clazz.cast(instance);
    }

}
