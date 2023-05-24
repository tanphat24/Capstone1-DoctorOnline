package com.example.doctor.service.impl;

import com.example.doctor.model.dto.DiseaseDTO;
import com.example.doctor.model.entity.Disease;
import com.example.doctor.repository.DiseaseRepository;
import com.example.doctor.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DiseaseServiceImpl implements DiseaseService {

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Override
    public List<Disease> findAll() {
        return diseaseRepository.findAll();
    }

    @Override
    public Disease findById(long id) {
        return diseaseRepository.findById(id).orElse(null);
    }

    @Override
    public Disease findByName(String name) {
        return diseaseRepository.findByName(name);
    }

    @Override
    public Disease save(DiseaseDTO diseaseDTO) {
        Disease disease = diseaseRepository.findById(diseaseDTO.getId()).orElse(null);
        if (disease == null) {
            disease = new Disease();
            disease.setStatus(true);
        }
        if (!StringUtils.isEmpty(diseaseDTO.getName())) {
            disease.setName(diseaseDTO.getName().trim());
        }
        if (!StringUtils.isEmpty(diseaseDTO.getDescription())) {
            disease.setDescription(diseaseDTO.getDescription().trim());
        }
        if (!StringUtils.isEmpty(diseaseDTO.isStatus())) {
            disease.setStatus(diseaseDTO.isStatus());
        }
        return diseaseRepository.save(disease);
    }

    @Override
    public Boolean delete(long id) {
        Disease disease = diseaseRepository.findById(id).orElse(null);
        if (disease == null) {
            return false;
        }
        disease.setStatus(false);
        diseaseRepository.save(disease);
        return null;
    }

    @Override
    public List<Disease> findDiseaseByIdIn(List<Long> ids) {
        return diseaseRepository.findDiseaseByIdIn(ids);
    }

}
