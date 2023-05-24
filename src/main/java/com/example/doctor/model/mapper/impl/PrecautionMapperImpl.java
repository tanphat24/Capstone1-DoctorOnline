package com.example.doctor.model.mapper.impl;

import com.example.doctor.model.dto.PrecautionDTO;
import com.example.doctor.model.entity.Precaution;
import com.example.doctor.model.mapper.PrecautionMapper;
import com.example.doctor.service.PrecautionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrecautionMapperImpl implements PrecautionMapper {

    @Autowired
    private PrecautionService precautionService;

    @Override
    public PrecautionDTO toDTO(Precaution precaution) {

        if (precaution == null)
            return null;

        PrecautionDTO precautionDTO = new PrecautionDTO();
        precautionDTO.setId(precaution.getId());
        precautionDTO.setName(precaution.getName());
        precautionDTO.setStatus(precaution.isStatus());

        return precautionDTO;
    }

    @Override
    public List<PrecautionDTO> toListDTO(List<Precaution> precautions) {

        if (precautions == null)
            return null;

        List<PrecautionDTO> result = new ArrayList<>();
        precautions.forEach(element -> result.add(toDTO(element)));

        return result;
    }

    @Override
    public Precaution toEntity(PrecautionDTO precautionDTO) {

        if (precautionDTO == null)
            return null;

        Precaution precaution = precautionService.findById(precautionDTO.getId());

        if (precaution == null)
            precaution = new Precaution();
        precaution.setName(precautionDTO.getName());
        precaution.setStatus(precautionDTO.isStatus());

        return precaution;
    }

}
