package com.example.doctor.validator;

import com.example.doctor.model.dto.PrecautionDTO;
import com.example.doctor.model.entity.Precaution;
import com.example.doctor.service.PrecautionService;
import com.example.doctor.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PrecautionValidator implements Validator {

    @Autowired
    private PrecautionService precautionService;

    @Override
    public boolean supports(Class<?> clazz) {
        return PrecautionDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PrecautionDTO precautionDTO = (PrecautionDTO) target;

        if (ValidatorUtil.isEmpty(precautionDTO.getName())) {
            errors.rejectValue("name", "Tên Phương Pháp không được để trống!",
                    "Tên Phương Pháp không được để trống!");
        } else {
            Precaution precaution = precautionService.findByName(precautionDTO.getName());
            if (precaution != null && precaution.getId() != precautionDTO.getId()) {
                errors.rejectValue("name", "Tên Phương Pháp đã tồn tại!",
                        "Tên Phương Pháp đã tồn tại!");
            }
        }
    }
}
