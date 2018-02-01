package com.baidu.duer.dcs.devicemodule.cmccgateway.message;

import com.baidu.duer.dcs.framework.message.Payload;

/**
 * Created by jiang on 2018/1/30.
 */

public class ControlGatewayPayload extends Payload {

    /**
     * gateway : 普通网关
     */

    private String gateway;

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }
}
