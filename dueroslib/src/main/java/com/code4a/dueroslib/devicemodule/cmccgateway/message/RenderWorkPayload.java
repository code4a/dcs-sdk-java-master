package com.code4a.dueroslib.devicemodule.cmccgateway.message;

import com.code4a.dueroslib.framework.message.Payload;

/**
 * Created by jiang on 2018/2/2.
 */

public class RenderWorkPayload extends Payload {

    /**
     * work : 家庭情况
     */

    private String work;

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}
