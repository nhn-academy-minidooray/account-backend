package com.nhnacademy.minidooray.account.backend.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.minidooray.account.backend.domain.Status;
import com.nhnacademy.minidooray.account.backend.domain.dto.AccountPageInfoDTO;
import com.nhnacademy.minidooray.account.backend.domain.dto.AccountStatusInfoDTO;
import com.nhnacademy.minidooray.account.backend.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testGetAccountPageInfoById() {
        Account account = Account.builder()
                .id("test")
                .email("test@test.com")
                .password("1234")
                .name("tester")
                .status(Status.JOIN.getValue())
                .build();

        entityManager.merge(account);

        AccountPageInfoDTO result = accountRepository
                .getAccountPageInfoById("test")
                .orElse(null);

        assertEquals(result.getId(), "test");
    }

    @Test
    void testGetAccountStatusInfoByIdAndPassword() {
        Account account = Account.builder()
                .id("test")
                .email("test@test.com")
                .password("1234")
                .name("tester")
                .status(Status.JOIN.getValue())
                .build();

        entityManager.merge(account);

        AccountStatusInfoDTO result = accountRepository
                .getAccountStatusInfoByIdAndPassword("test", "1234")
                .orElse(null);

        assertEquals(result.getId(), "test");
    }

}