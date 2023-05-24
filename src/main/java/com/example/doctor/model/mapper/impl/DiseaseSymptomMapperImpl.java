package com.example.doctor.model.mapper.impl;

import com.example.doctor.model.dto.DiseaseSymptomDTO;
import com.example.doctor.model.entity.DiseaseSymptom;
import com.example.doctor.model.entity.DiseaseSymptomId;
import com.example.doctor.model.mapper.DiseaseMapper;
import com.example.doctor.model.mapper.DiseaseSymptomMapper;
import com.example.doctor.model.mapper.SymptomMapper;
import com.example.doctor.service.DiseaseService;
import com.example.doctor.service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiseaseSymptomMapperImpl implements DiseaseSymptomMapper {

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private DiseaseMapper diseaseMapper;

    @Autowired
    private SymptomService precautionService;

    @Autowired
    private SymptomMapper precautionMapper;

    @Override
    public DiseaseSymptomDTO toDTO(DiseaseSymptom diseaseSymptom) {

        if (diseaseSymptom == null)
            return null;

        DiseaseSymptomDTO diseaseSymptomDTO = new DiseaseSymptomDTO();
        diseaseSymptomDTO.setDiseaseId(diseaseSymptom.getKeyId().getDiseaseId());
        diseaseSymptomDTO.setSymptomId(diseaseSymptom.getKeyId().getSymptomId());
        diseaseSymptomDTO.setSymptomDTO(precautionMapper.toDTO(diseaseSymptom.getSymptom()));
        diseaseSymptomDTO.setDiseaseDTO(diseaseMapper.toDTO(diseaseSymptom.getDisease()));
        diseaseSymptomDTO.setStatus(diseaseSymptom.isStatus());

        return diseaseSymptomDTO;
    }

    @Override
    public List<DiseaseSymptomDTO> toListDTO(List<DiseaseSymptom> diseaseSymptomList) {

        if (diseaseSymptomList == null)
            return null;

        List<DiseaseSymptomDTO> result = new ArrayList<>();
        diseaseSymptomList.forEach(
                diseaseSymptom -> {
                    result.add(toDTO(diseaseSymptom));
                }
        );

        return result;
    }

    @Override
    public DiseaseSymptom toEntity(DiseaseSymptomDTO diseaseSymptomDTO) {

        if (diseaseSymptomDTO == null)
            return null;

        DiseaseSymptom diseaseSymptom = new DiseaseSymptom();
        DiseaseSymptomId id = new DiseaseSymptomId();
        id.setSymptomId(diseaseSymptomDTO.getSymptomId());
        id.setDiseaseId(diseaseSymptomDTO.getDiseaseId());

        diseaseSymptom.setSymptom(precautionService.findById(diseaseSymptomDTO.getSymptomId()));
        diseaseSymptom.setDisease(diseaseService.findById(diseaseSymptomDTO.getDiseaseId()));
        diseaseSymptom.setKeyId(id);
        diseaseSymptom.setStatus(diseaseSymptomDTO.isStatus());

        return diseaseSymptom;
    }
    
}
