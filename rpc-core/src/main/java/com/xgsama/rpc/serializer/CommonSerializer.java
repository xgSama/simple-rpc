package com.xgsama.rpc.serializer;

/**
 * CommonSerializer
 *
 * @author : xgSama
 * @date : 2021/12/3 22:15:12
 */
public interface CommonSerializer {
    Integer KRYO_SERIALIZER = 0;
    Integer JSON_SERIALIZER = 1;
    Integer HESSIAN_SERIALIZER = 2;
    Integer PROTOBUF_SERIALIZER = 3;

    Integer DEFAULT_SERIALIZER = PROTOBUF_SERIALIZER;

    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 0:
//                return new KryoSerializer();
                return null;
            case 1:
                return new JsonSerializer();
            case 2:
//                return new HessianSerializer();
                return null;
            case 3:
                return new ProtobufSerializer();
            default:
                return null;
        }
    }

    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();
}
