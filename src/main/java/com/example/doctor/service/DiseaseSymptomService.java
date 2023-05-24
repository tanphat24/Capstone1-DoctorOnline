package com.example.doctor.service;

import com.example.doctor.model.entity.Disease;
import com.example.doctor.model.entity.DiseaseSymptom;
import com.example.doctor.model.entity.Symptom;

import java.util.List;

public interface DiseaseSymptomService {

    List<DiseaseSymptom> findAll();

    List<DiseaseSymptom> findByDisease(Disease disease);

    DiseaseSymptom save(DiseaseSymptom diseaseSymptom);

    void delete(List<DiseaseSymptom> diseaseSymptomList);

    List<DiseaseSymptom> findBySymptomIn(List<Symptom> symptomList);
    
}
