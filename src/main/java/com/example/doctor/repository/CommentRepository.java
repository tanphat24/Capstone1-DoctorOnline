package com.example.doctor.repository;

import com.example.doctor.model.entity.Account;
import com.example.doctor.model.entity.Comment;
import com.example.doctor.model.entity.Disease;
import com.example.doctor.repository.custom.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    List<Comment> findAllByOrderByUpdatedOnDesc();

    List<Comment> findByAccount(Account account);

    List<Comment> findByDiseaseAndStatusIsTrue(Disease disease);

}
