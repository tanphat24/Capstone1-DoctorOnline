package com.example.doctor.repository;

import com.example.doctor.model.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    Disease findByName(String name);

    List<Disease> findDiseaseByIdIn(List<Long> ids);

}
