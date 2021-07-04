package com.thesis.redomat.controllers;

import com.thesis.redomat.data.dtos.DomZdravljaDto;
import com.thesis.redomat.services.DomZdravljaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/domzdravlja")
public class DomZdravljaController {
    @Autowired
    DomZdravljaService domZdravljaService;

    @PostMapping
    public ResponseEntity addDomZdravlja(DomZdravljaDto domZdravljaDto){
        return domZdravljaService.addDomZdravlja(domZdravljaDto);
    }
}
