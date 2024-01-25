package com.nhnacademy.minidooray.account.backend.service;

import com.nhnacademy.minidooray.account.backend.domain.AccountRegisterRequestDTO;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfoDTO;
import com.nhnacademy.minidooray.account.backend.entity.Account;

public interface AccountService {

    void createAccount(AccountRegisterRequestDTO request);

    boolean matches(LoginInfoDTO loginInfoDTO);

    Account getAccount(String id);

    void setDormantAccount(String id);
}
