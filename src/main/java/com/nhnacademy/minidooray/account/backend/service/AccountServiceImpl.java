package com.nhnacademy.minidooray.account.backend.service;

import com.nhnacademy.minidooray.account.backend.domain.AccountPageInfoDto;
import com.nhnacademy.minidooray.account.backend.domain.AccountRegisterRequest;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfoRequest;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfoDto;
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
    public Optional<Account> createAccount(AccountRegisterRequest request) {
        if(accountRepository.existsById(request.getId())){
            log.error("createAccount() : Already exists id {}", request.getId());

            return Optional.empty();
        }

        Account account = Account.builder()
                .id(request.getId())
                .password(request.getPassword())
                .email(request.getEmail())
                .name(request.getName())
                .status(Status.JOIN.getValue())
                .build();

        accountRepository.save(account);

        return Optional.of(account);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean matches(LoginInfoRequest dto) {
        if(!accountRepository.existsById(dto.getId())){
            log.error("matches() : Not exist id {}", dto.getId());

            return false;
        }

        LoginInfoDto result = accountRepository.getLoginInfoById(dto.getId());

        return Objects.equals(result.getPassword(), dto.getPassword());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AccountPageInfoDto> getAccountPageInfo(String id) {
        AccountPageInfoDto result = accountRepository.getAccountPageInfoById(id);

        if(Objects.isNull(result)){
            log.error("getAccountPageInfo() : Not exist id {}", id);

            return Optional.empty();
        }

        return Optional.of(result);
    }

    @Transactional
    @Override
    public int setDormantAccount(String id) {
        Optional<Account> account = accountRepository.findById(id);

        if(account.isPresent() && !Objects.equals(account.get().getStatus(), "휴면")){
            accountRepository.updateStatus(id, "휴면");

            return 1;
        }

        log.error("setDormantAccount() : Not Found Account By {} or already set status '휴면'", id);

        return 0;
    }
}

