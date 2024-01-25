package com.nhnacademy.minidooray.account.backend.service;

import com.nhnacademy.minidooray.account.backend.domain.AccountRegisterRequest;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfo;
import com.nhnacademy.minidooray.account.backend.entity.Account;
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
    public Account createAccount(AccountRegisterRequest request) {
        if(!accountRepository.existsById(request.getId())){
            throw new AccountAlreadyExistsException();
        }

        Account account = new Account(
                request.getId(),
                request.getPassword(),
                request.getEmail(),
                Account.Status.JOIN
        );

        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean matches(LoginInfo loginInfo) {
        if(!accountRepository.existsById(loginInfo.getId())){
            throw new AccountNotFoundException();
        }

        LoginInfo result = accountRepository.getLoginInfoById(loginInfo.getId());

        return Objects.equals(result.getPassword(), loginInfo.getPassword());
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

        if(Objects.equals(account.getStatus().getValue(), status)) {
            throw new AlreadyInStatusException();
        }

        accountRepository.updateStatus(id, Account.Status.fromValue(status));
    }

    private boolean isValidStatus(String status) {
        for (Account.Status validStatus : Account.Status.values()) {
            if (validStatus.getValue().equals(status)) {
                return true;
            }
        }

        return false;
    }
}

