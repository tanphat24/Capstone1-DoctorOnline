package com.example.doctor.service.impl;

import com.example.doctor.model.entity.Account;
import com.example.doctor.model.entity.Comment;
import com.example.doctor.model.entity.Disease;
import com.example.doctor.repository.CommentRepository;
import com.example.doctor.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAllByOrderByUpdatedOnDesc();
    }

    @Override
    public Comment findById(long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Comment> findByAccount(Account account) {
        return commentRepository.findByAccount(account);
    }

    @Override
    public List<Comment> findByDisease(Disease disease) {
        return commentRepository.findByDiseaseAndStatusIsTrue(disease);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public HashMap<Long, Integer> findByDiseaseIds(List<Long> ids) {
        return commentRepository.findByDiseaseIds(ids);
    }

}
