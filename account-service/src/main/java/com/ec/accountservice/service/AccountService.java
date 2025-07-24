package com.ec.accountservice.service;

import com.ec.accountservice.dto.AccountRequestDTO;
import com.ec.accountservice.exception.UserAlreadyExistsException;
import com.ec.accountservice.model.Account;
import com.ec.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String register(AccountRequestDTO request) {
        if (accountRepository.findByUserId(request.getUserId()).isPresent()) {
            throw new UserAlreadyExistsException("User is already registered");
        }

        Account account = new Account();
        account.setUserId(request.getUserId());
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setAddress(request.getAddress());
        account.setDateOfBirth(request.getDateOfBirth());

        Account newAccount = accountRepository.save(account);

        return newAccount.getId().toString();
    }
}
