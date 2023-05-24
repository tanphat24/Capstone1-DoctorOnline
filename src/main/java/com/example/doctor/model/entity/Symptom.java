package com.example.doctor.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "symptom")
public class Symptom extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private boolean status;

}
