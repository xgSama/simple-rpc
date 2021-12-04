package com.xgsama.rpc.hook;

import com.xgsama.rpc.factory.ThreadPoolFactory;
import com.xgsama.rpc.util.NacosUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * ShutdownHook
 *
 * @author : xgSama
 * @date : 2021/12/4 12:50:47
 */
@Slf4j
public class ShutdownHook {
    private static final ShutdownHook shutdownHook = new ShutdownHook();

    public static ShutdownHook getShutdownHook() {
        return shutdownHook;
    }

    public void addClearAllHook() {
        log.info("关闭后将自动注销所有服务");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtil.clearRegistry();
            ThreadPoolFactory.shutDownAll();
        }));
    }


}
