package com.thesis.redomat.data.dtos;

public class ResponseDto {
    private int queueCount;
    private double eta;

    public ResponseDto(int queueCount, double eta) {
        this.queueCount = queueCount;
        this.eta = eta;
    }

    public ResponseDto() {
    }

    public int getQueueCount() {
        return queueCount;
    }

    public void setQueueCount(int queueCount) {
        this.queueCount = queueCount;
    }

    public double getEta() {
        return eta;
    }

    public void setEta(double eta) {
        this.eta = eta;
    }
}
