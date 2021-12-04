package com.xgsama.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示一个服务提供类，用于远程接口的实现类
 *
 * @author : xgSama
 * @date : 2021/12/3 22:08:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {

    public String name() default "";

}
