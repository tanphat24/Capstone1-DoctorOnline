package com.example.doctor.controller;

import com.example.doctor.model.dto.AccountDTO;
import com.example.doctor.model.dto.MessageDTO;
import com.example.doctor.model.entity.Account;
import com.example.doctor.service.AccountService;
import com.example.doctor.util.RegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/forgot-password")
public class ForgotPasswordController {

    private String redirectUrl = "/forgot-password";

    @Autowired
    private AccountService accountService;

    @Autowired
    private RegisterValidator registerValidator;

    @GetMapping(value = {"", "/"})
    public String registerPage(Model model) {
        try {
            AccountDTO accountDTO = new AccountDTO();

            model.addAttribute("messageDTO", null);
            model.addAttribute("accountDTO", accountDTO);

            return "forgot_password";
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

    @PostMapping(value = {"", "/"})
    public String register(Model model, @Valid AccountDTO accountDTO, BindingResult bindingResult) {
        try {
            // validate
            registerValidator.validate(accountDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("messageDTO", new MessageDTO("error",
                        "Vui lòng kiểm tra lại thông tin!"));
                model.addAttribute("accountDTO", accountDTO);
                return "forgot_password";
            } else {
                // save
                Account account = accountService.register(accountDTO);

                if (account != null) {
                    redirectUrl = "/message/register?username=" + account.getUsername();
                    return "redirect:" + redirectUrl;
                }

                model.addAttribute("messageDTO", new MessageDTO("error",
                        "Vui lòng kiểm tra lại thông tin!"));
                model.addAttribute("accountDTO", accountDTO);
                return "forgot_password";
            }
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

}
