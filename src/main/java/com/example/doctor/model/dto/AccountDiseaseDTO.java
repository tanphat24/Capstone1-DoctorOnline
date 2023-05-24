package com.example.doctor.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountDiseaseDTO {

    private AccountHistoryDTO historyDTO;
    private long historyId;

    private DiseaseDTO diseaseDTO;
    private long diseaseId;

    private List<DiseasePrecautionDTO> precautionDTOList;
    private List<DiseaseSymptomDTO> symptomDTOList;

    private String startDate;
    private String endDate;

    private int diseaseCount;
    private boolean status;

}
