package com.example.doctor.repository.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public HashMap<Long, Integer> findByDiseaseIds(List<Long> ids) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        String sql = "SELECT c.disease_id AS DISEASE_ID, COUNT(c.id) AS DISEASE_TOTAL FROM `comment` c WHERE c.disease_id IN (:ids) GROUP BY c.disease_id";

        mapSqlParameterSource.addValue("ids", ids);

        HashMap<Long, Integer> hashMap = new HashMap<>();

        namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, (rs, rowNum) ->
                hashMap.put(rs.getLong("DISEASE_ID"), rs.getInt("DISEASE_TOTAL")));

        return hashMap;
    }

}
