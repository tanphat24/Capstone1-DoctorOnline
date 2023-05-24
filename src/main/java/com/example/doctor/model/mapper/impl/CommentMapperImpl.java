package com.example.doctor.model.mapper.impl;

import com.example.doctor.model.dto.CommentDTO;
import com.example.doctor.model.entity.Comment;
import com.example.doctor.model.mapper.AccountMapper;
import com.example.doctor.model.mapper.CommentMapper;
import com.example.doctor.model.mapper.DiseaseMapper;
import com.example.doctor.service.AccountService;
import com.example.doctor.service.CommentService;
import com.example.doctor.service.DiseaseService;
import com.example.doctor.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapperImpl implements CommentMapper {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private DiseaseMapper diseaseMapper;

    @Autowired
    private DiseaseService diseaseService;

    @Override
    public CommentDTO toDTO(Comment comment) {

        if (comment == null)
            return null;

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setAccountDTO(accountMapper.toDTO(comment.getAccount()));
        commentDTO.setAccountId(comment.getAccount().getId());
        commentDTO.setDiseaseDTO(diseaseMapper.toDTO(comment.getDisease()));
        commentDTO.setDiseaseId(comment.getDisease().getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setUpdatedOn(DateUtil.convertDateToString(comment.getUpdatedOn(),"dd/MM/yyyy HH:mm:ss"));
        commentDTO.setStatus(comment.isStatus());

        return commentDTO;
    }

    @Override
    public List<CommentDTO> toListDTO(List<Comment> comments) {

        if (comments == null)
            return null;

        List<CommentDTO> result = new ArrayList<>();
        comments.forEach(comment -> result.add(toDTO(comment)));

        return result;
    }

    @Override
    public Comment toEntity(CommentDTO commentDTO) {
        if (commentDTO == null)
            return null;

        Comment comment = commentService.findById(commentDTO.getId());

        if (comment == null)
            comment = new Comment();

        comment.setAccount(accountService.findById(commentDTO.getAccountId()));
        comment.setDisease(diseaseService.findById(commentDTO.getDiseaseId()));
        comment.setContent(commentDTO.getContent());
        comment.setStatus(commentDTO.isStatus());

        return comment;
    }
}
