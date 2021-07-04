package com.thesis.redomat.services;

import com.thesis.redomat.data.dtos.DomZdravljaDto;
import com.thesis.redomat.data.models.DomZdravlja;
import com.thesis.redomat.repositories.DomZdravljaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DomZdravljaService {
    @Autowired
    DomZdravljaRepository domZdravljaRepository;

    public ResponseEntity addDomZdravlja(DomZdravljaDto domZdravljaDto){
        DomZdravlja domZdravlja = new DomZdravlja(domZdravljaDto.getOpstina(), domZdravljaDto.getName());
        return new ResponseEntity(domZdravljaRepository.save(domZdravlja), HttpStatus.OK);
    }
    public Optional<DomZdravlja> getDomZdravljaById(int id){
        return domZdravljaRepository.findById(id);
    }
}
