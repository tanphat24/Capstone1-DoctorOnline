package com.example.doctor.model.mapper;

import com.example.doctor.model.dto.SymptomDTO;
import com.example.doctor.model.entity.Symptom;

import java.util.List;

public interface SymptomMapper {

    SymptomDTO toDTO(Symptom symptom);

    List<SymptomDTO> toListDTO(List<Symptom> symptoms);

    Symptom toEntity(SymptomDTO symptomDTO);

}
