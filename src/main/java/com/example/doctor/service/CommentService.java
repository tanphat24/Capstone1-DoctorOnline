package com.example.doctor.service;

import com.example.doctor.model.entity.Account;
import com.example.doctor.model.entity.Comment;
import com.example.doctor.model.entity.Disease;

import java.util.HashMap;
import java.util.List;

public interface CommentService {

    List<Comment> findAll();

    Comment findById(long id);

    List<Comment> findByAccount(Account account);

    List<Comment> findByDisease(Disease disease);

    Comment save(Comment comment);

    HashMap<Long, Integer> findByDiseaseIds(List<Long> ids);

}
