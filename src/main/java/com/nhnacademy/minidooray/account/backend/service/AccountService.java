package com.nhnacademy.minidooray.account.backend.service;

import com.nhnacademy.minidooray.account.backend.domain.AccountRegisterRequest;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfo;
import com.nhnacademy.minidooray.account.backend.entity.Account;

public interface AccountService {

    Account createAccount(AccountRegisterRequest request);

    boolean matches(LoginInfo loginInfo);

    Account getAccount(String id);

    void updateAccountStatus(String id, String status);
}
