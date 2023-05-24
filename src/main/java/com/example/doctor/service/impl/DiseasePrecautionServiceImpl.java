package com.example.doctor.service.impl;

import com.example.doctor.model.entity.Disease;
import com.example.doctor.model.entity.DiseasePrecaution;
import com.example.doctor.repository.DiseasePrecautionRepository;
import com.example.doctor.service.DiseasePrecautionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiseasePrecautionServiceImpl implements DiseasePrecautionService {

    @Autowired
    private DiseasePrecautionRepository diseasePrecautionRepository;

    @Override
    public List<DiseasePrecaution> findAll() {
        return diseasePrecautionRepository.findAll();
    }

    @Override
    public List<DiseasePrecaution> findByDisease(Disease disease) {
        return diseasePrecautionRepository.findByDiseaseAndStatusIsTrue(disease);
    }

    @Override
    public DiseasePrecaution save(DiseasePrecaution diseasePrecaution) {
        return diseasePrecautionRepository.save(diseasePrecaution);
    }

    @Override
    public void delete(List<DiseasePrecaution> diseasePrecautionList) {
        diseasePrecautionRepository.deleteAll(diseasePrecautionList);
    }
}
