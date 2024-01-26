package com.nhnacademy.minidooray.account.backend.controller;

import com.nhnacademy.minidooray.account.backend.domain.dto.AccountStatusInfoDTO;
import com.nhnacademy.minidooray.account.backend.domain.requestbody.AccountIdOnlyRequest;
import com.nhnacademy.minidooray.account.backend.domain.dto.AccountPageInfoDTO;
import com.nhnacademy.minidooray.account.backend.domain.requestbody.AccountRegisterRequest;
import com.nhnacademy.minidooray.account.backend.domain.requestbody.LoginInfoRequest;
import com.nhnacademy.minidooray.account.backend.service.AccountService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        boolean isProcessed = accountService.createAccount(request);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AccountStatusInfoDTO> doLogin(@RequestBody LoginInfoRequest request) {
        Optional<AccountStatusInfoDTO> info
                = accountService.matches(request);

        return info.isPresent()
                ? ResponseEntity.ok(info.get())
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @GetMapping("/info/{accountId}")
    public ResponseEntity<AccountPageInfoDTO> getInfo(@PathVariable String accountId) {
            Optional<AccountPageInfoDTO> info
                    = accountService.getAccountPageInfo(accountId);

            return info.isPresent()
                    ? ResponseEntity.ok(info.get())
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAccount(@RequestBody LoginInfoRequest request) {
        boolean isProcessed = accountService.setDormantAccount(request);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
