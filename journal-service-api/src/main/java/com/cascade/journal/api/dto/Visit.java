package com.cascade.journal.api.dto;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Visit implements Serializable {
    public long id = 0;
    public long customerId = 0;
    public int vYear, vMonth, vDay;
    public int vHour, vMinute;

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[id=" + id
                + ", customerId=" + customerId
                + ", time=" + new GregorianCalendar(
                        vYear, vMonth, vDay, vHour, vMinute).getTime()
                + "]";
    }
}
