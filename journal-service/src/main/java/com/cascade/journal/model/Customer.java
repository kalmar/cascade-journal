package com.cascade.journal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    private String firstName;

    private String lastName;

    @Temporal(TemporalType.DATE)
    private Calendar birthDate;

    @Column(length = 32)
    private String mobilePhone;

    @Column(length = 64)
    private String email;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[id=" + id
                + ", name=" + firstName + " " + lastName
                + ", bd=" + birthDate.getTime()
                + ", mobile=" + mobilePhone
                + ", email=" + email
                + "]";
    }
}
