package com.baidu.duer.dcs.devicemodule.cmccgateway.message;

import com.baidu.duer.dcs.framework.message.Payload;

/**
 * Created by jiang on 2018/1/30.
 */

public class ControlDevicePayload extends Payload {

    /**
     * device : 智能门锁
     * type : 传感器
     */

    private String device;
    private String type;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
