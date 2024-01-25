package com.nhnacademy.minidooray.account.backend.repository;

import com.nhnacademy.minidooray.account.backend.domain.LoginInfoDTO;
import com.nhnacademy.minidooray.account.backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("select new com.nhnacademy.minidooray.account.backend.domain.LoginInfoDTO(a.id, a.password) from Account a where a.id = ?1")
    LoginInfoDTO getLoginInfoById(String id);

    @Modifying
    @Query("update Account a set a.status = ?2 where a.id = ?1")
    void updateStatus(String id, String status);
}
