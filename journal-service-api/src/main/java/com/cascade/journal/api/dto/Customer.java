package com.cascade.journal.api.dto;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Customer implements Serializable {
    public long id = 0;
    public String firstName;
    public String lastName;
    public String mobilePhone;
    public String email;
    public int year, month, day;


//
//    public Customer toOrigin() {
//        Customer origin = new Customer();
//        origin.setId(id);
//        origin.setFirstName(firstName);
//        origin.setLastName(lastName);
//        origin.setMobilePhone(mobilePhone);
//        origin.setEmail(email);
//        origin.setBirthDate(new GregorianCalendar(year, month, day));
//        return origin;
//    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[id=" + id
                + ", name=" + firstName + " " + lastName
                + ", bd=" + new GregorianCalendar(year, month, day).getTime()
                + ", mobile=" + mobilePhone
                + ", email=" + email
                + "]";
    }
}
