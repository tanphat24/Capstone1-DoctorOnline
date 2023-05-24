package com.example.doctor.repository.custom;

import java.util.HashMap;
import java.util.List;

public interface CommentRepositoryCustom {

    HashMap<Long, Integer> findByDiseaseIds(List<Long> ids);

}
