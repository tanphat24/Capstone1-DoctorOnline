package com.example.doctor.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "account_history")
public class AccountHistory extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private String disease;

    private boolean status;

}
