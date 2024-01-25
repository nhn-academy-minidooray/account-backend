package com.nhnacademy.minidooray.account.backend.repository;

import com.nhnacademy.minidooray.account.backend.domain.LoginInfo;
import com.nhnacademy.minidooray.account.backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, String> {
    LoginInfo getLoginInfoById(String id);

    @Modifying
    @Query("update Account a set a.status = ?2 where a.id = ?1")
    void updateStatus(String id, Account.Status status);
}
