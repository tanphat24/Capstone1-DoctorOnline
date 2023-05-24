package com.example.doctor.validator;

import com.example.doctor.model.dto.AccountHistoryDTO;
import com.example.doctor.util.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class AccountHistoryValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AccountHistoryDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountHistoryDTO accountHistoryDTO = (AccountHistoryDTO) target;

        if (accountHistoryDTO.getCreatedOn() != null && !accountHistoryDTO.getCreatedOn().trim().isEmpty() &&
                accountHistoryDTO.getUpdatedOn() != null && !accountHistoryDTO.getUpdatedOn().trim().isEmpty()) {
            Date dateStart = DateUtil.convertStringToDate(accountHistoryDTO.getCreatedOn(), "yyyy-MM-dd");
            Date dateEnd = DateUtil.convertStringToDate(accountHistoryDTO.getUpdatedOn(), "yyyy-MM-dd");
            boolean isStartBeforeEnd = DateUtil.compareStartDateEndDate(dateStart, dateEnd);
            if (!isStartBeforeEnd) {
                errors.rejectValue("createdOn", "Thời gian bắt đầu lớn hơn thời gian kết thúc",
                        "Thời gian bắt đầu lớn hơn thời gian kết thúc");
            }
        }

    }
}
