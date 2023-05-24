package com.example.doctor.repository;

import com.example.doctor.model.entity.Disease;
import com.example.doctor.model.entity.DiseasePrecaution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseasePrecautionRepository extends JpaRepository<DiseasePrecaution, Long> {

    List<DiseasePrecaution> findByDiseaseAndStatusIsTrue(Disease disease);

}
