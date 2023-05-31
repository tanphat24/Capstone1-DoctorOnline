package com.example.doctor.service.impl;

import com.example.doctor.model.entity.AccountDisease;
import com.example.doctor.model.entity.AccountHistory;
import com.example.doctor.repository.AccountDiseaseRepository;
import com.example.doctor.repository.custom.AccountDiseaseRepositoryCustom;
import com.example.doctor.service.AccountDiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AccountDiseaseServiceImpl implements AccountDiseaseService {

    @Autowired
    private AccountDiseaseRepository accountDiseaseRepository;

    @Autowired
    private AccountDiseaseRepositoryCustom accountDiseaseRepositoryCustom;

    @Override
    public List<AccountDisease> findAll() {
        return accountDiseaseRepository.findAll();
    }

    @Override
    public List<AccountDisease> findByHistory(AccountHistory history) {
        return accountDiseaseRepository.findByHistory(history);
    }

    @Override
    public HashMap<Long, Integer> findByCreatedOn(List<Long> ids, String startDate, String endDate) {
        return accountDiseaseRepositoryCustom.findByCreatedOn(ids,
                startDate + " 00:00:00",
                endDate+  " 23:59:59");
    }

    @Override
    public List<AccountDisease> save(List<AccountDisease> accountDiseaseList) {
        return accountDiseaseRepository.saveAll(accountDiseaseList);
    }

}
