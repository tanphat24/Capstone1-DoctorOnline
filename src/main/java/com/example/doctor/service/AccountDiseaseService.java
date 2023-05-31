package com.example.doctor.service;

import com.example.doctor.model.entity.AccountDisease;
import com.example.doctor.model.entity.AccountHistory;

import java.util.HashMap;
import java.util.List;

public interface AccountDiseaseService {

    List<AccountDisease> findAll();

    List<AccountDisease> findByHistory(AccountHistory history);

    HashMap<Long, Integer> findByCreatedOn(List<Long> ids, String startDate, String endDate);

    List<AccountDisease> save(List<AccountDisease> accountDiseaseList);

}
