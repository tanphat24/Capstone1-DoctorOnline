package com.example.doctor.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiseasePrecautionDTO {

    private long diseaseId;
    private DiseaseDTO diseaseDTO;

    private long precautionId;
    private PrecautionDTO precautionDTO;

    private boolean status;

    private List<String> listPrecautionId;

}
