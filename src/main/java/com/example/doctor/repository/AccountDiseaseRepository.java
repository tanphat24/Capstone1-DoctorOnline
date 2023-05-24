package com.example.doctor.repository;

import com.example.doctor.model.entity.AccountDisease;
import com.example.doctor.model.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDiseaseRepository extends JpaRepository<AccountDisease, Long> {

    List<AccountDisease> findByHistory(AccountHistory history);

}
