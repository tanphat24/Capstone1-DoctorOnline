package com.example.doctor.repository.custom;

import java.util.HashMap;
import java.util.List;

public interface AccountDiseaseRepositoryCustom {

    HashMap<Long, Integer> findByCreatedOn(List<Long> ids, String startDate, String endDate);

}
