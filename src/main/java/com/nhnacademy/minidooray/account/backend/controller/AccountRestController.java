package com.nhnacademy.minidooray.account.backend.controller;

import com.nhnacademy.minidooray.account.backend.domain.AccountIdOnlyDTO;
import com.nhnacademy.minidooray.account.backend.domain.AccountRegisterRequestDTO;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfoDTO;
import com.nhnacademy.minidooray.account.backend.entity.Account;
import com.nhnacademy.minidooray.account.backend.exception.AccountNotFoundException;
import com.nhnacademy.minidooray.account.backend.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountRestController {
    private final AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createAccount(@RequestBody AccountRegisterRequestDTO request) {
        accountService.createAccount(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> doLogin(@RequestBody LoginInfoDTO loginInfoDTO) {
        return accountService.matches(loginInfoDTO)
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @GetMapping("/info")
    public ResponseEntity<Account> getInfo(@RequestBody AccountIdOnlyDTO request) {
        try {
            Account account = accountService.getAccount(request.getId());

            return ResponseEntity.ok(account);
        } catch(AccountNotFoundException exception) {
            log.error("Not Exist Account.", exception);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAccount(@RequestBody AccountIdOnlyDTO request) {
        accountService.updateAccountStatus(request.getId(), "휴면");

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
