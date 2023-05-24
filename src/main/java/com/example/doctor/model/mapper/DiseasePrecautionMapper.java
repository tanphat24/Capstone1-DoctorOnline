package com.example.doctor.model.mapper;

import com.example.doctor.model.dto.DiseasePrecautionDTO;
import com.example.doctor.model.entity.DiseasePrecaution;

import java.util.List;

public interface DiseasePrecautionMapper {

    DiseasePrecautionDTO toDTO(DiseasePrecaution diseasePrecaution);

    List<DiseasePrecautionDTO> toListDTO(List<DiseasePrecaution> diseasePrecautionList);

    DiseasePrecaution toEntity(DiseasePrecautionDTO diseasePrecautionDTO);

}
