package com.example.doctor.model.mapper;

import com.example.doctor.model.dto.HistoryDiseasesDTO;
import com.example.doctor.model.entity.AccountHistory;

import java.util.List;

public interface HistoryDiseasesMapper {

    HistoryDiseasesDTO toDTO(AccountHistory accountHistory);

    List<HistoryDiseasesDTO> toListDTO(List<AccountHistory> accountHistory);
}
