package com.example.doctor.validator;

import com.example.doctor.model.dto.AccountDiseaseDTO;
import com.example.doctor.util.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


// test
//test2

import java.util.Date;

@Component
public class AccountDiseaseValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDiseaseDTO.class.isAssignableFrom(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        AccountDiseaseDTO accountDiseaseDTO = (AccountDiseaseDTO) target;

        if (accountDiseaseDTO.getStartDate() != null && !accountDiseaseDTO.getStartDate().trim().isEmpty() &&
                accountDiseaseDTO.getEndDate() != null && !accountDiseaseDTO.getEndDate().trim().isEmpty()) {
            Date dateStart = DateUtil.convertStringToDate(accountDiseaseDTO.getStartDate(), "yyyy-MM-dd");
            Date dateEnd = DateUtil.convertStringToDate(accountDiseaseDTO.getEndDate(), "yyyy-MM-dd");
            boolean isStartBeforeEnd = DateUtil.compareStartDateEndDate(dateStart, dateEnd);
            if (!isStartBeforeEnd) {
                errors.rejectValue("createdOn", "Thời gian bắt đầu lớn hơn thời gian kết thúc",
                        "Thời gian bắt đầu lớn hơn thời gian kết thúc");
            }
        }
    }
}
