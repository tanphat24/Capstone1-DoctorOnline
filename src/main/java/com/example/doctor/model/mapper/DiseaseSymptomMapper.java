package com.example.doctor.model.mapper;

import com.example.doctor.model.dto.DiseaseSymptomDTO;
import com.example.doctor.model.entity.DiseaseSymptom;

import java.util.List;

public interface DiseaseSymptomMapper {

    DiseaseSymptomDTO toDTO(DiseaseSymptom diseaseSymptom);

    List<DiseaseSymptomDTO> toListDTO(List<DiseaseSymptom> diseaseSymptomList);

    DiseaseSymptom toEntity(DiseaseSymptomDTO diseaseSymptomDTO);
    
}
