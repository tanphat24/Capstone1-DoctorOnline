package com.example.doctor.controller.back;

import com.example.doctor.model.dto.CommentDTO;
import com.example.doctor.model.dto.MessageDTO;
import com.example.doctor.model.entity.Comment;
import com.example.doctor.model.mapper.CommentMapper;
import com.example.doctor.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/back/comments")
public class CommentController {

    private static final String REDIRECT_URL = "/back/comments/";

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<Comment> commentList = commentService.findAll();
            model.addAttribute("commentList", commentMapper.toListDTO(commentList));
            return "back/comment_list";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/form/{id}")
    public String edit(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            Comment comment = commentService.findById(id);
            model.addAttribute("commentDTO", commentMapper.toDTO(comment));
            if (action != null) {
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO(action,
                        status.equalsIgnoreCase("success") ?
                                "Cập nhật dữ liệu thành công!" :
                                "Vui lòng kiểm tra lại thông tin!"));
            }

            if (status != null && status.equalsIgnoreCase("success")) {
                model.addAttribute("status", "success");
            }

            return "back/comment_form";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping("/form/")
    public String save(CommentDTO commentDTO, Model model) {
        try {
            Comment comment = commentService.save( commentMapper.toEntity(commentDTO));
            String redirect = "/back/comments/form/" + comment.getId() + "?action=save&status=success";
            return "redirect:" + redirect;
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        Comment comment = commentService.findById(id);
        comment.setStatus(false);
        commentService.save(comment);
        return "redirect:" + REDIRECT_URL;
    }

}
