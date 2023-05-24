package com.example.doctor.controller;

import com.example.doctor.model.dto.AccountDTO;
import com.example.doctor.model.dto.MessageDTO;
import com.example.doctor.model.entity.Account;
import com.example.doctor.model.mapper.AccountMapper;
import com.example.doctor.service.AccountService;
import com.example.doctor.service.CustomUserDetails;
import com.example.doctor.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/change-password"})
public class ChangePasswordController {

    private static final String REDIRECT_URL = "/change-password";

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AccountMapper accountMapper;

    @GetMapping(value = {"","/"})
    public String view(Model model, Authentication authentication) {
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Account customAccount = customUserDetails.getAccount();
            Account account = accountService.findById(customAccount.getId());

            model.addAttribute("accountDTO", accountMapper.toDTO(account));

            return "change_password";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = {"", "/"})
    public String save(Model model, Authentication authentication, AccountDTO accountDTO, BindingResult bindingResult) {
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Account customAccount = customUserDetails.getAccount();
            accountDTO.setId(customAccount.getId());

            Account account = accountService.findById(customAccount.getId());

            // verify value
            passwordValidator.validate(accountDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("accountDTO", accountMapper.toDTO(account));
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "change_password";
            } else {
                // save
                String encodedPassword = passwordEncoder.encode(accountDTO.getNewPassword());
                account.setPassword(encodedPassword);
                accountService.save(account);

                String redirectUrl = "/logout";
                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

}
