package com.thesis.redomat.data.dtos;

public class DiagnosisDto {
    private String ssn;
    private String diagnosis;

    public DiagnosisDto(){}

    public DiagnosisDto(String ssn, String diagnosis) {
        this.ssn = ssn;
        this.diagnosis = diagnosis;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
