package com.nhnacademy.minidooray.account.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidooray.account.backend.domain.Status;
import com.nhnacademy.minidooray.account.backend.domain.dto.AccountPageInfoDTO;
import com.nhnacademy.minidooray.account.backend.domain.dto.AccountStatusInfoDTO;
import com.nhnacademy.minidooray.account.backend.domain.requestbody.AccountRegisterRequest;
import com.nhnacademy.minidooray.account.backend.domain.requestbody.LoginInfoRequest;
import com.nhnacademy.minidooray.account.backend.entity.Account;
import com.nhnacademy.minidooray.account.backend.repository.AccountRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountStatusInfoDTO accountStatusInfoDTO;

    @Mock
    private AccountPageInfoDTO accountPageInfoDTO;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    @DisplayName("계정 생성 성공")
    void testCreateAccountSuccess() {
        AccountRegisterRequest request = new AccountRegisterRequest("test", "1234", "test@naver.com", "tester");

        when(accountRepository.existsById(request.getId()))
                .thenReturn(false);

        boolean result = accountService.createAccount(request);

        assertTrue(result);

        verify(accountRepository)
                .existsById(request.getId());
        verify(accountRepository)
                .save(any(Account.class));
    }

    @Test
    @DisplayName("이미 존재하는 계정일 경우, 계정 생성 실패")
    void testCreateAccountFailed() {
        AccountRegisterRequest request = new AccountRegisterRequest("test", "1234", "test@naver.com", "tester");

        when(accountRepository.existsById(request.getId()))
                .thenReturn(true);

        boolean result = accountService.createAccount(request);

        assertFalse(result);

        verify(accountRepository)
                .existsById(request.getId());
        verify(accountRepository, never())
                .save(any(Account.class));
    }

    @Test
    void testMatches() {
        LoginInfoRequest request = new LoginInfoRequest("test", "1234");

        when(accountRepository.getAccountStatusInfoByIdAndPassword(request.getId(), request.getPassword()))
                .thenReturn(Optional.of(accountStatusInfoDTO));

        Optional<AccountStatusInfoDTO> result = accountService.matches(request);

        assertTrue(result.isPresent());
        assertEquals(accountStatusInfoDTO, result.get());

        verify(accountRepository)
                .getAccountStatusInfoByIdAndPassword(request.getId(), request.getPassword());
    }

    @Test
    void testGetAccountPageInfo() {
        when(accountRepository.getAccountPageInfoById("test"))
                .thenReturn(Optional.of(accountPageInfoDTO));

        Optional<AccountPageInfoDTO> result = accountService.getAccountPageInfo("test");

        assertTrue(result.isPresent());
        assertEquals(accountPageInfoDTO, result.get());

        verify(accountRepository)
                .getAccountPageInfoById("test");
    }

    @Test
    @DisplayName("휴면 상태로 전환 성공")
    void testSetDormantAccountSuccess() {
        Account account = Account.builder()
                .id("test")
                .email("test@test.com")
                .password("1234")
                .name("tester")
                .status(Status.JOIN.getValue())
                .build();

        LoginInfoRequest request = new LoginInfoRequest("test", "1234");

        when(accountRepository.findById(request.getId()))
                .thenReturn(Optional.of(account));
        when(accountRepository
                .getAccountStatusInfoByIdAndPassword(request.getId(), request.getPassword()))
                .thenReturn(Optional.of(accountStatusInfoDTO));


        boolean result = accountService.setDormantAccount(request);

        assertEquals(Status.DORMANT.getValue(), account.getStatus());
        assertTrue(result);

        verify(accountRepository)
                .findById(request.getId());
        verify(accountRepository)
                .getAccountStatusInfoByIdAndPassword(request.getId(), request.getPassword());
        verify(accountRepository)
                .save(account);

    }

    @Test
    @DisplayName("계정을 찾지 못한 경우, 상태 전환 실패")
    void testSetDormantAccountWhenAccountNotFound(){
        LoginInfoRequest request = new LoginInfoRequest("test", "1234");

        when(accountRepository.findById(request.getId()))
                .thenReturn(Optional.empty());

        boolean result = accountService.setDormantAccount(request);

        assertFalse(result);

        verify(accountRepository)
                .findById(request.getId());
    }

    @Test
    @DisplayName("로그인 정보 불일치할 경우, 상태 전환 실패")
    void testSetDormantAccountWhenLoginInfoMismatch() {
        Account account = Account.builder()
                .id("test")
                .email("test@test.com")
                .password("1234")
                .name("tester")
                .status(Status.JOIN.getValue())
                .build();

        LoginInfoRequest request = new LoginInfoRequest("test", "12345");

        when(accountRepository.findById(request.getId()))
                .thenReturn(Optional.of(account));
        when(accountRepository
                .getAccountStatusInfoByIdAndPassword(request.getId(), request.getPassword()))
                .thenReturn(Optional.empty());


        boolean result = accountService.setDormantAccount(request);

        assertFalse(result);

        verify(accountRepository)
                .findById(request.getId());
        verify(accountRepository)
                .getAccountStatusInfoByIdAndPassword(request.getId(), request.getPassword());
    }

    @Test
    @DisplayName("이미 휴면 상태이거나 탈퇴 계정일 경우, 상태 전환 실패")
    void testSetDormantAccountWhenAccountAlreadyDormantOrWithdrawn() {
        Account account = Account.builder()
                .id("test")
                .email("test@test.com")
                .password("1234")
                .name("tester")
                .status(Status.WITHDRAWAL.getValue())
                .build();

        LoginInfoRequest request = new LoginInfoRequest("test", "1234");

        when(accountRepository.findById(request.getId()))
                .thenReturn(Optional.of(account));
        when(accountRepository
                .getAccountStatusInfoByIdAndPassword(request.getId(), request.getPassword()))
                .thenReturn(Optional.of(accountStatusInfoDTO));


        boolean result = accountService.setDormantAccount(request);

        assertEquals(Status.WITHDRAWAL.getValue(), account.getStatus());
        assertFalse(result);

        verify(accountRepository)
                .findById(request.getId());
        verify(accountRepository)
                .getAccountStatusInfoByIdAndPassword(request.getId(), request.getPassword());
        verify(accountRepository, never())
                .save(account);
    }
}