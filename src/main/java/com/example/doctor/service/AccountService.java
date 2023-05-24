package com.example.doctor.service;

import com.example.doctor.model.dto.AccountDTO;
import com.example.doctor.model.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account findById(long id);

    Account findByUsername(String username);

    Account save(AccountDTO accountDTO);

    Account save(Account account);

    Account register(AccountDTO accountDTO);

}
