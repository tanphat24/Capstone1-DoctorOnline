package com.example.doctor.model.mapper;

import com.example.doctor.model.dto.AccountDiseaseDTO;
import com.example.doctor.model.entity.AccountDisease;

import java.util.List;

public interface AccountDiseaseMapper {

    AccountDiseaseDTO toDTO(AccountDisease accountDisease);

    List<AccountDiseaseDTO> toListDTO(List<AccountDisease> accountDiseaseList);

    AccountDisease toEntity(AccountDiseaseDTO accountDiseaseDTO);

}
