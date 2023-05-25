package com.example.doctor.controller.back;

import com.example.doctor.model.entity.Account;
import com.example.doctor.model.entity.AccountHistory;
import com.example.doctor.model.entity.Comment;
import com.example.doctor.model.entity.Disease;
import com.example.doctor.service.AccountHistoryService;
import com.example.doctor.service.AccountService;
import com.example.doctor.service.CommentService;
import com.example.doctor.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/back/dashboard")
public class DashboardController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private AccountHistoryService accountHistoryService;

    @Autowired
    private CommentService commentService;

    @GetMapping(value = {"", "/"})
    public String homePage(Model model) {
        List<Account> accountList = accountService.findAll();
        List<Disease> diseaseList = diseaseService.findAll();
        List<AccountHistory> accountHistoryList = accountHistoryService.findAll();
        List<Comment> commentList = commentService.findAll();

        model.addAttribute("accountSize", accountList.size());
        model.addAttribute("diseaseSize", diseaseList.size());
        model.addAttribute("accountHistorySize", accountHistoryList.size());
        model.addAttribute("commentSize", commentList.size());
        return "back/dashboard";
    }

}
