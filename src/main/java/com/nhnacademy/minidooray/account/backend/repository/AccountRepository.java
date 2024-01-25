package com.nhnacademy.minidooray.account.backend.repository;

import com.nhnacademy.minidooray.account.backend.domain.LoginInfo;
import com.nhnacademy.minidooray.account.backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    LoginInfo getLoginInfoById(String id);

    Account updateStatusById(String id);
}
