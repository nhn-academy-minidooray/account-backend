package com.nhnacademy.minidooray.account.backend.service;

import com.nhnacademy.minidooray.account.backend.domain.AccountPageInfoDto;
import com.nhnacademy.minidooray.account.backend.domain.AccountRegisterRequest;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfoRequest;
import com.nhnacademy.minidooray.account.backend.entity.Account;
import java.util.Optional;

public interface AccountService {

    Optional<Account> createAccount(AccountRegisterRequest request);

    boolean matches(LoginInfoRequest loginInfoRequest);

    Optional<AccountPageInfoDto> getAccountPageInfo(String id);

    int setDormantAccount(String id);
}
