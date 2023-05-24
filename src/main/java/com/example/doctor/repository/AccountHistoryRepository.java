package com.example.doctor.repository;

import com.example.doctor.model.entity.Account;
import com.example.doctor.model.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {

    @Query(value = "SELECT a FROM AccountHistory a WHERE a.account = :account ORDER BY a.createdOn DESC")
    List<AccountHistory> findByAccount(Account account);

    @Query(value = "SELECT * FROM account_history WHERE created_on BETWEEN :createdOn AND :updatedOn ORDER BY created_on DESC",
            nativeQuery = true)
    List<AccountHistory> findByCreatedOn(String createdOn, String updatedOn);
}
