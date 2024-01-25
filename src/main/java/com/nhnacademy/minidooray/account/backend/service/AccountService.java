package com.nhnacademy.minidooray.account.backend.service;

import com.nhnacademy.minidooray.account.backend.domain.AccountRegisterRequestDTO;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfoDTO;
import com.nhnacademy.minidooray.account.backend.entity.Account;

public interface AccountService {

    Account createAccount(AccountRegisterRequestDTO request);

    boolean matches(LoginInfoDTO loginInfoDTO);

    Account getAccount(String id);

    void updateAccountStatus(String id, String status);
}
