package com.example.doctor.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class AccountDisease implements Serializable {

    @EmbeddedId
    private AccountDiseaseId keyId;

    @ManyToOne
    @MapsId("historyId")
    @JoinColumn(name = "history_id")
    private AccountHistory history;

    @ManyToOne
    @MapsId("diseaseId")
    @JoinColumn(name = "disease_id")
    private Disease disease;

    private boolean status;

}
