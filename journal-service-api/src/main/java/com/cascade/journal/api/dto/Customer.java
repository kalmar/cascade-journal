package com.cascade.journal.api.dto;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Customer implements Serializable {
    public long id = 0;
    public String firstName;
    public String lastName;
    public String mobile;
    public String email;
    public int bdYear, bdMonth, bdDay;

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[id=" + id
                + ", name=" + firstName + " " + lastName
                + ", bd=" + new GregorianCalendar(bdYear, bdMonth, bdDay).getTime()
                + ", mobile=" + mobile
                + ", email=" + email
                + "]";
    }
}
