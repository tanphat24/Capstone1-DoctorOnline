package com.example.doctor.controller;

import com.example.doctor.model.dto.AccountDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = { "/error" })
public class ErrorController {

    @GetMapping(value = {"/login"})
    public String redirectLoginFaile(Model model, @RequestParam(required = false) String username) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(username);
        accountDTO.setPassword("");

        model.addAttribute("accountDTO", accountDTO);
        model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");

        return "login";
    }

}
