package com.xgsama.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务扫描的基包
 *
 * @author : xgSama
 * @date : 2021/12/3 22:09:08
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceScan {
    public String value() default "";
}
