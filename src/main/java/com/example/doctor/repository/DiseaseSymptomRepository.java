package com.example.doctor.repository;

import com.example.doctor.model.entity.Disease;
import com.example.doctor.model.entity.DiseaseSymptom;
import com.example.doctor.model.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseSymptomRepository extends JpaRepository<DiseaseSymptom, Long> {

    List<DiseaseSymptom> findByDiseaseAndStatusIsTrue(Disease disease);

    List<DiseaseSymptom> findBySymptomIn(List<Symptom> symptomList);

}
