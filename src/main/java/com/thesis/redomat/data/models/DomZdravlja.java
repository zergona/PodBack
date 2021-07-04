package com.thesis.redomat.data.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class DomZdravlja {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private int domZdravljaId;
    private int opstina;
    private String name;

    public DomZdravlja(int opstina, String name) {
        this.opstina = opstina;
        this.name = name;
    }

    public DomZdravlja() {
    }

    public int getDomZdravljaId() {
        return domZdravljaId;
    }


    public int getOpstina() {
        return opstina;
    }

    public void setOpstina(int opstina) {
        this.opstina = opstina;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
