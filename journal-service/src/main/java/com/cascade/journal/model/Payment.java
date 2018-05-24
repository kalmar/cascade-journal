package com.cascade.journal.model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Customer customer;

    @Temporal(TemporalType.DATE)
    private Calendar date;

    private int payment;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                + "[id=" + id
                + ",customer=" + customer
                + ",date=" + date
                + "]";
    }
}
