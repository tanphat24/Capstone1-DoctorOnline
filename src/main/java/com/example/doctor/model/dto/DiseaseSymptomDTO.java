package com.example.doctor.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiseaseSymptomDTO {

    private long diseaseId;
    private DiseaseDTO diseaseDTO;

    private long symptomId;
    private SymptomDTO symptomDTO;

    private boolean status;

    private List<String> listSymptomId;
    
}
