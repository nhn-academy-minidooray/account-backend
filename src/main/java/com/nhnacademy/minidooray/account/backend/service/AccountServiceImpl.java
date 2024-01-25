package com.nhnacademy.minidooray.account.backend.service;

import com.nhnacademy.minidooray.account.backend.domain.AccountRegisterRequestDTO;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfoDTO;
import com.nhnacademy.minidooray.account.backend.entity.Account;
import com.nhnacademy.minidooray.account.backend.domain.Status;
import com.nhnacademy.minidooray.account.backend.exception.AccountAlreadyExistsException;
import com.nhnacademy.minidooray.account.backend.exception.AccountNotFoundException;
import com.nhnacademy.minidooray.account.backend.exception.AlreadyInStatusException;
import com.nhnacademy.minidooray.account.backend.exception.InvalidAccountStatusException;
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
    public Account createAccount(AccountRegisterRequestDTO request) {
        if(accountRepository.existsById(request.getId())){
            throw new AccountAlreadyExistsException();
        }

        Account account = new Account(
                request.getId(),
                request.getPassword(),
                request.getEmail(),
                Status.JOIN.getValue()
        );

        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean matches(LoginInfoDTO loginInfoDTO) {
        if(!accountRepository.existsById(loginInfoDTO.getId())){
            throw new AccountNotFoundException();
        }

        LoginInfoDTO result = accountRepository.getLoginInfoById(loginInfoDTO.getId());

        return Objects.equals(result.getPassword(), loginInfoDTO.getPassword());
    }

    @Transactional(readOnly = true)
    @Override
    public Account getAccount(String id) {
        return accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
    }

    @Transactional
    @Override
    public void updateAccountStatus(String id, String status) {
        if(!isValidStatus(status)){
            throw new InvalidAccountStatusException();
        }

        Account account = getAccount(id);

        if(Objects.equals(account.getStatus(), status)) {
            throw new AlreadyInStatusException();
        }

        accountRepository.updateStatus(id, status);
    }

    private boolean isValidStatus(String status) {
        for (Status validStatus : Status.values()) {
            if (validStatus.getValue().equals(status)) {
                return true;
            }
        }

        return false;
    }
}

