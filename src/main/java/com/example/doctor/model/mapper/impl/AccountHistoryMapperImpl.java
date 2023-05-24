package com.example.doctor.model.mapper.impl;

import com.example.doctor.model.dto.AccountDTO;
import com.example.doctor.model.dto.AccountHistoryDTO;
import com.example.doctor.model.dto.DiseaseDTO;
import com.example.doctor.model.entity.Account;
import com.example.doctor.model.entity.AccountHistory;
import com.example.doctor.model.entity.Disease;
import com.example.doctor.model.mapper.AccountHistoryMapper;
import com.example.doctor.model.mapper.AccountMapper;
import com.example.doctor.service.AccountHistoryService;
import com.example.doctor.service.AccountService;
import com.example.doctor.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountHistoryMapperImpl implements AccountHistoryMapper {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountHistoryService accountHistoryService;
    @Autowired
    private AccountService accountService;

    @Override
    public AccountHistoryDTO toDTO(AccountHistory accountHistory) {
        if (accountHistory == null) {
            return null;
        }

        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        accountHistoryDTO.setId(accountHistory.getId());
        accountHistoryDTO.setDisease(accountHistory.getDisease());
        accountHistoryDTO.setAccountDTO(accountMapper.toDTO(accountHistory.getAccount()));
        accountHistoryDTO.setAccountId(accountHistory.getAccount().getId());
        accountHistoryDTO.setCreatedOn(DateUtil.convertDateToString(accountHistory.getCreatedOn(), "dd-MM-yyyy HH:mm"));
        accountHistoryDTO.setUpdatedOn(DateUtil.convertDateToString(accountHistory.getUpdatedOn(), "dd-MM-yyyy HH:mm"));
        accountHistoryDTO.setStatus(accountHistory.isStatus());

        return accountHistoryDTO;
    }

    @Override
    public List<AccountHistoryDTO> toListDTO(List<AccountHistory> accountHistoryList) {
        if (accountHistoryList == null)
            return null;

        List<AccountHistoryDTO> result = new ArrayList<>();
        accountHistoryList.forEach(element -> result.add(toDTO(element)));

        return result;
    }

    @Override
    public AccountHistory toEntity(AccountHistoryDTO accountHistoryDTO) {
        if (accountHistoryDTO == null)
            return null;

        AccountHistory accountHistory = accountHistoryService.findById(accountHistoryDTO.getId());

        if (accountHistory == null)
            accountHistory = new AccountHistory();
        accountHistory.setDisease(accountHistoryDTO.getDisease());
        accountHistory.setAccount(accountService.findById(accountHistoryDTO.getAccountId()));
        accountHistory.setStatus(accountHistoryDTO.isStatus());

        return accountHistory;
    }
}
