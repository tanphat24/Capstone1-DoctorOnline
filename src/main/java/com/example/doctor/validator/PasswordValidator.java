package com.example.doctor.validator;

import com.example.doctor.model.dto.AccountDTO;
import com.example.doctor.model.entity.Account;
import com.example.doctor.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountDTO accountDTO = (AccountDTO) target;
        Account account = accountService.findById(accountDTO.getId());

        // verify old password
        if(accountDTO.getOldPassword() == null || accountDTO.getOldPassword().trim().isEmpty()){
            errors.rejectValue("oldPassword", "Vui lòng nhập mật khẩu cũ!",
                    "Vui lòng nhập mật khẩu cũ!");
        }else{
            if (!passwordEncoder.matches(accountDTO.getOldPassword(), account.getPassword())) {
                errors.rejectValue("oldPassword", "Mật khẩu cũ không đúng!",
                        "Mật khẩu cũ không đúng!");
            }
        }

        // verify new password
        if(accountDTO.getNewPassword() == null || accountDTO.getNewPassword().trim().isEmpty()){
            errors.rejectValue("newPassword", "Vui lòng nhập mật khẩu mới!",
                    "Vui lòng nhập mật khẩu mới!");
        }else{
            if (accountDTO.getNewPassword().length() < 5) {
                errors.rejectValue("newPassword", "Mật khẩu cần ít nhất 8 ký tự!",
                        "Mật khẩu cần ít nhất 8 ký tự!");
            }
        }

        // verify new password again
        if (accountDTO.getConfirmPassword() == null || accountDTO.getConfirmPassword().trim().isEmpty()) {
            errors.rejectValue("confirmPassword", "Vui lòng nhập lại mật khẩu mới!",
                    "Vui lòng nhập lại mật khẩu mới!");
        } else {
            if (!accountDTO.getNewPassword().equalsIgnoreCase(accountDTO.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "Mật khẩu không trùng khớp!",
                        "Mật khẩu không trùng khớp!");
            }
        }
    }
}
