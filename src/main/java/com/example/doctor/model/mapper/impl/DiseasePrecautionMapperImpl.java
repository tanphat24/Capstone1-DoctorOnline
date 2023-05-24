package com.example.doctor.model.mapper.impl;

import com.example.doctor.model.dto.DiseasePrecautionDTO;
import com.example.doctor.model.entity.DiseasePrecaution;
import com.example.doctor.model.entity.DiseasePrecautionId;
import com.example.doctor.model.mapper.DiseaseMapper;
import com.example.doctor.model.mapper.DiseasePrecautionMapper;
import com.example.doctor.model.mapper.PrecautionMapper;
import com.example.doctor.service.DiseaseService;
import com.example.doctor.service.PrecautionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiseasePrecautionMapperImpl implements DiseasePrecautionMapper {

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private DiseaseMapper diseaseMapper;

    @Autowired
    private PrecautionService precautionService;

    @Autowired
    private PrecautionMapper precautionMapper;

    @Override
    public DiseasePrecautionDTO toDTO(DiseasePrecaution diseasePrecaution) {

        if (diseasePrecaution == null)
            return null;

        DiseasePrecautionDTO diseasePrecautionDTO = new DiseasePrecautionDTO();
        diseasePrecautionDTO.setDiseaseId(diseasePrecaution.getKeyId().getDiseaseId());
        diseasePrecautionDTO.setPrecautionId(diseasePrecaution.getKeyId().getPrecautionId());
        diseasePrecautionDTO.setPrecautionDTO(precautionMapper.toDTO(diseasePrecaution.getPrecaution()));
        diseasePrecautionDTO.setDiseaseDTO(diseaseMapper.toDTO(diseasePrecaution.getDisease()));
        diseasePrecautionDTO.setStatus(diseasePrecaution.isStatus());

        return diseasePrecautionDTO;
    }

    @Override
    public List<DiseasePrecautionDTO> toListDTO(List<DiseasePrecaution> diseasePrecautionList) {

        if (diseasePrecautionList == null)
            return null;

        List<DiseasePrecautionDTO> result = new ArrayList<>();
        diseasePrecautionList.forEach(
                diseasePrecaution -> {
                    result.add(toDTO(diseasePrecaution));
                }
        );

        return result;
    }

    @Override
    public DiseasePrecaution toEntity(DiseasePrecautionDTO diseasePrecautionDTO) {

        if (diseasePrecautionDTO == null)
            return null;

        DiseasePrecaution diseasePrecaution = new DiseasePrecaution();
        DiseasePrecautionId id = new DiseasePrecautionId();
        id.setPrecautionId(diseasePrecautionDTO.getPrecautionId());
        id.setDiseaseId(diseasePrecautionDTO.getDiseaseId());

        diseasePrecaution.setPrecaution(precautionService.findById(diseasePrecautionDTO.getPrecautionId()));
        diseasePrecaution.setDisease(diseaseService.findById(diseasePrecautionDTO.getDiseaseId()));
        diseasePrecaution.setKeyId(id);
        diseasePrecaution.setStatus(diseasePrecautionDTO.isStatus());

        return diseasePrecaution;
    }
}
