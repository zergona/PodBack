package com.thesis.redomat.services;


import com.thesis.redomat.data.dtos.ConfirmationDto;
import com.thesis.redomat.data.dtos.DiagnosisDto;
import com.thesis.redomat.data.dtos.ResponseDto;
import com.thesis.redomat.data.models.Diagnosis;
import com.thesis.redomat.data.models.Patient;
import com.thesis.redomat.data.models.Request;
import com.thesis.redomat.data.models.RequestDto;
import com.thesis.redomat.repositories.DiagnosisRepository;
import com.thesis.redomat.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

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

    @Autowired
    DiagnosisRepository diagnosisRepository;


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
        String htmlMsg = "Postovani, <br>" +
                "Molimo da potvrdite Vas termin klikom <a href = 'https://pregledondemand.herokuapp.com/request/confirm/" + request.getRequestId() + "/1'>ovdje</a>" + "<br>" +
                "Klikom <a href = 'https://pregledondemand.herokuapp.com/request/cancel/" + request.getRequestId() + "'>ovdje </a> mozete otkazati svoj termin.";
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@podmobile.ba");
            helper.setTo(requestDto.getEmail());
            helper.setSubject("Potvrda zahtjeva za pregled u COVID ambulanti");
            helper.setText(htmlMsg, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);

    }
    public void cancelRequest(int id){
        Optional<Request> requestOptional = requestRepository.findById(id);
        if(requestOptional.isPresent()){
            Request request = requestOptional.get();
            request.setStatus(3);
            requestRepository.save(request);
        }
    }
    public void finishRequest(int id, DiagnosisDto diagnosisDto){
        Optional<Request> requestOptional = requestRepository.findById(id);
        if(requestOptional.isPresent()){
            Request request = requestOptional.get();
            request.setStatus(4);
            requestRepository.save(request);
        }
        Optional<Patient> patientOptional = patientService.getPatientBySsn(diagnosisDto.getSsn());
        if(patientOptional.isPresent()){
            Diagnosis diagnosis = new Diagnosis(patientOptional.get(), diagnosisDto.getDiagnosis());
            diagnosisRepository.save(diagnosis);
        }
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
        String email = requestOptional.get().getPatient().getEmail();
        String htmlMsg = "Postovani, <br>" +
                "Vas pregled bi trebao poceti za " + etaInMin + " minuta. <br>"
                +"Vi ste " + todayActiveCount +" u redu.";
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@podmobile.ba");
            helper.setTo(email);
            helper.setSubject("Vas pregled u COVID ambulanti");
            helper.setText(htmlMsg, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    public Iterable<Request> getConfirmedActiveRequests(){
        return requestRepository.findTodayActive(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
    }
    public ArrayList<RequestDto> getConfirmedActiveForFront(){
        Iterable<Request> requestIterable = requestRepository.findTodayActive(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        ArrayList<RequestDto> requestDtos = new ArrayList<>();
        for (Request request : requestIterable) {
            RequestDto requestDto = new RequestDto(request.getRequestId(), request.getPatient().getFirstName(), request.getPatient().getLastName(), request.getPatient().getEmail(), request.getPatient().getSsn() , 1, request.getSymptoms());
            requestDtos.add(requestDto);
        }
        return requestDtos;
    }

    public Optional<Request> getRequestById(int id){
        return requestRepository.findById(id);
    }
}
