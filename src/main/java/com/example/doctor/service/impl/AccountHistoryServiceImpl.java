package com.example.doctor.service.impl;

import com.example.doctor.model.dto.AccountHistoryDTO;
import com.example.doctor.model.entity.Account;
import com.example.doctor.model.entity.AccountHistory;
import com.example.doctor.repository.AccountHistoryRepository;
import com.example.doctor.service.AccountHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountHistoryServiceImpl implements AccountHistoryService {

    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

    @Override
    public List<AccountHistory> findAll() {
        return accountHistoryRepository.findAll();
    }

    @Override
    public AccountHistory findById(long id) {
        return accountHistoryRepository.findById(id).orElse(null);
    }

    public List<AccountHistory> findByAccount(Account account) {
        return accountHistoryRepository.findByAccount(account);
    }

    @Override
    public AccountHistory save(AccountHistory accountHistory) {
        return accountHistoryRepository.save(accountHistory);
    }

    @Override
    public List<AccountHistory> findByCreatedOn(AccountHistoryDTO accountHistoryDTO) {
        String createdOn = accountHistoryDTO.getCreatedOn() + " 00:00:00";
        String updatedOn = accountHistoryDTO.getUpdatedOn() +  " 23:59:59";
        return accountHistoryRepository.findByCreatedOn(createdOn, updatedOn);
    }


}
