package com.example.doctor.service.impl;

import com.example.doctor.model.entity.Symptom;
import com.example.doctor.repository.SymptomRepository;
import com.example.doctor.service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SymptomServiceImpl implements SymptomService {

    @Autowired
    private SymptomRepository symptomRepository;

    @Override
    public List<Symptom> findAll() {
        return symptomRepository.findAll();
    }

    @Override
    public Symptom findById(long id) {
        return symptomRepository.findById(id).orElse(null);
    }

    @Override
    public Symptom findByName(String name) {
        return symptomRepository.findByName(name);
    }

    @Override
    public Symptom save(Symptom symptom) {
        return symptomRepository.save(symptom);
    }

    public List<Symptom> findByActive() {
        return symptomRepository.findByStatusIsTrue();
    }


    @Override
    public List<Symptom> findSymptomByIdIn(List<Long> ids) {
        return symptomRepository.findSymptomByIdIn(ids);
    }

}
