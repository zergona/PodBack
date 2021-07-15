package com.thesis.redomat.controllers;


import com.thesis.redomat.data.dtos.ConfirmationDto;
import com.thesis.redomat.data.dtos.DiagnosisDto;
import com.thesis.redomat.data.models.Request;
import com.thesis.redomat.data.models.RequestDto;
import com.thesis.redomat.services.RequestService;
import org.apache.coyote.Response;
import org.hibernate.tool.schema.internal.exec.GenerationTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
@RequestMapping("/request")
public class RequestController {
    @Autowired
    RequestService requestService;

    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity createRequest(@RequestBody RequestDto requestDto){
        return new ResponseEntity(requestService.createNewRequest(requestDto), HttpStatus.OK);
    }
    @CrossOrigin
    @GetMapping("/confirm/{id}/{confirmation}")
    ModelAndView confirmRequest(@PathVariable int id, @PathVariable int confirmation){

        ConfirmationDto confirmationDto = new ConfirmationDto(id, confirmation);
        ResponseEntity re = requestService.confirmRequest(confirmationDto);
        return new ModelAndView("redirect:" + "http://localhost:4200/confirmation");
    }

    @CrossOrigin
    @PostMapping("/finish/{id}")
    ResponseEntity finishExam(@PathVariable int id, @RequestBody DiagnosisDto diagnosisDto){
        requestService.finishRequest(id, diagnosisDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/cancel/{id}")
    ModelAndView cancelRequest(@PathVariable int id){
        requestService.cancelRequest(id);
        return new ModelAndView("redirect:" + "http://localhost:4200/cancel");
    }

    @CrossOrigin
    @GetMapping("/getConfirmedActive")
    ResponseEntity getConfirmedActiveRequests(){
        return new ResponseEntity(requestService.getConfirmedActiveForFront(), HttpStatus.OK);
    }
    @CrossOrigin
    @GetMapping("/{id}")
    ResponseEntity getRequestById(@PathVariable int id){
        return new ResponseEntity(requestService.getRequestById(id), HttpStatus.OK);
    }

}
