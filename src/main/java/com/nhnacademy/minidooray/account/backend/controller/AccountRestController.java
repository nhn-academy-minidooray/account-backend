package com.nhnacademy.minidooray.account.backend.controller;

import com.nhnacademy.minidooray.account.backend.domain.AccountIdOnlyRequest;
import com.nhnacademy.minidooray.account.backend.domain.AccountPageInfoDto;
import com.nhnacademy.minidooray.account.backend.domain.AccountPageInfoRequest;
import com.nhnacademy.minidooray.account.backend.domain.AccountRegisterRequest;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfoRequest;
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
    public ResponseEntity<Void> createAccount(@RequestBody AccountRegisterRequest request) {
        accountService.createAccount(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> doLogin(@RequestBody LoginInfoRequest request) {
        return accountService.matches(request)
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @GetMapping("/info")
    public ResponseEntity<AccountPageInfoDto> getInfo(@RequestBody AccountIdOnlyRequest request) {
        try {
            AccountPageInfoDto info = accountService.getAccountPageInfo(request.getId());

            return ResponseEntity.ok(info);
        } catch(AccountNotFoundException exception) {
            log.error("Not Exist Account.", exception);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAccount(@RequestBody AccountIdOnlyRequest request) {
        accountService.setDormantAccount(request.getId());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
