package com.cascade.journal.model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "deals")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Payment payment;

    @Temporal(TemporalType.DATE)
    private Calendar date;

    @Temporal(TemporalType.DATE)
    private Calendar dueDate;

    private int visits;

    private int visitsCounter;

    private String notice;
}
