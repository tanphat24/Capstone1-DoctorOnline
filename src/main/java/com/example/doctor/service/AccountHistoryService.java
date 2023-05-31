package com.example.doctor.service;

import com.example.doctor.model.dto.AccountHistoryDTO;
import com.example.doctor.model.entity.Account;
import com.example.doctor.model.entity.AccountHistory;

import java.util.List;

public interface AccountHistoryService {

    List<AccountHistory> findAll();

    AccountHistory findById(long id);

    List<AccountHistory> findByAccount(Account account);

    AccountHistory save(AccountHistory accountHistory);

    List<AccountHistory> findByCreatedOn(AccountHistoryDTO accountHistoryDTO);

}
