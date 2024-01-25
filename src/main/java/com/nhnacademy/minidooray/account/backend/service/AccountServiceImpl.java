package com.nhnacademy.minidooray.account.backend.service;

import com.nhnacademy.minidooray.account.backend.domain.AccountPageInfoDto;
import com.nhnacademy.minidooray.account.backend.domain.AccountPageInfoRequest;
import com.nhnacademy.minidooray.account.backend.domain.AccountRegisterRequest;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfoRequest;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfoDto;
import com.nhnacademy.minidooray.account.backend.entity.Account;
import com.nhnacademy.minidooray.account.backend.domain.Status;
import com.nhnacademy.minidooray.account.backend.exception.AccountAlreadyExistsException;
import com.nhnacademy.minidooray.account.backend.exception.AccountNotFoundException;
import com.nhnacademy.minidooray.account.backend.repository.AccountRepository;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public void createAccount(AccountRegisterRequest request) {
        if(accountRepository.existsById(request.getId())){
            throw new AccountAlreadyExistsException();
        }

        Account account = Account.builder()
                .id(request.getId())
                .password(request.getPassword())
                .email(request.getEmail())
                .name(request.getName())
                .status(Status.JOIN.getValue())
                .build();

        accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean matches(LoginInfoRequest dto) {
        if(!accountRepository.existsById(dto.getId())){
            throw new AccountNotFoundException();
        }

        LoginInfoDto result = accountRepository.getLoginInfoById(dto.getId());

        return Objects.equals(result.getPassword(), dto.getPassword());
    }

    @Transactional(readOnly = true)
    @Override
    public AccountPageInfoDto getAccountPageInfo(String id) {
        return accountRepository.getAccountPageInfoById(id);
    }

    @Transactional
    @Override
    public void setDormantAccount(String id) {
        accountRepository.updateStatus(id, "휴면");
    }
}

