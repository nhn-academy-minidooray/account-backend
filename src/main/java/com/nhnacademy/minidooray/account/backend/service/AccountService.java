package com.nhnacademy.minidooray.account.backend.service;

import com.nhnacademy.minidooray.account.backend.domain.AccountPageInfoDto;
import com.nhnacademy.minidooray.account.backend.domain.AccountRegisterRequest;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfoRequest;

public interface AccountService {

    void createAccount(AccountRegisterRequest request);

    boolean matches(LoginInfoRequest loginInfoRequest);

    AccountPageInfoDto getAccountPageInfo(String id);

    void setDormantAccount(String id);
}
