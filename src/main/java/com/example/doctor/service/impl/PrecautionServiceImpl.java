package com.example.doctor.service.impl;

import com.example.doctor.model.entity.Precaution;
import com.example.doctor.repository.PrecautionRepository;
import com.example.doctor.service.PrecautionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrecautionServiceImpl implements PrecautionService {

    @Autowired
    private PrecautionRepository precautionRepository;

    @Override
    public List<Precaution> findAll() {
        return precautionRepository.findAll();
    }

    @Override
    public Precaution findById(long id) {
        return precautionRepository.findById(id).orElse(null);
    }

    @Override
    public Precaution findByName(String name) {
        return precautionRepository.findByName(name);
    }

    @Override
    public Precaution save(Precaution precaution) {
        return precautionRepository.save(precaution);
    }

    @Override
    public List<Precaution> findPrecautionByIdIn(List<Long> ids) {
        return precautionRepository.findPrecautionByIdIn(ids);
    }

}
