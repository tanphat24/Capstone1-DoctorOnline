package com.example.doctor.repository;

import com.example.doctor.model.entity.Precaution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrecautionRepository extends JpaRepository<Precaution, Long> {

    Precaution findByName(String name);

    List<Precaution> findPrecautionByIdIn(List<Long> ids);

}
