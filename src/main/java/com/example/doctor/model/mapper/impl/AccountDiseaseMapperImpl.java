package com.example.doctor.model.mapper.impl;

import com.example.doctor.model.dto.AccountDiseaseDTO;
import com.example.doctor.model.entity.AccountDisease;
import com.example.doctor.model.entity.AccountDiseaseId;
import com.example.doctor.model.mapper.AccountDiseaseMapper;
import com.example.doctor.model.mapper.AccountHistoryMapper;
import com.example.doctor.model.mapper.DiseaseMapper;
import com.example.doctor.service.AccountHistoryService;
import com.example.doctor.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountDiseaseMapperImpl implements AccountDiseaseMapper {

    @Autowired
    private AccountHistoryMapper accountHistoryMapper;

    @Autowired
    private DiseaseMapper diseaseMapper;

    @Autowired
    private AccountHistoryService accountHistoryService;

    @Autowired
    private DiseaseService diseaseService;

    @Override
    public AccountDiseaseDTO toDTO(AccountDisease accountDisease) {

        if (accountDisease == null)
            return null;

        AccountDiseaseDTO accountDiseaseDTO = new AccountDiseaseDTO();
        accountDiseaseDTO.setDiseaseId(accountDisease.getKeyId().getDiseaseId());
        accountDiseaseDTO.setDiseaseDTO(diseaseMapper.toDTO(accountDisease.getDisease()));
        accountDiseaseDTO.setHistoryId(accountDisease.getKeyId().getHistoryId());
        accountDiseaseDTO.setHistoryDTO(accountHistoryMapper.toDTO(accountDisease.getHistory()));

        accountDiseaseDTO.setStatus(accountDisease.isStatus());

        return accountDiseaseDTO;
    }

    @Override
    public List<AccountDiseaseDTO> toListDTO(List<AccountDisease> accountDiseaseList) {

        if (accountDiseaseList == null)
            return null;

        List<AccountDiseaseDTO> result = new ArrayList<>();
        accountDiseaseList.forEach(
                accountDisease -> {
                    result.add(toDTO(accountDisease));
                }
        );

        return result;
    }

    @Override
    public AccountDisease toEntity(AccountDiseaseDTO accountDiseaseDTO) {

        if (accountDiseaseDTO == null)
            return null;

        AccountDisease accountDisease = new AccountDisease();
        AccountDiseaseId id = new AccountDiseaseId();
        id.setDiseaseId(accountDiseaseDTO.getDiseaseId());
        id.setHistoryId(accountDiseaseDTO.getHistoryId());

        accountDisease.setDisease(diseaseService.findById(accountDiseaseDTO.getDiseaseId()));
        accountDisease.setHistory(accountHistoryService.findById(accountDiseaseDTO.getHistoryId()));
        accountDisease.setKeyId(id);
        accountDisease.setStatus(accountDiseaseDTO.isStatus());

        return accountDisease;
    }
}
