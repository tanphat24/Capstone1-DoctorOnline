package com.example.doctor.model.mapper;

import com.example.doctor.model.dto.PrecautionDTO;
import com.example.doctor.model.entity.Precaution;

import java.util.List;

public interface PrecautionMapper {

    PrecautionDTO toDTO(Precaution precaution);

    List<PrecautionDTO> toListDTO(List<Precaution> precautions);

    Precaution toEntity(PrecautionDTO precautionDTO);

}
