package com.xgsama.test;


import com.xgsama.rpc.annotation.Service;
import com.xgsama.rpc.api.ByeService;

/**
 * @author xgsama
 */
@Service
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(String name) {
        return "bye, " + name;
    }
}
