package com.nhnacademy.minidooray.account.backend.repository;

import com.nhnacademy.minidooray.account.backend.domain.AccountPageInfoDto;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfoDto;
import com.nhnacademy.minidooray.account.backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, String> {
    LoginInfoDto getLoginInfoById(String id);

    @Modifying
    @Query("update Account a set a.status = ?2 where a.id = ?1")
    void updateStatus(String id, String status);

    AccountPageInfoDto getAccountPageInfoById(String id);
}
