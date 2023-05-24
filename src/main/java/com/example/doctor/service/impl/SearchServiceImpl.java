package com.example.doctor.service.impl;

import com.example.doctor.model.dto.SearchDTO;
import com.example.doctor.model.entity.*;
import com.example.doctor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private AccountHistoryService accountHistoryService;

    @Autowired
    private AccountDiseaseService accountDiseaseService;


    @Autowired
    private DiseaseSymptomService diseaseSymptomService;

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private AccountService accountService;

    @Override
    public List<Disease> findDiseaseBySymptom(SearchDTO searchDTO) {
        if (searchDTO.getSymptomList() == null || searchDTO.getSymptomList().isEmpty()) {
            return null;
        }

        List<Long> symptomIds = searchDTO.getSymptomList().stream()
                .map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        List<Symptom> symptomList = symptomService.findSymptomByIdIn(symptomIds);
        List<DiseaseSymptom> diseaseSymptomList = diseaseSymptomService.findBySymptomIn(symptomList);

        HashMap<Long, Disease> diseaseHashMap = new HashMap<>();

        if (diseaseSymptomList != null) {
            for (DiseaseSymptom diseaseSymptom : diseaseSymptomList) {
                Disease disease = diseaseSymptom.getDisease();
                diseaseHashMap.put(disease.getId(), disease);
            }
        }

        Collection<Disease> values = diseaseHashMap.values();

        List<Disease> diseaseList = new ArrayList<>(values);

        return diseaseList;
    }

    @Override
    public boolean save(long accountId, SearchDTO searchDTO) {
        Account account = accountService.findById(accountId);

        List<Long> diseaseIds = Arrays.asList(searchDTO.getDiseaseList().split(","))
                .stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        List<Disease> diseaseList = diseaseService.findDiseaseByIdIn(diseaseIds);

        // save account history
        AccountHistory history = new AccountHistory();
        history.setAccount(account);
        history.setDisease(diseaseList.stream()
                .map(Disease::getName)
                .collect(Collectors.toList()).toString());
        history.setStatus(true);

        accountHistoryService.save(history);

        // save account disease
        List<AccountDisease> accountDiseaseList = new ArrayList<>();
        if (diseaseList != null) {
            for (Disease disease : diseaseList) {
                AccountDisease accountDisease = new AccountDisease();

                AccountDiseaseId accountDiseaseId = new AccountDiseaseId();
                accountDiseaseId.setDiseaseId(disease.getId());
                accountDiseaseId.setHistoryId(history.getId());
                accountDisease.setKeyId(accountDiseaseId);
                accountDisease.setDisease(disease);
                accountDisease.setHistory(history);
                accountDisease.setStatus(true);
                accountDiseaseList.add(accountDisease);
            }
            accountDiseaseService.save(accountDiseaseList);
        }

        return true;
    }

}
