package com.xgsama.rpc.exception;

import com.xgsama.rpc.enums.RpcError;

/**
 * RPC调用异常
 *
 * @author : xgSama
 * @date : 2021/12/3 21:42:36
 */
public class RpcException extends RuntimeException {

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }
}
