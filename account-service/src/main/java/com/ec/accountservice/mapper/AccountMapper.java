package com.ec.accountservice.mapper;

import account.AccountRequest;
import com.ec.accountservice.dto.AccountRequestDTO;
import com.ec.accountservice.model.Account;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class AccountMapper {

    public AccountRequestDTO toDTO(AccountRequest accountRequest) {
        return new AccountRequestDTO(
                UUID.fromString(accountRequest.getUserId()),
                accountRequest.getFirstName(),
                accountRequest.getLastName(),
                accountRequest.getAddress(),
                LocalDate.parse(accountRequest.getDateOfBirth())
        );
    }
}
