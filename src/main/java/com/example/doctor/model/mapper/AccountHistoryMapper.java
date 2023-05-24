package com.example.doctor.model.mapper;

import com.example.doctor.model.dto.AccountHistoryDTO;
import com.example.doctor.model.entity.AccountHistory;

import java.util.List;

public interface AccountHistoryMapper {

    // Map Entity to DTO
    AccountHistoryDTO toDTO(AccountHistory accountHistory);

    List<AccountHistoryDTO> toListDTO(List<AccountHistory> accountHistoryList);

    // Map DTO to Entity
    AccountHistory toEntity(AccountHistoryDTO accountHistoryDTO);

}
