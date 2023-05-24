package com.example.doctor.controller.front;

import com.example.doctor.model.entity.Account;
import com.example.doctor.model.entity.AccountHistory;
import com.example.doctor.model.entity.Comment;
import com.example.doctor.service.AccountHistoryService;
import com.example.doctor.service.CommentService;
import com.example.doctor.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/front/home")
public class HomeController {

    @Autowired
    private AccountHistoryService accountHistoryService;

    @Autowired
    private CommentService commentService;

    @GetMapping(value = {"", "/"})
    public String homePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Account account = customUserDetails.getAccount();

        List<AccountHistory> accountHistoryList = accountHistoryService.findByAccount(account);
        List<Comment> commentList = commentService.findByAccount(account);

        model.addAttribute("accountHistorySize", accountHistoryList.size());
        model.addAttribute("commentSize", commentList.size());
        return "front/home";
    }

}
