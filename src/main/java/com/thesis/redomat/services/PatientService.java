package com.thesis.redomat.services;

import com.thesis.redomat.data.models.Patient;
import com.thesis.redomat.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    public void addNewPatient(Patient patient){
        Optional<Patient> patientOptional = patientRepository.findBySsn(patient.getSsn());
        if(!patientOptional.isPresent()){
            patientRepository.save(patient);
        }
    }
    public Optional<Patient> getPatientBySsn(String ssn){
        return patientRepository.findBySsn(ssn);
    }
}
