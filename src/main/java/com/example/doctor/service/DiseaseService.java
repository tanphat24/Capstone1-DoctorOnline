package com.example.doctor.service;

import com.example.doctor.model.dto.DiseaseDTO;
import com.example.doctor.model.entity.Disease;

import java.util.List;

public interface DiseaseService {

    List<Disease> findAll();

    Disease findById(long id);

    Disease findByName(String name);

    Disease save(DiseaseDTO diseaseDTO);

    Boolean delete(long id);

    List<Disease> findDiseaseByIdIn(List<Long> ids);

}
