package com.example.doctor.model.mapper;

import com.example.doctor.model.dto.DiseaseDTO;
import com.example.doctor.model.entity.Disease;

import java.util.List;

public interface DiseaseMapper {

    DiseaseDTO toDTO(Disease disease);

    List<DiseaseDTO> toListDTO(List<Disease> diseases);

    Disease toEntity(DiseaseDTO diseaseDTO);

}
