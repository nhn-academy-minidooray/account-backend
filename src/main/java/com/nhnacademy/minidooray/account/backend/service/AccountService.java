package com.nhnacademy.minidooray.account.backend.service;

import com.nhnacademy.minidooray.account.backend.domain.dto.AccountPageInfoDTO;
import com.nhnacademy.minidooray.account.backend.domain.dto.AccountStatusInfoDTO;
import com.nhnacademy.minidooray.account.backend.domain.requestbody.AccountRegisterRequest;
import com.nhnacademy.minidooray.account.backend.domain.requestbody.LoginInfoRequest;
import java.util.Optional;

public interface AccountService {

    boolean createAccount(AccountRegisterRequest request);

    Optional<AccountStatusInfoDTO> matches(LoginInfoRequest loginInfoRequest);

    Optional<AccountPageInfoDTO> getAccountPageInfo(String id);

    boolean setDormantAccount(LoginInfoRequest request);
}
