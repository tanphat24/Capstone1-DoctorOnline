package com.example.doctor.model.mapper.impl;

import com.example.doctor.model.dto.AccountDTO;
import com.example.doctor.model.dto.RoleDTO;
import com.example.doctor.model.entity.Account;
import com.example.doctor.model.entity.Role;
import com.example.doctor.model.mapper.AccountMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDTO toDTO(Account account) {
        if (account == null) {
            return null;
        }

        // account
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setFullName(account.getFullName());
        accountDTO.setAvatar(account.getAvatar());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setPhone(account.getPhone());
        accountDTO.setAddress(account.getAddress());
        accountDTO.setStatus(account.isStatus());

        // role
        RoleDTO roleDTO = new RoleDTO();
        Role role = account.getRole();
        if (role != null) {
            roleDTO.setId(role.getId());
            roleDTO.setDescription(role.getDescription());
            roleDTO.setName(role.getName());
            roleDTO.setStatus(role.isStatus());
        }
        accountDTO.setRoleDTO(roleDTO);

        return accountDTO;
    }

    @Override
    public List<AccountDTO> toListDTO(List<Account> accountList) {
        if (accountList == null)
            return null;

        List<AccountDTO> result = new ArrayList<>();
        accountList.forEach(element -> result.add(toDTO(element)));

        return result;
    }

    @Override
    public Account toEntity(Account account, AccountDTO accountDTO) {
        if (account == null) {
            return null;
        }

        // set account
        account.setUsername(accountDTO.getUsername());
        account.setPassword(accountDTO.getPassword());
        account.setStatus(accountDTO.isStatus());

        return account;
    }

}
