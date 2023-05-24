package com.example.doctor.repository;

import com.example.doctor.model.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {

    Symptom findByName(String name);
    
    List<Symptom> findByStatusIsTrue();

    List<Symptom> findSymptomByIdIn(List<Long> ids);

}
