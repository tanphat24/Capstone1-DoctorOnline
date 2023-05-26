package com.example.doctor.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiseaseDTO {

    private long id;
    private String name;
    private String description;
    private boolean status;

}
