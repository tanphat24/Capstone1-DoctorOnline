package com.example.doctor.service;

import com.example.doctor.model.dto.SearchDTO;
import com.example.doctor.model.entity.Disease;

import java.util.List;

public interface SearchService {

    List<Disease> findDiseaseBySymptom(SearchDTO searchDTO);

    boolean save(long accountId, SearchDTO searchDTO);

}
