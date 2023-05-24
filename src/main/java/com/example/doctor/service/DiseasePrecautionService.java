package com.example.doctor.service;

import com.example.doctor.model.entity.Disease;
import com.example.doctor.model.entity.DiseasePrecaution;

import java.util.List;

public interface DiseasePrecautionService {

    List<DiseasePrecaution> findAll();

    List<DiseasePrecaution> findByDisease(Disease disease);

    DiseasePrecaution save(DiseasePrecaution diseasePrecaution);

    void delete(List<DiseasePrecaution> diseasePrecautionList);

}
