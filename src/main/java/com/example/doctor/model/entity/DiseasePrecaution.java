package com.example.doctor.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class DiseasePrecaution implements Serializable {

    @EmbeddedId
    private DiseasePrecautionId keyId;

    @ManyToOne
    @MapsId("diseaseId")
    @JoinColumn(name = "disease_id")
    private Disease disease;

    @ManyToOne
    @MapsId("precautionId")
    @JoinColumn(name = "precaution_id")
    private Precaution precaution;

    private boolean status;

}
