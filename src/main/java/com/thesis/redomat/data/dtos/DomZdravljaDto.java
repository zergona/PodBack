package com.thesis.redomat.data.dtos;

public class DomZdravljaDto {
    private int opstina;
    private String name;

    public DomZdravljaDto(int opstina, String name) {
        this.opstina = opstina;
        this.name = name;
    }

    public DomZdravljaDto() {
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
