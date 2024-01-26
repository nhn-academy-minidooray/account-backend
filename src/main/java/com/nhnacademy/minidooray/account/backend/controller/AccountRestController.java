package com.nhnacademy.minidooray.account.backend.controller;

import com.nhnacademy.minidooray.account.backend.domain.AccountIdOnlyRequest;
import com.nhnacademy.minidooray.account.backend.domain.AccountPageInfoDto;
import com.nhnacademy.minidooray.account.backend.domain.AccountRegisterRequest;
import com.nhnacademy.minidooray.account.backend.domain.LoginInfoRequest;
import com.nhnacademy.minidooray.account.backend.entity.Account;
import com.nhnacademy.minidooray.account.backend.service.AccountService;
import java.util.Optional;
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
        Optional<Account> account = accountService.createAccount(request);

        return account.isPresent()
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> doLogin(@RequestBody LoginInfoRequest request) {
        return accountService.matches(request)
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @GetMapping("/info")
    public ResponseEntity<AccountPageInfoDto> getInfo(@RequestBody AccountIdOnlyRequest request) {
            Optional<AccountPageInfoDto> info
                    = accountService.getAccountPageInfo(request.getId());

            return info.isPresent()
                    ? ResponseEntity.ok(info.get())
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAccount(@RequestBody AccountIdOnlyRequest request) {
        int result = accountService.setDormantAccount(request.getId());

        return result == 1
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
