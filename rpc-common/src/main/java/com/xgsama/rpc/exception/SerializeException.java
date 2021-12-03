package com.xgsama.rpc.exception;

/**
 * 序列化异常
 *
 * @author : xgSama
 * @date : 2021/12/3 21:43:17
 */
public class SerializeException extends RuntimeException {
    public SerializeException(String msg) {
        super(msg);
    }
}
