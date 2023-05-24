package com.example.doctor.model.mapper.impl;

import com.example.doctor.model.dto.AccountDiseaseDTO;
import com.example.doctor.model.dto.HistoryDiseasesDTO;
import com.example.doctor.model.entity.AccountHistory;
import com.example.doctor.model.entity.Disease;
import com.example.doctor.model.mapper.*;
import com.example.doctor.service.AccountDiseaseService;
import com.example.doctor.service.DiseasePrecautionService;
import com.example.doctor.service.DiseaseSymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryDiseasesMapperImpl implements HistoryDiseasesMapper {

    @Autowired
    private AccountHistoryMapper historyMapper;

    @Autowired
    private AccountDiseaseService accountDiseaseService;

    @Autowired
    private AccountDiseaseMapper accountDiseaseMapper;

    @Autowired
    private DiseasePrecautionService precautionService;

    @Autowired
    private DiseasePrecautionMapper precautionMapper;

    @Autowired
    private DiseaseSymptomService symptomService;

    @Autowired
    private DiseaseSymptomMapper symptomMapper;

    @Autowired
    private DiseaseMapper diseaseMapper;

    @Override
    public HistoryDiseasesDTO toDTO(AccountHistory accountHistory) {
        if (accountHistory == null)
            return null;

        HistoryDiseasesDTO historyDiseasesDTO = new HistoryDiseasesDTO();
        historyDiseasesDTO.setHistoryId(accountHistory.getId());
        historyDiseasesDTO.setHistoryDTO(historyMapper.toDTO(accountHistory));

        List<AccountDiseaseDTO> accountDiseaseDTOList = accountDiseaseMapper.toListDTO(
                     accountDiseaseService.findByHistory(accountHistory));

        accountDiseaseDTOList.forEach(
                accountDiseaseDTO -> {
                    Disease disease = diseaseMapper.toEntity(accountDiseaseDTO.getDiseaseDTO());
                    accountDiseaseDTO.setSymptomDTOList(
                            symptomMapper.toListDTO(symptomService.findByDisease(disease)));
                    accountDiseaseDTO.setPrecautionDTOList(
                            precautionMapper.toListDTO(precautionService.findByDisease(disease))
                    );
                }
        );

        historyDiseasesDTO.setDiseaseDTOList(accountDiseaseDTOList);

        return historyDiseasesDTO;
    }

    @Override
    public List<HistoryDiseasesDTO> toListDTO(List<AccountHistory> accountHistory) {

        if (accountHistory == null)
            return null;

        List<HistoryDiseasesDTO> result = new ArrayList<>();
        accountHistory.forEach(history -> result.add(toDTO(history)));

        return result;
    }
}
