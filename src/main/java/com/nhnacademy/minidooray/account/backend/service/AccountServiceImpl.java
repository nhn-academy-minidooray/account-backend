package com.nhnacademy.minidooray.account.backend.service;

import com.nhnacademy.minidooray.account.backend.domain.dto.AccountPageInfoDTO;
import com.nhnacademy.minidooray.account.backend.domain.dto.AccountStatusInfoDTO;
import com.nhnacademy.minidooray.account.backend.domain.requestbody.AccountRegisterRequest;
import com.nhnacademy.minidooray.account.backend.domain.requestbody.LoginInfoRequest;
import com.nhnacademy.minidooray.account.backend.entity.Account;
import com.nhnacademy.minidooray.account.backend.domain.Status;
import com.nhnacademy.minidooray.account.backend.repository.AccountRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public boolean createAccount(AccountRegisterRequest request) {
        if(accountRepository.existsById(request.getId())){
            log.error("createAccount() : Already exists id {}", request.getId());

            return false;
        }

        Account account = Account.builder()
                .id(request.getId())
                .password(request.getPassword())
                .email(request.getEmail())
                .name(request.getName())
                .status(Status.JOIN.getValue())
                .build();

        accountRepository.save(account);

        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AccountStatusInfoDTO> matches(LoginInfoRequest request) {
        return accountRepository
                .getAccountStatusInfoByIdAndPassword(request.getId(), request.getPassword());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AccountPageInfoDTO> getAccountPageInfo(String id) {
        return accountRepository.getAccountPageInfoById(id);
    }

    @Transactional
    @Override
    public boolean setDormantAccount(LoginInfoRequest request) {
        Optional<Account> account = accountRepository.findById(request.getId());

        if(account.isEmpty()){
            log.error("setDormantAccount() : Not Found Account By {}", request.getId());

            return false;
        }

        if(accountRepository
                .getAccountStatusInfoByIdAndPassword(request.getId(), request.getPassword())
                .isEmpty()){
            log.error("setDormantAccount() : Not match id and password");

            return false;
        }

        if(Objects.equals(account.get().getStatus(), Status.DORMANT.getValue())
                || Objects.equals(account.get().getStatus(), Status.WITHDRAWAL.getValue())) {
            log.error("setDormantAccount() : Cannot set status");

            return false;
        }

        Account modifiedAccount = account.get();
        modifiedAccount.setStatus(Status.DORMANT.getValue());
        accountRepository.save(modifiedAccount);

        return true;
    }
}

