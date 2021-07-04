package com.thesis.redomat.data.models;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@Entity
public class Request {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private int requestId;
    private String symptoms;
    private LocalDateTime date;
    @ManyToOne
    private DomZdravlja domZdravlja;
    @ManyToOne
    private Patient patient;
    private int status;
    //status - 0 -> request intialized
    //status - 1 -> request accepted
    //status - 2 -> request declined
    //status - 3 -> request declined after agreement
    //status - 4 -> request completed

    public Request(){}

    public Request(String symptoms, DomZdravlja domZdravlja, Patient patient) {
        this.symptoms = symptoms;
        this.status = 0;
        this.domZdravlja = domZdravlja;
        this.date = LocalDateTime.now();
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
