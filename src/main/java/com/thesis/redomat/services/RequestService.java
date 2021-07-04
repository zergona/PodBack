package com.thesis.redomat.services;


import com.thesis.redomat.data.dtos.ConfirmationDto;
import com.thesis.redomat.data.dtos.ResponseDto;
import com.thesis.redomat.data.models.Patient;
import com.thesis.redomat.data.models.Request;
import com.thesis.redomat.data.models.RequestDto;
import com.thesis.redomat.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class RequestService {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    PatientService patientService;

    @Autowired
    DomZdravljaService domZdravljaService;

    @Autowired
    private JavaMailSender emailSender;


    public ResponseEntity createNewRequest(RequestDto requestDto){
        Optional<Patient> patientOptional = patientService.getPatientBySsn(requestDto.getSsn());
        Patient patient;
        if(patientOptional.isPresent()){
            patient = patientOptional.get();
        }
        else{
            patient = new Patient(requestDto.getfName(), requestDto.getlName(), requestDto.getSsn(), requestDto.getEmail());
            patientService.addNewPatient(patient);
        }
        Request request = new Request(requestDto.getSymptoms(), domZdravljaService.getDomZdravljaById(requestDto.getDomZdravlja()).get(), patient);
        requestRepository.save(request);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo("h.muharemovic@gmail.com");
        message.setSubject("Potvrda zahtjeva za pregled u COVID ambulanti");
        message.setText("Postovani,\n" +
                "Molimo da potvrdite Vas termin klikom na sljedeci link: localhost:8080/request/confirm/" + request.getRequestId() + "/1\n" +
                "Klikom na sljedeci link mozete otkazati svoj termin: localhost:8080/request/cancel/" + request.getRequestId() );
        emailSender.send(message);
        return new ResponseEntity(HttpStatus.OK);

    }
    public ResponseEntity confirmRequest(ConfirmationDto confirmationDto){
        Optional<Request> requestOptional = requestRepository.findById(confirmationDto.getRequestId());
        if(requestOptional.isPresent()) {
            Request request = requestOptional.get();
            request.setStatus(confirmationDto.getResponse());
            requestRepository.save(request);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
        Optional<Integer> todayActiveCount = requestRepository.countTodayActive(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        int todayActiveCountInt = todayActiveCount.get();
        double etaInMin = todayActiveCountInt * 20;
        ResponseDto responseDto = new ResponseDto(todayActiveCountInt, etaInMin);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    public Iterable<Request> getConfirmedActiveRequests(){
        return requestRepository.findTodayActive(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
    }
    public ArrayList<RequestDto> getConfirmedActiveForFront(){
        Iterable<Request> requestIterable = requestRepository.findTodayActive(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        ArrayList<RequestDto> requestDtos = new ArrayList<>();
        for (Request request : requestIterable) {
            RequestDto requestDto = new RequestDto(request.getPatient().getFirstName(), request.getPatient().getLastName(), request.getPatient().getEmail(), request.getPatient().getSsn() , 1, request.getSymptoms());
            requestDtos.add(requestDto);
        }
        return requestDtos;
    }
}
