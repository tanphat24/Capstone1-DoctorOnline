package com.example.doctor.model.mapper.impl;

import com.example.doctor.model.dto.DiseaseDTO;
import com.example.doctor.model.entity.Disease;
import com.example.doctor.model.mapper.DiseaseMapper;
import com.example.doctor.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiseaseMapperImpl implements DiseaseMapper {

    @Autowired
    private DiseaseService diseaseService;

    @Override
    public DiseaseDTO toDTO(Disease disease) {

        if (disease == null)
            return null;

        DiseaseDTO diseaseDTO = new DiseaseDTO();
        diseaseDTO.setId(disease.getId());
        diseaseDTO.setName(disease.getName());
        diseaseDTO.setDescription(disease.getDescription());
        diseaseDTO.setStatus(disease.isStatus());

        return diseaseDTO;
    }

    @Override
    public List<DiseaseDTO> toListDTO(List<Disease> diseases) {

        if (diseases == null)
            return null;

        List<DiseaseDTO> result = new ArrayList<>();
        diseases.forEach(element -> result.add(toDTO(element)));

        return result;
    }

    @Override
    public Disease toEntity(DiseaseDTO diseaseDTO) {

        if (diseaseDTO == null)
            return null;

        Disease disease = diseaseService.findById(diseaseDTO.getId());

        if (disease == null)
            disease = new Disease();
        disease.setName(diseaseDTO.getName());
        disease.setDescription(diseaseDTO.getDescription());
        disease.setStatus(diseaseDTO.isStatus());
        disease.setDescription(diseaseDTO.getDescription());

        return disease;
    }
}
