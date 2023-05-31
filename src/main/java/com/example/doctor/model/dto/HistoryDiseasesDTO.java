package com.example.doctor.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HistoryDiseasesDTO {

    private long historyId;
    private AccountHistoryDTO historyDTO;

    private List<AccountDiseaseDTO> diseaseDTOList;

}
