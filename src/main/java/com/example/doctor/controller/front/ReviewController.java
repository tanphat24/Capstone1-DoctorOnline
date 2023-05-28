package com.example.doctor.controller.front;

import com.example.doctor.model.entity.*;
import com.example.doctor.model.mapper.CommentMapper;
import com.example.doctor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/front/review")
public class ReviewController {

    private String redirectUrl = "/front/review";

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Account account = customUserDetails.getAccount();

            List<Comment> commentList = commentService.findByAccount(account);
            model.addAttribute("commentList", commentMapper.toListDTO(commentList));
            return "front/review";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + redirectUrl;
        }
    }

}
