package com.code4a.dueroslib.devicemodule.screen.message;

import com.code4a.dueroslib.framework.message.Payload;

import java.io.Serializable;
import java.util.List;

public class RenderHintPayload extends Payload implements Serializable {
    public List<String> cueWords;
}
