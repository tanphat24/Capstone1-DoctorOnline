package com.example.doctor.controller.front;

import com.example.doctor.model.entity.Account;
import com.example.doctor.model.entity.AccountDisease;
import com.example.doctor.model.entity.AccountHistory;
import com.example.doctor.model.mapper.AccountHistoryMapper;
import com.example.doctor.model.mapper.DiseaseMapper;
import com.example.doctor.model.mapper.HistoryDiseasesMapper;
import com.example.doctor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/front/history")
public class HistoryController {

    private static final String REDIRECT_URL = "/front/home";

    @Autowired
    private AccountHistoryService historyService;

    @Autowired
    private HistoryDiseasesMapper historyDiseasesMapper;

    @GetMapping(value = {"", "/"})
    public String homePage(Model model, Authentication authentication) {
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Account account = customUserDetails.getAccount();

            List<AccountHistory> accountHistoryList = historyService.findByAccount(account);
            model.addAttribute("accountHistoryList",
                    historyDiseasesMapper.toListDTO(accountHistoryList));

            return "front/history";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

}
