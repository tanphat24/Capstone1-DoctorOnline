package com.example.doctor.repository.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AccountDiseaseRepositoryCustomImpl implements AccountDiseaseRepositoryCustom {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public HashMap<Long, Integer> findByCreatedOn(List<Long> ids, String startDate, String endDate) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        String query = "SELECT AD.disease_id AS DISEASE_ID, COUNT(*) AS DISEASE_TOTAL FROM `account_disease` AS AD " +
                "LEFT JOIN `account_history` AS AH ON AD.history_id = AH.id " +
                "WHERE AD.disease_id IN (:ids) AND AH.created_on BETWEEN :startDate AND :endDate " +
                "GROUP BY AD.disease_id";

        mapSqlParameterSource.addValue("ids", ids);
        mapSqlParameterSource.addValue("startDate", startDate);
        mapSqlParameterSource.addValue("endDate", endDate);

        HashMap<Long, Integer> hashMap = new HashMap<>();
        namedParameterJdbcTemplate.query(query, mapSqlParameterSource, (rs, rowNum) ->
                hashMap.put(rs.getLong("DISEASE_ID"), rs.getInt("DISEASE_TOTAL")));

        return hashMap;
    }
}
