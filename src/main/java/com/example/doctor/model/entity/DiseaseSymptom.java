package com.example.doctor.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class DiseaseSymptom implements Serializable {

    @EmbeddedId
    private DiseaseSymptomId keyId;

    @ManyToOne
    @MapsId("diseaseId")
    @JoinColumn(name = "disease_id")
    private Disease disease;

    @ManyToOne
    @MapsId("symptomId")
    @JoinColumn(name = "symptom_id")
    private Symptom symptom;

    private boolean status;

}
