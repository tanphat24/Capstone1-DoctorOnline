package com.example.doctor.model.mapper.impl;

import com.example.doctor.model.dto.SymptomDTO;
import com.example.doctor.model.entity.Symptom;
import com.example.doctor.model.mapper.SymptomMapper;
import com.example.doctor.service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SymptomMapperImpl implements SymptomMapper {

    @Autowired
    private SymptomService symptomService;

    @Override
    public SymptomDTO toDTO(Symptom symptom) {

        if (symptom == null)
            return null;

        SymptomDTO symptomDTO = new SymptomDTO();
        symptomDTO.setId(symptom.getId());
        symptomDTO.setName(symptom.getName());
        symptomDTO.setStatus(symptom.isStatus());

        return symptomDTO;
    }

    @Override
    public List<SymptomDTO> toListDTO(List<Symptom> symptoms) {
        
        if (symptoms == null)
            return null;

        List<SymptomDTO> result = new ArrayList<>();
        symptoms.forEach(element -> result.add(toDTO(element)));

        return result;
    }

    @Override
    public Symptom toEntity(SymptomDTO symptomDTO) {

        if (symptomDTO == null)
            return null;

        Symptom symptom = symptomService.findById(symptomDTO.getId());

        if (symptom == null)
            symptom = new Symptom();
        symptom.setName(symptomDTO.getName());
        symptom.setStatus(symptomDTO.isStatus());

        return symptom;
    }

}
