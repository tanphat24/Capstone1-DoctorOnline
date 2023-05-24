package com.example.doctor.validator;

import com.example.doctor.model.dto.DiseaseDTO;
import com.example.doctor.model.entity.Disease;
import com.example.doctor.service.DiseaseService;
import com.example.doctor.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DiseaseValidator implements Validator {

    @Autowired
    private DiseaseService diseaseService;

    @Override
    public boolean supports(Class<?> clazz) {
        return DiseaseDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DiseaseDTO diseaseDTO = (DiseaseDTO) target;

        if (ValidatorUtil.isEmpty(diseaseDTO.getName())) {
            errors.rejectValue("name", "Tên Bệnh không được để trống!",
                    "Tên Bệnh không được để trống!");
        } else {
            Disease disease = diseaseService.findByName(diseaseDTO.getName());
            if (disease != null && disease.getId() != diseaseDTO.getId()) {
                errors.rejectValue("name", "Tên Bệnh đã tồn tại!",
                        "Tên Bệnh đã tồn tại!");
            }
        }

        if (ValidatorUtil.isEmpty(diseaseDTO.getDescription())) {
            errors.rejectValue("description", "Mô tả Bệnh không được để trống!",
                    "Mô t Bệnh không được để trống!");
        }
    }
}
