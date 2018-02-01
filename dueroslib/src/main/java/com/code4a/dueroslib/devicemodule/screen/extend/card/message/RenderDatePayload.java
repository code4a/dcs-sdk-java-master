package com.code4a.dueroslib.devicemodule.screen.extend.card.message;

import com.code4a.dueroslib.framework.message.Payload;

import java.io.Serializable;

public class RenderDatePayload extends Payload implements Serializable {
    public String datetime;
    public String timeZoneName;
    public String day;
}
