package com.code4a.dueroslib.devicemodule.screen.extend.card.message;

import com.code4a.dueroslib.framework.message.Payload;

import java.io.Serializable;

public class RenderAirQualityPayload extends Payload implements Serializable {
    public String city;
    public String currentTemperature;
    public String pm25;
    public String airQuality;
    public String day;
    public String date;
    public String dateDescription;
    public String tips;
}
