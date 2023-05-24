package com.example.doctor.service.impl;

import com.example.doctor.model.entity.Disease;
import com.example.doctor.model.entity.DiseaseSymptom;
import com.example.doctor.model.entity.Symptom;
import com.example.doctor.repository.DiseaseSymptomRepository;
import com.example.doctor.service.DiseaseSymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiseaseSymptomServiceImpl implements DiseaseSymptomService {

    @Autowired
    private DiseaseSymptomRepository diseaseSymptomRepository;

    @Override
    public List<DiseaseSymptom> findAll() {
        return diseaseSymptomRepository.findAll();
    }

    @Override
    public List<DiseaseSymptom> findByDisease(Disease disease) {
        return diseaseSymptomRepository.findByDiseaseAndStatusIsTrue(disease);
    }

    @Override
    public DiseaseSymptom save(DiseaseSymptom diseaseSymptom) {
        return diseaseSymptomRepository.save(diseaseSymptom);
    }

    @Override
    public void delete(List<DiseaseSymptom> diseaseSymptomList) {
        diseaseSymptomRepository.deleteAll(diseaseSymptomList);
    }

    @Override
    public List<DiseaseSymptom> findBySymptomIn(List<Symptom> symptomList) {
        return diseaseSymptomRepository.findBySymptomIn(symptomList);
    }

}
