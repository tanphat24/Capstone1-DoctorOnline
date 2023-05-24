package com.example.doctor.model.mapper;

import com.example.doctor.model.dto.CommentDTO;
import com.example.doctor.model.entity.Comment;

import java.util.List;

public interface CommentMapper {

    CommentDTO toDTO(Comment comment);

    List<CommentDTO> toListDTO(List<Comment> comments);

    Comment toEntity(CommentDTO commentDTO);

}
