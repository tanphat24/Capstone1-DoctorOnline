package com.example.doctor.validator;

import com.example.doctor.model.dto.SymptomDTO;
import com.example.doctor.model.entity.Symptom;
import com.example.doctor.service.SymptomService;
import com.example.doctor.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SymptomValidator implements Validator {

    @Autowired
    private SymptomService symptomService;

    @Override
    public boolean supports(Class<?> clazz) {
        return SymptomDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SymptomDTO symptomDTO = (SymptomDTO) target;

        if (ValidatorUtil.isEmpty(symptomDTO.getName())) {
            errors.rejectValue("name", "Tên Triệu Chứng không được để trống!",
                    "Tên Triệu Chứng không được để trống!");
        } else {
            Symptom symptom = symptomService.findByName(symptomDTO.getName());
            if (symptom != null && symptom.getId() != symptomDTO.getId()) {
                errors.rejectValue("name", "Tên Triệu Chứng đã tồn tại!",
                        "Tên Triệu Chứng đã tồn tại!");
            }
        }
    }
}
