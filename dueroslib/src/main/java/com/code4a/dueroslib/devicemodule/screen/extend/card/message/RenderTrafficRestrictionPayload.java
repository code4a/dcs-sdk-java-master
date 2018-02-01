package com.code4a.dueroslib.devicemodule.screen.extend.card.message;

import com.code4a.dueroslib.framework.message.Payload;

import java.io.Serializable;
import java.util.List;

public class RenderTrafficRestrictionPayload extends Payload implements Serializable {
    public String city;
    public String day;
    public String date;
    public String dateDescription;
    public String restrictionRule;
    public String todayRestriction;
    public String tomorrowRestriction;
    public List<Restriction> weekRestriction;

    public static final class Restriction implements Serializable {
        public String restriction;
        public String day;
    }
}
