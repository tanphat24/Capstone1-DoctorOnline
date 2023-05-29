package com.example.doctor.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentDTO {

    private long id;

    private AccountDTO accountDTO;
    private long accountId;

    private DiseaseDTO diseaseDTO;
    private long diseaseId;

    private String content;

    private boolean status;
    private String updatedOn;
    private String createdOn;

    List<String> precautionDTOList;

}
