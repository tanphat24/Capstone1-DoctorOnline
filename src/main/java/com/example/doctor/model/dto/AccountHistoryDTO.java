package com.example.doctor.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountHistoryDTO {

    private long id;
    private AccountDTO accountDTO;
    private long accountId;
    private String disease;
    private boolean status;

    private String createdOn;
    private String updatedOn;
    private int sumAccount;

    private int sumAccountHistory;
    private int sumDisease;
    private boolean isBegin;

}
