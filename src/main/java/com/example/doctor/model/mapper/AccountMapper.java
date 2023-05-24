package com.example.doctor.model.mapper;

import com.example.doctor.model.dto.AccountDTO;
import com.example.doctor.model.entity.Account;

import java.util.List;

public interface AccountMapper {

    // Map Entity to DTO
    AccountDTO toDTO(Account account);

    List<AccountDTO> toListDTO(List<Account> accountList);

    // Map DTO to Entity
    Account toEntity(Account account, AccountDTO accountDTO);

}
