package com.example.doctor.service;

import com.example.doctor.model.entity.Precaution;

import java.util.List;

public interface PrecautionService {

    List<Precaution> findAll();

    Precaution findById(long id);

    Precaution findByName(String name);

    Precaution save(Precaution precaution);

    List<Precaution> findPrecautionByIdIn(List<Long> ids);

}
