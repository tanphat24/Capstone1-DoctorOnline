package com.example.doctor.service;

import com.example.doctor.model.entity.Symptom;

import java.util.List;

public interface SymptomService {

    List<Symptom> findAll();

    Symptom findById(long id);

    Symptom findByName(String name);

    Symptom save(Symptom symptom);
    
    List<Symptom> findByActive();

    List<Symptom> findSymptomByIdIn(List<Long> ids);

}
