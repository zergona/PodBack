package com.thesis.redomat.data.models;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Data
public class RequestDto {
    private String fName;
    private String lName;
    private String email;
    private String ssn;
    private int domZdravlja;
    private String symptoms;


    public RequestDto(String fName, String lName, String email, String ssn, int domZdravlja, String symptoms) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.ssn = ssn;
        this.domZdravlja = domZdravlja;
        this.symptoms = symptoms;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public int getDomZdravlja() {
        return domZdravlja;
    }

    public void setDomZdravlja(int domZdravlja) {
        this.domZdravlja = domZdravlja;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
}
