package com.xgsama.rpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * PackageType
 *
 * @author : xgSama
 * @date : 2021/12/3 21:37:45
 */
@AllArgsConstructor
@Getter
public enum PackageType {

    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;
}
